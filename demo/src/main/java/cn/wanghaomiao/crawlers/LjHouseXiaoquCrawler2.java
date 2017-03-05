package cn.wanghaomiao.crawlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.wanghaomiao.dao.mybatis.LjHouseXiaoquDAO;
import cn.wanghaomiao.model.LjHouseXiaoqu;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//小区信息更新成交记录的页数
@Crawler(name = "ljhouse_xiaoqu2")
public class LjHouseXiaoquCrawler2 extends BaseSeimiCrawler {
    @Autowired
    private LjHouseXiaoquDAO storeToDbDAO;

    @Override
    public String[] startUrls() {
    	List<LjHouseXiaoqu> xiaoquList = storeToDbDAO.selectXiaoqu(null,null);
    	List<String> urlList = new ArrayList<String>();
    	for(LjHouseXiaoqu xiaoqu : xiaoquList) {
    		String url = xiaoqu.getUrl();
			urlList.add(xiaoqu2chengjiao(url));
    	}
		return urlList.toArray(new String[0]);
    }

    //小区url -> 小区成交url
    //http://bj.lianjia.com/xiaoqu/1111027379001/
    //http://bj.lianjia.com/chengjiao/c1111027379001/
	private String xiaoqu2chengjiao(String url) {
		return url.replaceAll("/xiaoqu/", "/chengjiao/c");
	}
	private String chengjiao2xiaoqu(String url) {
		return url.replaceAll("/chengjiao/c", "/xiaoqu/");
	}

    @Override
    public void start(Response response) {
    	JXDocument doc = response.document();
        try {
        	String url = response.getUrl();
        	List<Object> lis = doc.sel("//div[@class='page-box house-lst-page-box']/@page-data");
            logger.info("start...  {} {}", url, lis);
            JSONObject json = JSON.parseObject((String)lis.get(0));
            String totalPage = String.valueOf(json.get("totalPage"));
            int r = storeToDbDAO.updateTotalPage(chengjiao2xiaoqu(url),totalPage);
            logger.info("totalPage= {}, result = {}", totalPage, r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
