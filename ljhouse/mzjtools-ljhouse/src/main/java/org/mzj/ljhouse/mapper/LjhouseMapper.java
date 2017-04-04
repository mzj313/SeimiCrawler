package org.mzj.ljhouse.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface LjhouseMapper {
	@Select("select id, name from ljhouse_district order by id")
	List<Map<String, Object>> findQuList(@Param("name") String name);
	
	@Update("update ljhouse_xiaoqu set pinyin=#{pinyin},shortpinyin=#{shortpinyin} where id=#{id}")
	int setPinyin2xiaoqu(@Param("id") Integer id, @Param("pinyin") String pinyin,
			@Param("shortpinyin") String shortpinyin);
}
