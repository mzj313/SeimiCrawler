package org.mzj.ljhouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mzj.ljhouse.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class CrawlOnePageTest {
	@Autowired
	private AreaMapper areaMapper;

	@Test
	public void 抓取行政区划() {
		try {
			Document document = null;
				document = Jsoup.connect("http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201703/t20170310_1471429.html")
						.get();
			
			List<String[]> result = new ArrayList<String[]>();
			Elements elements = document.getElementsByClass("MsoNormal");
			int size = elements.size();
			System.out.println(size);
			for (int i = 0; i < size; i++) {
				Element element = elements.get(i);
				String[] codename = new String[2];
				Elements spanElements = element.getElementsByTag("span");
				for(Element span : spanElements) {
					if(span.hasAttr("lang")) {
						codename[0] = span.text().replace(" ", "");
					} else if(span.hasAttr("style")) {
						codename[1] = span.text().replace("　", "");
					}
				}
				result.add(codename);
			}
			for(String[] ss : result) {
//			System.out.println(ss[0] + " - " + ss[1]);
				areaMapper.save(ss[0], ss[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
