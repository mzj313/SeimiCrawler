package cn.wanghaomiao.crawlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.wanghaomiao.dao.mybatis.LjHouseChengjiaoDAO2;
import cn.wanghaomiao.dao.mybatis.LjHouseXiaoquDAO;
import cn.wanghaomiao.model.LjHouseChengjiao2;
import cn.wanghaomiao.model.LjHouseXiaoqu;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.core.SeimiBeanResolver;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//成交二手房，从按小区查成交的列表页获取，最多只能查100页
@Crawler(name = "ljhouse_chengjiao3")
public class LjHouseChengjiaoCrawler3 extends BaseSeimiCrawler {
	@Autowired
	private LjHouseXiaoquDAO xiaoquDAO;
    @Autowired
    private LjHouseChengjiaoDAO2 storeToDbDAO;

    @Override
    public String[] startUrls() {
    	List<LjHouseXiaoqu> xiaoquList = xiaoquDAO.selectXiaoqu(null,null);
//    	System.out.println(xiaoquList);
    	List<String> urlList = new ArrayList<String>();
    	for(LjHouseXiaoqu xiaoqu : xiaoquList) {
    		String xqurl = xiaoqu.getUrl();//http://bj.lianjia.com/xiaoqu/1111027379001/
			for (int i = 1; i <= xiaoqu.getTotalPage(); i++) {
    			String url = xqurl.replaceAll("/xiaoqu/", "/chengjiao/pg" + i + "c");
    			urlList.add(url);
    		}
    	}
		return urlList.toArray(new String[0]);
    }

    @Override
    public void start(Response response) {
    	JXDocument doc = response.document();
        try {
        	List<Object> lis = doc.sel("//ul[@class='listContent']/li");
            logger.info("start...  {}", response.getUrl());
            for(Object li : lis) {
            	LjHouseChengjiao2 lj = SeimiBeanResolver.parse(LjHouseChengjiao2.class, li.toString());
            	lj.setRoomMainInfo(subStr(lj.getRoomMainInfo()," ",1).trim());
            	lj.setRoomSubInfo(subStr(lj.getRoomSubInfo()," ",0).trim());
            	lj.setTypeMainInfo(subStr(lj.getTypeMainInfo(), "|", 0).trim());
            	lj.setTypeSubInfo(subStr(lj.getTypeSubInfo(), "|", 1).trim());
            	lj.setAreaMainInfo(subStr(lj.getAreaMainInfo()," ",2).trim());
            	lj.setAreaSubInfo(subStr(lj.getAreaSubInfo()," ",1).trim());
            	lj.setCommunity(subStr(lj.getCommunity()," ",1).trim());
            	lj.setUrl(response.getUrl());
//            	logger.info("bean resolve res={}", lj);
                if(lj.getTitle().trim().length() <= 0 || lj.getTotalPrice().trim().length()<=0
						|| lj.getUnitPrice().trim().length() <= 0 || lj.getDealDate().trim().length() <= 0) {
					logger.error("标题或总价或单价或交易日期为空 {}", lj);
					continue;
				}
                //防止重复写入
				List<LjHouseChengjiao2> chengjiaoList = storeToDbDAO.selectChengjiao(lj.getTitle(), lj.getTotalPrice(),
						lj.getUnitPrice(), lj.getDealDate());
                if(!chengjiaoList.isEmpty()) {
                	logger.info("记录已存在{}条 id={}",chengjiaoList.size(),chengjiaoList.get(0).getId());
                	continue;
                }
                //使用神器paoding-jade存储到DB
                int changeNum = storeToDbDAO.save(lj);
                logger.info("store success,id = {},changeNum={}", lj.getId(), changeNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // s用sp分隔取第i个
	protected static String subStr(String s, String sp, int i) {
		String[] ss = s.split("\\" + sp);
		if (i >= 0 && i < ss.length) {
			return ss[i];
		} else {
			return s;
		}
	}

    public static void main(String[] args) {
		String s = "南 北 | 简装 | 有电梯";
		System.out.println(subStr(s, "|", 0));
		System.out.println(subStr(s, "|", 1));
		System.out.println(subStr(s, "|", 2));
		
	}
}
