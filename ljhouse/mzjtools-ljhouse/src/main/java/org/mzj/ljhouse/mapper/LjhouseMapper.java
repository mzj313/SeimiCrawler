package org.mzj.ljhouse.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LjhouseMapper {
	@Select("select id, name from ljhouse_district order by id")
	List<Map<String, Object>> findQuList(@Param("name") String name);
}
