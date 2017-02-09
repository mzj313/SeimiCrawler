package cn.wanghaomiao.crawlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.wanghaomiao.dao.mybatis.LjHouseDAO;
import cn.wanghaomiao.model.LjHouse;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "ljhouse")
public class LjHouseCrawler extends BaseSeimiCrawler {
    @Autowired
    private LjHouseDAO storeToDbDAO;

    @Override
    public String[] startUrls() {
        String url = "http://bj.lianjia.com/ershoufang/pg{$rowNo}ng1hu1nb1su1y4y3y2y1f5f2l2bp350ep450/";
        List<String> urlList = new ArrayList<String>();
        // 总共22页 每次爬5页
		int[][] rs = { { 0, 5 }, { 5, 10 }, { 10, 15 }, { 15, 20 }, { 20, 22 } };
		int k = 0;
		for (int i = rs[k][0]; i < rs[k][1]; i++) {
			urlList.add(url.replace("{$rowNo}", "" + (i + 1)));
        }
		return urlList.toArray(new String[0]);
    }

    @Override
    public void start(Response response) {
        JXDocument doc = response.document();
        try {
            List<Object> urls = doc.sel("//a[@class='img']/@href");
            logger.info("start... {}", urls.size());
            for (Object s : urls) {
            	Thread.sleep(100);
                push(Request.build(s.toString(), "renderBean"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renderBean(Response response) {
        try {
        	LjHouse lj = response.render(LjHouse.class);
        	lj.setUrl(response.getUrl());
            logger.info("bean resolve res={}", lj);
            //使用神器paoding-jade存储到DB
            int changeNum = storeToDbDAO.save(lj);
            logger.info("store success,blogId = {},changeNum={}", lj.getId(), changeNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
