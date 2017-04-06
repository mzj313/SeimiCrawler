package org.mzj.ljhouse;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@SpringBootApplication
public class Application {

	// localhost:8280
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	// http://localhost:8280/druid

	/**
	 * 注册DruidServlet
	 */
	@Bean
	public ServletRegistrationBean druidServletRegistrationBean() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		servletRegistrationBean.setServlet(new StatViewServlet());
		servletRegistrationBean.addUrlMappings("/druid/*");
		return servletRegistrationBean;
	}

	/**
	 * 注册DruidFilter拦截
	 */
	@Bean
	public FilterRegistrationBean duridFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new WebStatFilter());
		Map<String, String> initParams = new HashMap<String, String>();
		// 设置忽略请求
		initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		filterRegistrationBean.setInitParameters(initParams);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

	/**
	 * 配置DataSource
	 */
	@Bean
	public DataSource druidDataSource() throws SQLException {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUsername("root");
		druidDataSource.setPassword("root123");
		druidDataSource.setUrl("jdbc:mysql://localhost:3306/seimi");
		druidDataSource.setMaxActive(100);
		druidDataSource.setFilters("stat,wall");
		druidDataSource.setInitialSize(10);
		return druidDataSource;
	}

}