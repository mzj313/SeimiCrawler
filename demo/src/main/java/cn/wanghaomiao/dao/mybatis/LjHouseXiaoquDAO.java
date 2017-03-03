package cn.wanghaomiao.dao.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import cn.wanghaomiao.model.LjHouseXiaoqu;

public interface LjHouseXiaoquDAO {

	@Insert("insert into ljhouse_xiaoqu (title,totalprice,houseInfo,positionInfo,positionInfo1,positionInfo2,tagList,num,url,update_time) "
			+ "values (#{lj.title},#{lj.totalPrice},#{lj.houseInfo},#{lj.positionInfo},#{lj.positionInfo1},#{lj.positionInfo2},#{lj.tagList},#{lj.num},#{lj.url},now())")
	@Options(useGeneratedKeys = true, keyProperty = "lj.id")
	int save(@Param("lj") LjHouseXiaoqu lj);
}
