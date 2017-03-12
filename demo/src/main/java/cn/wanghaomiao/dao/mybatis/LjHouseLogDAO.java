package cn.wanghaomiao.dao.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

public interface LjHouseLogDAO {

	@Insert("insert into ljhouse_log (type,msg,createtime) values (#{type},#{msg},now())")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int save(@Param("id") String id, @Param("type") String type, @Param("msg") String msg);
	
}

