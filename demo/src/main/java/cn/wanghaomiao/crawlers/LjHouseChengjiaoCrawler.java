package cn.wanghaomiao.crawlers;

import java.util.ArrayList;
import java.util.List;

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
		int startpage = 3481 / 30;// 从0开始
		for (int i = startpage; i < startpage + 1; i++) {// 每次爬5页
			urlList.add(url.replace("{$rowNo}", "" + (i + 1)));
        }
		return urlList.toArray(new String[0]);
    }

    @Override
    public void start(Response response) {
        JXDocument doc = response.document();
        try {
            List<Object> urls = doc.sel("//a[@class='img']/@href");
            logger.info("start... {} {}", urls.size(), response.getUrl());
            for (Object s : urls) {
            	Thread.sleep(1000);//
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
            if(lj.getTitle().trim().length() <= 0 && lj.getTotalPrice().trim().length()<=0) {
            	logger.error("标题总价都为空");
            	return;//退出
            }
            //使用神器paoding-jade存储到DB
            int changeNum = storeToDbDAO.save(lj);
            logger.info("store success,id = {},changeNum={}", lj.getId(), changeNum);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);;//退出
        }
    }
    
    public static void main(String[] args) {
		System.out.println(30/30);
	}
}
