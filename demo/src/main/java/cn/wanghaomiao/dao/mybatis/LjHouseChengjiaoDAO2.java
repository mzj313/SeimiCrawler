package cn.wanghaomiao.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.wanghaomiao.model.LjHouseChengjiao2;

public interface LjHouseChengjiaoDAO2 {

	@Insert("insert into ljhouse_chengjiao2 (title,totalprice,unitprice,roommaininfo,roomsubinfo,typemaininfo,typesubinfo,areamaininfo,areasubinfo,community,position1,position2,position3,position4,dealdate,content,url,update_time) "
			+ "values (#{lj.title},#{lj.totalPrice},#{lj.unitPrice},#{lj.roomMainInfo},#{lj.roomSubInfo},#{lj.typeMainInfo},#{lj.typeSubInfo},#{lj.areaMainInfo},#{lj.areaSubInfo},#{lj.community},#{lj.position1},#{lj.position2},#{lj.position3},#{lj.position4},#{lj.dealDate},#{lj.content},#{lj.url},now())")
	@Options(useGeneratedKeys = true, keyProperty = "lj.id")
	int save(@Param("lj") LjHouseChengjiao2 lj);

	@Select("SELECT * FROM ljhouse_chengjiao2 WHERE (title = #{title} or #{title} is null) and (totalPrice = #{totalPrice} or #{totalPrice} is null) and (unitPrice = #{unitPrice} or #{unitPrice} is null) and (dealDate = #{dealDate} or #{dealDate} is null)")
	List<LjHouseChengjiao2> selectChengjiao(@Param("title") String title, @Param("totalPrice") String totalPrice,
			@Param("unitPrice") String unitPrice, @Param("dealDate") String dealDate);
}
