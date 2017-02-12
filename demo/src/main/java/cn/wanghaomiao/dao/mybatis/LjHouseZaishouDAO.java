package cn.wanghaomiao.dao.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import cn.wanghaomiao.model.LjHouseZaishou;

public interface LjHouseZaishouDAO {

	@Insert("insert into ljhouse_zaishou (title,totalprice,unitprice,roommaininfo,roomsubinfo,typemaininfo,typesubinfo,areamaininfo,areasubinfo,community,position1,position2,position3,position4,content,url,update_time) "
			+ "values (#{lj.title},#{lj.totalPrice},#{lj.unitPrice},#{lj.roomMainInfo},#{lj.roomSubInfo},#{lj.typeMainInfo},#{lj.typeSubInfo},#{lj.areaMainInfo},#{lj.areaSubInfo},#{lj.community},#{lj.position1},#{lj.position2},#{lj.position3},#{lj.position4},#{lj.content},#{lj.url},now())")
	@Options(useGeneratedKeys = true, keyProperty = "lj.id")
	int save(@Param("lj") LjHouseZaishou lj);
}
