package org.mzj.test;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import cn.wanghaomiao.dao.mybatis.LjHouseXiaoquDAO;
import cn.wanghaomiao.model.LjHouseXiaoqu;
import net.paoding.rose.jade.context.application.JadeFactory;

public class MzjTest {
	private static DriverManagerDataSource dataSource;

	@BeforeClass
	public static void setUp() throws Exception {
		try {
			dataSource = new DriverManagerDataSource();
			dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/seimi");
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUsername("root");
			dataSource.setPassword("root123");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test01() {
		try {
			JadeFactory factory = new JadeFactory(dataSource);
			LjHouseXiaoquDAO dao = factory.create(LjHouseXiaoquDAO.class);
			List<LjHouseXiaoqu> xiaoquList = dao.selectXiaoqu(null, null, "1111027380476");
			System.out.println(xiaoquList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
