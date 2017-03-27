package org.mzj.ljhouse;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mzj.ljhouse.mapper.LjhouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {
	@Autowired
	private LjhouseMapper ljhouseMapper;
	
	@Test
	@Rollback
	public void findByName() throws Exception {
		List<Map<String, Object>> quList = ljhouseMapper.findQuList(null);
		System.out.println(quList);
	}
}
