package cn.wanghaomiao.dao.mybatis;

import org.apache.ibatis.jdbc.SQL;

import cn.wanghaomiao.model.LjHouseXiaoqu;

//Provider必须为public，否则ProviderSqlSource can not access a member of class cn.wanghaomiao.dao.mybatis.LjHouseXiaoquProvider with modifiers ""
public class LjHouseXiaoquProvider {
	public LjHouseXiaoquProvider(){
		super();
	}
	
	public String update(final LjHouseXiaoqu lj) {
		return new SQL() {
			{
				UPDATE("ljhouse_xiaoqu");
				if (lj.getTotalPage() != null && !"".equals(lj.getTotalPage())) {
					SET("totalPage = #{totalPage}");
				}
				if (lj.getFetchPage() != null && !"".equals(lj.getFetchPage())) {
					SET("fetchPage = #{fetchPage}");
				}
				if (lj.getId() != null) {
					WHERE("id = #{id}");
				} else {
					WHERE("rid = #{rid}");
				}
			}
		}.toString();
	}
}