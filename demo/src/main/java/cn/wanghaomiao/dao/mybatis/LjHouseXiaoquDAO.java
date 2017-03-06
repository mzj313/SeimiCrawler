package cn.wanghaomiao.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.wanghaomiao.model.LjHouseXiaoqu;

public interface LjHouseXiaoquDAO {

	@Insert("insert into ljhouse_xiaoqu (title,averageprice,houseInfo,positionInfo,positionInfo1,positionInfo2,tagList,num,url,rid,update_time) "
			+ "values (#{lj.title},#{lj.averagePrice},#{lj.houseInfo},#{lj.positionInfo},#{lj.positionInfo1},#{lj.positionInfo2},#{lj.tagList},#{lj.num},#{lj.url},#{lj.rid},now())")
	@Options(useGeneratedKeys = true, keyProperty = "lj.id")
	int save(@Param("lj") LjHouseXiaoqu lj);
	
	@Select("SELECT * FROM ljhouse_xiaoqu WHERE (positionInfo1 = #{positionInfo1} or #{positionInfo1} is null) and (title = #{title} or #{title} is null)")
	List<LjHouseXiaoqu> selectXiaoqu(@Param("positionInfo1")String positionInfo1, @Param("title")String title);
	
	@Update("update ljhouse_xiaoqu set totalPage = #{totalPage} WHERE url = #{url}")
	int updateTotalPage(@Param("url")String url, @Param("totalPage")String totalPage);
}
