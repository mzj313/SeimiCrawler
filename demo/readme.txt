http://seimi.wanghaomiao.cn/

	
SHOW TABLE STATUS;#Auto_increment当前该表的最大自增ID
#修改自增id
alter table ljhouse_chengjiao AUTO_INCREMENT=1801;
delete from ljhouse_chengjiao where id>1800;
