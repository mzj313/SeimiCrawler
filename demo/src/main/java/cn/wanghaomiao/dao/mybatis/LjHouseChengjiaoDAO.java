package cn.wanghaomiao.dao.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import cn.wanghaomiao.model.LjHouseChengjiao;

public interface LjHouseChengjiaoDAO {

	@Insert("insert into ljhouse_chengjiao (title,totalprice,unitprice,roommaininfo,roomsubinfo,typemaininfo,typesubinfo,areamaininfo,areasubinfo,community,position1,position2,position3,position4,content,update_time) "
			+ "values (#{lj.title},#{lj.totalPrice},#{lj.unitPrice},#{lj.roomMainInfo},#{lj.roomSubInfo},#{lj.typeMainInfo},#{lj.typeSubInfo},#{lj.areaMainInfo},#{lj.areaSubInfo},#{lj.community},#{lj.position1},#{lj.position2},#{lj.position3},#{lj.position4},#{lj.content},now())")
	@Options(useGeneratedKeys = true, keyProperty = "lj.id")
	int save(@Param("lj") LjHouseChengjiao lj);
}
