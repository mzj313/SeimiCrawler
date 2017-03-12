#按月按小区统计均价t_xiaoqumonthprice
create table t_xiaoqumonthprice as
select x.rid,x.title,SUBSTR(c.dealDate FROM 1 FOR 7) dealmonth,x.positionInfo1,x.positionInfo2,
       count(*) chengjiaonum,ceil(avg(c.unitPrice)) unitPrice,
       ceil(avg(case when c.roomMainInfo like '2室%' then c.unitPrice else null end)) unitPrice2ju
  from ljhouse_chengjiao3 c,ljhouse_xiaoqu x
 where c.rid = x.rid
 group by x.rid,x.title,x.positionInfo1,x.positionInfo2,SUBSTR(c.dealDate FROM 1 FOR 7)
 order by c.dealDate;
alter table t_xiaoqumonthprice add index idx_qu_month(`rid`,`dealmonth`,`positionInfo1`,`positionInfo2`);
#按月按小区统计均价增加环比同比t_xiqoqumonthprice2
create table t_xiqoqumonthprice2 as
select a.dealmonth,a.positionInfo1,a.positionInfo2,a.title,
       a.unitPrice,b.unitPrice unitPrice_preMonth,c.unitPrice unitPrice_preYear,
       (a.unitPrice-b.unitPrice)/b.unitPrice*100 month2month,(a.unitPrice-c.unitPrice)/c.unitPrice*100 year2year
  from (select t.*,str_to_date(t.dealmonth,'%Y.%m') monthd from t_xiaoqumonthprice t) a,
       (select t.*,str_to_date(t.dealmonth,'%Y.%m') monthd from t_xiaoqumonthprice t) b,
       (select t.*,str_to_date(t.dealmonth,'%Y.%m') monthd from t_xiaoqumonthprice t) c
 where ((year(a.monthd) - year(b.monthd))*12+(month(a.monthd) - month(b.monthd)) = 1)  and a.rid = b.rid
   and ((year(a.monthd) - year(c.monthd))*12+(month(a.monthd) - month(c.monthd)) = 12) and a.rid = c.rid
 order by a.dealmonth desc;

#按月按区统计均价
create table t_qumonthprice as
select d.id,a.* from 
(
select q.name,q.month,ifnull(sum(chengjiaonum),0) chengjiaonum,ifnull(ceil(avg(p.unitPrice)),0) unitPrice
  from t_qumonth q left join t_xiaoqumonthprice p 
    on q.name = p.qu and q.month = p.dealmonth
 where q.month <= '2017.02'
 group by q.name,q.month
) a,ljhouse_district d
where a.name = d.name
order by d.id,a.month desc;

#按月统计各区均价
select month,
       group_concat(lpad(unitPrice,6,' ') order by t.id separator ', ') 
       '#西城#, #东城#, #海淀#, #朝阳#,#石景山#,#丰台#,#通州#,#门头沟#,#顺义#,#大兴#,#房山#,#昌平#, #亦庄#, #燕郊#'
from 
(
select a.*,d.id 
from t_qumonthprice a,ljhouse_district d
where a.name = d.name
) t
group by t.month
order by t.month desc;

#按月按区统计均价增加同比环比
select a.month,a.name,a.unitPrice,b.unitPrice unitPrice_preMonth,c.unitPrice unitPrice_preYear,
       (a.unitPrice-b.unitPrice)/b.unitPrice*100 m2m,(a.unitPrice-c.unitPrice)/c.unitPrice*100 y2y
  from (select t.*,str_to_date(t.month,'%Y.%m') monthd from t_qumonthprice t) a,
       (select t.*,str_to_date(t.month,'%Y.%m') monthd from t_qumonthprice t) b,
       (select t.*,str_to_date(t.month,'%Y.%m') monthd from t_qumonthprice t) c
 where ((year(a.monthd) - year(b.monthd))*12+(month(a.monthd) - month(b.monthd)) = 1)  and a.name = b.name
   and ((year(a.monthd) - year(c.monthd))*12+(month(a.monthd) - month(c.monthd)) = 12) and a.name = c.name
 order by a.month desc,a.id;
#----------------------------------------------------------
#创建基表
create table T200(`id` int not null,PRIMARY KEY (`id`));
delimiter $$
CREATE PROCEDURE createT200()
BEGIN
DECLARE i INT DEFAULT 1;
WHILE (i<=200) DO
INSERT INTO T200 VALUES(i);
SET i=i+1;
END WHILE;
END$$
call createT200();
#按月生成时间列表
select date_add(min,interval t200.id-1 month)
 from (select str_to_date('2011.1.1','%Y.%m.%d') min,str_to_date('2017.4.1','%Y.%m.%d') max) t,
       t200
where date_add(min,interval t200.id-1 month) <= max;
#按区按月生成基干表t_qumonth
create table t_qumonth as
select q.name,y.month from 
ljhouse_district q,
(
select date_format(date_add(min,interval t200.id-1 month),'%Y.%m') month
 from (select str_to_date('2011.1.1','%Y.%m.%d') min,str_to_date('2017.4.1','%Y.%m.%d') max) t,
       t200
where date_add(min,interval t200.id-1 month) <= max
) y
order by q.id,y.month;

#--------------------------------------------------------------
select * from ljhouse_xiaoqu t where t.fetchPage = 3 and t.totalPage > t.fetchPage;#1989
select * from ljhouse_chengjiao3 t where t.update_time > str_to_date('2017-3-12 18:25:0','%Y-%m-%d %H:%i:%s');

#---------------------------test-------------------------------
select sum(id),avg(id),avg(case when id>5 then null else id end) from t200 where id < 10;
select replace('120平米','平米','');
select (year(max) - year(min))*12+(month(max) - month(min)) monthdiff
  from (select str_to_date('2010.11','%Y.%m') min,str_to_date('2011.1.11','%Y.%m.%d') max) t;

