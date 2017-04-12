package org.mzj.ljhouse.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface AreaMapper {
	@Insert("insert into area(code,name) values (#{code},#{name})")
	int save(@Param("code") String code, @Param("name") String name);
}
