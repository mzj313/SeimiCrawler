package cn.wanghaomiao.crawlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import cn.wanghaomiao.dao.mybatis.LjHouseChengjiaoDAO;
import cn.wanghaomiao.model.LjHouseChengjiao;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//成交二手房
@Crawler(name = "ljhouse_chengjiao")
public class LjHouseChengjiaoCrawler extends BaseSeimiCrawler {
    @Autowired
    private LjHouseChengjiaoDAO storeToDbDAO;

    @Override
    public String[] startUrls() {
        String url = "http://bj.lianjia.com/chengjiao/pg{$rowNo}sf1hu1f2f5y4y3y2y1l2l3bp150ep450/";
        List<String> urlList = new ArrayList<String>();
        // 总共22页 每次爬10页
		int[][] rs = { { 0, 10 }, { 10, 20 }, { 20, 30 }, { 30, 40 }, { 40, 50 }, //01234
				      { 50, 60 }, { 60, 70 }, { 70, 80 }, { 80, 90 }, { 90, 100 } };
		int k = 6;
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
            	Thread.sleep(2000);//
                push(Request.build(s.toString(), "renderBean"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renderBean(Response response) {
        try {
        	LjHouseChengjiao lj = response.render(LjHouseChengjiao.class);
        	lj.setUrl(response.getUrl());
            logger.info("bean resolve res={}", lj);
            //使用神器paoding-jade存储到DB
            int changeNum = storeToDbDAO.save(lj);
            logger.info("store success,id = {},changeNum={}", lj.getId(), changeNum);
        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();//停止
        }
    }
    
    public static void main(String[] args) {
		System.out.println(200 * new Random().nextInt(10));
	}
}
