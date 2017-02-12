http://seimi.wanghaomiao.cn/

	
SHOW TABLE STATUS;#Auto_increment当前该表的最大自增ID
#修改自增id
alter table ljhouse_chengjiao AUTO_INCREMENT=3481;
delete from ljhouse_chengjiao where id>=3481;


#找空缺id
SELECT a.id FROM ljhouse_chengjiao a 
where not exists(select 1 from ljhouse_chengjiao b where b.id=a.id+1)
;#1980 2480
SELECT b.id FROM ljhouse_chengjiao b 
where not exists(select 1 from ljhouse_chengjiao a where b.id=a.id+1)
;#1985 2499

#去重
delete from ljhouse_chengjiao where id in (select id from (
	select *
	from ljhouse_chengjiao t
	where exists(select 1 from ljhouse_chengjiao c 
								where c.id<t.id and c.title=t.title 
									and c.totalPrice=t.totalPrice
									and c.unitPrice=t.unitPrice
									and c.areaMainInfo=t.areaMainInfo
                  and c.community=t.community
	)
  and t.id >= 3481
) a);
