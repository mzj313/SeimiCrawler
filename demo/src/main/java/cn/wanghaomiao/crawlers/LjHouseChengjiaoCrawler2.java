package cn.wanghaomiao.crawlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.wanghaomiao.dao.mybatis.LjHouseChengjiaoDAO3;
import cn.wanghaomiao.dao.mybatis.LjHouseXiaoquDAO;
import cn.wanghaomiao.model.LjHouseChengjiao2;
import cn.wanghaomiao.model.LjHouseChengjiao3;
import cn.wanghaomiao.model.LjHouseXiaoqu;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.core.SeimiBeanResolver;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//成交二手房，从列表页获取，最多只能查100页
@Crawler(name = "ljhouse_chengjiao2")
public class LjHouseChengjiaoCrawler2 extends BaseSeimiCrawler {
	@Autowired
	private LjHouseXiaoquDAO xiaoquDAO;
    @Autowired
    private LjHouseChengjiaoDAO3 storeToDbDAO;

    @Override
    public String[] startUrls() {
        String url = "http://bj.lianjia.com/chengjiao/pg{$rowNo}sf1hu1f2f5y4y3y2y1l2l3bp150ep500/";
        List<String> urlList = new ArrayList<String>();
		for (int i = 1; i <= 100; i++) {
			urlList.add(url.replace("{$rowNo}", "" + i));
        }
		return urlList.toArray(new String[0]);
    }

    @Override
    public void start(Response response) {
    	JXDocument doc = response.document();
        try {
        	List<Object> lis = doc.sel("//ul[@class='listContent']/li");
//            logger.info("start...  {}", response.getUrl());
            for(Object li : lis) {
            	LjHouseChengjiao3 lj = SeimiBeanResolver.parse(LjHouseChengjiao3.class, li.toString());
            	lj.setRoomMainInfo(subStr(lj.getRoomMainInfo()," ",1).trim());
            	lj.setRoomSubInfo(subStr(lj.getRoomSubInfo()," ",0).trim());
            	lj.setTypeMainInfo(subStr(lj.getTypeMainInfo(), "|", 0).trim());
            	lj.setTypeSubInfo(subStr(lj.getTypeSubInfo(), "|", 1).trim());
            	lj.setAreaMainInfo(subStr(lj.getAreaMainInfo()," ",2).trim());
            	lj.setAreaSubInfo(subStr(lj.getAreaSubInfo()," ",1).trim());
            	lj.setCommunity(subStr(lj.getCommunity()," ",1).trim());
            	lj.setPosition4(response.getUrl());
            	lj.setRid(subStr(lj.getTitle().trim(), " ", 0));
            	
            	List<LjHouseXiaoqu> xiaoquList = xiaoquDAO.selectXiaoqu(null, lj.getRid(), null);
            	if(!xiaoquList.isEmpty()) {
            		lj.setRid(xiaoquList.get(0).getRid());
            	}
            	
//            	logger.info("bean resolve res={}", lj);
                if(lj.getTitle().trim().length() <= 0 || lj.getTotalPrice().trim().length()<=0
						|| lj.getUnitPrice().trim().length() <= 0 || lj.getDealDate().trim().length() <= 0) {
                	logger.error("标题或总价或单价或交易日期为空 {}", lj);
                	continue;
                }
                //防止重复写入
				List<LjHouseChengjiao3> chengjiaoList = storeToDbDAO.selectChengjiao(lj.getTitle(), lj.getTotalPrice(),
						lj.getUnitPrice(), lj.getDealDate());
                if(!chengjiaoList.isEmpty()) {
//                	logger.info("记录已存在{}条 id={}",chengjiaoList.size(),chengjiaoList.get(0).getId());
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
	
	static String getRidFromUrl(String url){
		String t = url.replaceAll("http://bj.lianjia.com/chengjiao/", "").replaceAll("/", "").replaceAll(".html", "");
		return t.substring(t.indexOf("c")+1);
    }

    public static void main(String[] args) {
		String s = "南 北 | 简装 | 有电梯";
		System.out.println(subStr(s, "|", 0));
		System.out.println(subStr(s, "|", 1));
		System.out.println(subStr(s, "|", 2));
		
	}
}
