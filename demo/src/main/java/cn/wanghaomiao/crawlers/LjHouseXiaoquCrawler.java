package cn.wanghaomiao.crawlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.wanghaomiao.dao.mybatis.LjHouseXiaoquDAO;
import cn.wanghaomiao.model.LjHouseXiaoqu;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.core.SeimiBeanResolver;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//小区信息
@Crawler(name = "ljhouse_xiaoqu")
public class LjHouseXiaoquCrawler extends BaseSeimiCrawler {
    @Autowired
    private LjHouseXiaoquDAO storeToDbDAO;

    @Override
    public String[] startUrls() {
        String url = "http://bj.lianjia.com/xiaoqu/pg{$rowNo}";
        List<String> urlList = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			urlList.add(url.replace("{$rowNo}", "" + (i + 1)));
        }
		return urlList.toArray(new String[0]);
    }

    @Override
    public void start(Response response) {
    	JXDocument doc = response.document();
        try {
        	List<Object> lis = doc.sel("//ul[@class='listContent']/li");
            logger.info("start...  {}", response.getUrl());
            Thread.sleep(1000);//
            for(Object li : lis) {
            	LjHouseXiaoqu lj = SeimiBeanResolver.parse(LjHouseXiaoqu.class, li.toString());
            	lj.setHouseInfo(lj.getHouseInfo().trim());
            	lj.setRid(getRidFromUrl(lj.getUrl()));
            	logger.info("bean resolve res={}", lj);
                if(lj.getTitle().trim().length() <= 0 && lj.getAveragePrice().trim().length()<=0) {
                	logger.error("标题均价都为空");
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
    
    static String getRidFromUrl(String url){
		return url.replaceAll("http://bj.lianjia.com/xiaoqu/", "").replaceAll("/", "");
    }
    
    public static void main(String[] args) {
		System.out.println(getRidFromUrl("http://bj.lianjia.com/xiaoqu/1111027380049/"));
	}

}
