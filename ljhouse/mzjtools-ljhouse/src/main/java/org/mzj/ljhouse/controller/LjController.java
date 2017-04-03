package org.mzj.ljhouse.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.series.Line;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/lj")
public class LjController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("/qu")
	public Object getQuList() {
		String sql = "select id, name from ljhouse_district order by id";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		JSONObject json = new JSONObject();
		json.put("list", list);
		return json;
	}

	@RequestMapping("/shequ")
	public Object getShequList(ModelMap modelMap, String quid) {
		String sql = "select positionInfo2 name,count(*) ct from t_shequmonthprice t where t.id = ? group by positionInfo2 order by convert(positionInfo2 USING gbk)";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, quid);
		JSONObject json = new JSONObject();
		json.put("list", list);
		return json;
	}

	@RequestMapping("/xiaoqu")
	public Object getXiaoquList(ModelMap modelMap, String quid, String shequid) {
		String sql = "select id,title name from ljhouse_xiaoqu t where t.positionInfo1=? and t.positionInfo2 = ? order by convert(title USING gbk)";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, quid, shequid);
		JSONObject json = new JSONObject();
		json.put("list", list);
		return json;
	}

	// http://localhost:8080/lj/shequPrice
	@RequestMapping("/shequPrice")
	// 有了ModelMap后面的参数不用@RequestParam也能接收到
	public Object getShequPrice(ModelMap modelMap, String shequ) {
		String[] args = shequ.split(",");
		if (args == null)
			return null;

		StringBuffer questionMark = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			questionMark.append(",?");
		}
		String sql = "select * from t_shequmonthprice2 t where t.positionInfo2 in (" + questionMark.substring(1)
				+ ") order by month";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args);
		System.out.println(list);

		
//		Option option = new Option();
//		option.title("社区成交均价").tooltip(Trigger.axis).legend("金额(万元)");
//		option.xAxis(new ValueAxis().boundaryGap(0d, 0.01)); //
//		CategoryAxis category = new CategoryAxis();// 类目轴
//		Line line = new Line(shequ);
//		for (Map<String, Object> objectMap : list) { //
//			category.data(objectMap.get("month"));
//			line.data(objectMap.get("unitPrice"));
//		}
//		option.yAxis(category);// 类目轴
//		option.yAxis(new ValueAxis());
//		option.series(line);
		 

		JSONObject option = new JSONObject();

		JSONObject title = new JSONObject();
		title.put("text", "按社区统计均价");
		option.put("title", title);
		option.put("tooltip", new JSONObject());// 提示信息

		JSONObject xAxis = new JSONObject();
		xAxis.put("type", "category");
		List<Object> xdata = new ArrayList<Object>();
		Map<String, Object> seriesdataMap = new HashMap<String, Object>();
		for (Map<String, Object> m : list) {
			xdata.add(m.get("month"));
			String key = m.get("positionInfo1") + "-" + m.get("positionInfo2");
			Map<String, Object> seriesData = (Map<String, Object>) seriesdataMap.get(key);
			if (seriesData == null) {
				seriesData = new HashMap<String, Object>();
				seriesData.put("name", m.get("positionInfo2"));
				seriesData.put("type", "line");
				seriesdataMap.put(key, seriesData);
			}
			List<Object> seriesdata = (List<Object>) seriesData.get("data");
			if (seriesdata == null) {
				seriesdata = new ArrayList<Object>();
				seriesData.put("data", seriesdata);
			}
			seriesdata.add(m.get("unitPrice"));
		}
		xAxis.put("data", xdata);
		option.put("xAxis", xAxis);

		JSONObject yAxis = new JSONObject();
		yAxis.put("type", "value");
		option.put("yAxis", yAxis);

		JSONArray series = JSONArray.fromObject(seriesdataMap.values());
		option.put("series", series);
		return option;
	}

	// http://localhost:8280/lj/xiaoquPrice
	@RequestMapping("/xiaoquPrice")
	// 有了ModelMap后面的参数不用@RequestParam也能接收到
	public Object getXiaoquPrice(ModelMap modelMap, String xiaoqu) {
		String[] args = xiaoqu.split(",");
		if (args == null)
			return null;

		StringBuffer questionMark = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			questionMark.append(",?");
		}
		String sql = "select m.month,m.positionInfo1,m.positionInfo2,m.title,ifnull(n.unitPrice,'') unitPrice "
				+ " from t_xiaoqumonth m left join t_xiaoqumonthprice2 n "
				+ " on (m.positionInfo1=n.positionInfo1 and m.positionInfo2=n.positionInfo2 and m.month=n.dealmonth and m.title=n.title) "
				+ " where m.month > '2013.01' and m.title"
				+ " in (" + questionMark.substring(1)
				+ " ) order by month";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args);
		System.out.println(list);

		JSONObject option = new JSONObject();

		JSONObject title = new JSONObject();
		title.put("text", "按社区统计均价");
		option.put("title", title);
		option.put("tooltip", new JSONObject());// 提示信息

		JSONObject xAxis = new JSONObject();
		xAxis.put("type", "category");
		Set<Object> xdataSet = new HashSet<Object>();
		Map<String, Object> seriesdataMap = new HashMap<String, Object>();//[p1_p2_title=[name=,type=line]]
		for (Map<String, Object> m : list) {
			xdataSet.add(m.get("month"));
			String key = m.get("positionInfo1") + "-" + m.get("positionInfo2") + "-" + m.get("title");
			Map<String, Object> seriesData = (Map<String, Object>) seriesdataMap.get(key);
			if (seriesData == null) {
				seriesData = new HashMap<String, Object>();
				seriesData.put("name", m.get("title"));
				seriesData.put("type", "line");
				seriesdataMap.put(key, seriesData);
			}
			List<Object> seriesdata = (List<Object>) seriesData.get("data");
			if (seriesdata == null) {
				seriesdata = new ArrayList<Object>();
				seriesData.put("data", seriesdata);
			}
			seriesdata.add(m.get("unitPrice"));
		}
		List<Object> xdata = Arrays.asList(xdataSet.toArray());
		Collections.sort(xdata, new Comparator<Object>(){
			public int compare(Object o1, Object o2) {
				return ((String)o1).compareTo((String)o2);
			}
		});
		xAxis.put("data", xdata);
		option.put("xAxis", xAxis);

		JSONObject yAxis = new JSONObject();
		yAxis.put("type", "value");
		option.put("yAxis", yAxis);

		for(Iterator<Object> it = seriesdataMap.values().iterator();it.hasNext();) {
			List<Object> seriesdata = (List<Object>) ((Map<String, Object>)it.next()).get("data");
			Collections.sort(seriesdata, new Comparator<Object>(){
				public int compare(Object o1, Object o2) {
					return (String.valueOf(o1)).compareTo(String.valueOf(o2));
				}
			});
		}
		JSONArray series = JSONArray.fromObject(seriesdataMap.values());
		option.put("series", series);
		return option;
	}
}
