package cn.wanghaomiao.crawlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.wanghaomiao.dao.mybatis.LjHouseChengjiaoDAO3;
import cn.wanghaomiao.dao.mybatis.LjHouseLogDAO;
import cn.wanghaomiao.dao.mybatis.LjHouseXiaoquDAO;
import cn.wanghaomiao.model.LjHouseChengjiao3;
import cn.wanghaomiao.model.LjHouseXiaoqu;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.core.SeimiBeanResolver;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//成交二手房，从按小区查成交的列表页获取，最多只能查100页
@Crawler(name = "ljhouse_chengjiao3")
public class LjHouseChengjiaoCrawler3 extends BaseSeimiCrawler {
	static int errNum = 0;
	@Autowired
	private LjHouseXiaoquDAO xiaoquDAO;
    @Autowired
    private LjHouseChengjiaoDAO3 storeToDbDAO;
    @Autowired
    LjHouseLogDAO ljHouseLogDAO;

    @Override
    public String[] startUrls() {
    	List<LjHouseXiaoqu> xiaoquList = xiaoquDAO.selectXiaoqu(null,null,null);
//    	System.out.println(xiaoquList);
    	List<String> urlList = new ArrayList<String>();
    	for(LjHouseXiaoqu xiaoqu : xiaoquList) {
    		String xqurl = xiaoqu.getUrl();//http://bj.lianjia.com/xiaoqu/1111027379001/
			for (int i = xiaoqu.getFetchPage(); i <= xiaoqu.getTotalPage(); i++) {
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
//			logger.info("start...  {} lis.size={}", response.getUrl(),lis.size());
			String rid = getRidFromUrl(response.getUrl());
			if (lis.isEmpty()) {
				logger.error("流量异常或页面改版！");
//				ljHouseLogDAO.save(null, "ljhouse_chengjiao3", response.getUrl() + " 流量异常或页面改版！");
//				errNum++;
//				if(errNum > 50) systemExit();
				return;
			}
			int totalChangeNum = 0;
			for (Object li : lis) {
				LjHouseChengjiao3 lj = SeimiBeanResolver.parse(LjHouseChengjiao3.class, li.toString());
				lj.setRoomMainInfo(subStr(lj.getRoomMainInfo(), " ", 1).trim());
				lj.setRoomSubInfo(subStr(lj.getRoomSubInfo(), " ", 0).trim());
				lj.setTypeMainInfo(subStr(lj.getTypeMainInfo(), "|", 0).trim());
				lj.setTypeSubInfo(subStr(lj.getTypeSubInfo(), "|", 1).trim());
				lj.setAreaMainInfo(subStr(lj.getAreaMainInfo(), " ", 2).trim());
				lj.setAreaSubInfo(subStr(lj.getAreaSubInfo(), " ", 1).trim());
				lj.setCommunity(subStr(lj.getCommunity(), " ", 1).trim());
				lj.setUrl(response.getUrl());
				lj.setRid(rid);
				// logger.info("bean resolve res={}", lj);
				if (lj.getTitle().trim().length() <= 0 || lj.getTotalPrice().trim().length() <= 0
						|| lj.getUnitPrice().trim().length() <= 0 || lj.getDealDate().trim().length() <= 0) {
//					logger.error("标题或总价或单价或交易日期为空 {}", lj.getTitle() + " " + lj.getUrl());
					continue;
				}
				// 防止重复写入
				List<LjHouseChengjiao3> chengjiaoList = storeToDbDAO.selectChengjiao(lj.getTitle(), lj.getTotalPrice(),
						lj.getUnitPrice(), lj.getDealDate());
				if (!chengjiaoList.isEmpty()) {
//					logger.info("记录已存在{}条 id={}", chengjiaoList.size(), chengjiaoList.get(0).getId());
					continue;
				}
				// 使用神器paoding-jade存储到DB
				int changeNum = storeToDbDAO.save(lj);
//				logger.info("store success,id = {},changeNum={}", lj.getId(), changeNum);
				totalChangeNum+=changeNum;
			}
			// 更新xiaoqu表已获取页数
			List<LjHouseXiaoqu> xiaoquList = xiaoquDAO.selectXiaoqu(null, null, rid);
			if (!xiaoquList.isEmpty()) {
				LjHouseXiaoqu xiaoqu = xiaoquList.get(0);
				int fetchPage = (xiaoqu.getFetchPage() == null ? 0 : xiaoqu.getFetchPage()) + 1;
				xiaoqu.setFetchPage(fetchPage);
				xiaoquDAO.update(xiaoqu);
				logger.info("{} 更新获取页数为 {} 本次增加记录数{}", xiaoqu.getTitle(), fetchPage, totalChangeNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void systemExit() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				System.exit(0);
			};
		}.start();
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
		String t = url.replaceAll("http://bj.lianjia.com/chengjiao/", "").replaceAll("/", "");
		return t.substring(t.indexOf("c")+1);
    }
    
    public static void main(String[] args) {
		System.out.println(getRidFromUrl("http://bj.lianjia.com/chengjiao/pg6c1111027382209/"));
	}
}
