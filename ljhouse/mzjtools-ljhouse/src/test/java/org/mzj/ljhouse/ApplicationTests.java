package org.mzj.ljhouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mzj.ljhouse.mapper.LjhouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {
	@Autowired
	private LjhouseMapper ljhouseMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	@Rollback
	public void findByName() throws Exception {
		List<Map<String, Object>> quList = ljhouseMapper.findQuList(null);
		System.out.println(quList);
	}
	
	
	@Test
	public void test设置小区表拼音列() {
		String sql = "select id, title from ljhouse_xiaoqu t ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		try {
			for(Map<String, Object> map : list) {
				String pinyin = PinyinHelper.convertToPinyinString((String) map.get("title"), "", PinyinFormat.WITHOUT_TONE);
				String shortpinyin = PinyinHelper.getShortPinyin((String) map.get("title"));
				ljhouseMapper.setPinyin2xiaoqu((Integer) map.get("id"), pinyin, shortpinyin);
			}
		} catch (PinyinException e) {
			e.printStackTrace();
		}
	}
}
