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
select * from t_xiaoqumonthprice t;
#按月按小区统计均价增加环比同比t_xiqoqumonthprice2
create table t_xiaoqumonthprice2 as
select a.dealmonth,a.positionInfo1,a.positionInfo2,a.title,a.chengjiaonum,
       a.unitPrice,b.unitPrice unitPrice_preMonth,c.unitPrice unitPrice_preYear,
       (a.unitPrice-b.unitPrice)/b.unitPrice*100 month2month,(a.unitPrice-c.unitPrice)/c.unitPrice*100 year2year
  from (select t.*,str_to_date(t.dealmonth,'%Y.%m') monthd from t_xiaoqumonthprice t) a,
       (select t.*,str_to_date(t.dealmonth,'%Y.%m') monthd from t_xiaoqumonthprice t) b,
       (select t.*,str_to_date(t.dealmonth,'%Y.%m') monthd from t_xiaoqumonthprice t) c
 where ((year(a.monthd) - year(b.monthd))*12+(month(a.monthd) - month(b.monthd)) = 1)  and a.rid = b.rid
   and ((year(a.monthd) - year(c.monthd))*12+(month(a.monthd) - month(c.monthd)) = 12) and a.rid = c.rid
 order by a.dealmonth desc;

select m.month,m.positionInfo1,m.positionInfo2,ifnull(n.unitPrice,'') unitPrice
  from t_xiaoqumonth m left join t_xiaoqumonthprice2 n 
    on (m.positionInfo1=n.positionInfo1 and m.positionInfo2=n.positionInfo2 and m.month=n.dealmonth and m.title=n.title)
 where m.month > '2013.01' and m.title in ('建欣苑二里') 
 order by month;
#------------------
#按月按社区统计均价t_shequmonthprice
create table t_shequmonthprice as
select d.id,SUBSTR(c.dealDate FROM 1 FOR 7) dealmonth,x.positionInfo1,x.positionInfo2,
       count(*) chengjiaonum,ceil(avg(c.unitPrice)) unitPrice,
       ceil(avg(case when c.roomMainInfo like '2室%' then c.unitPrice else null end)) unitPrice2ju
  from ljhouse_chengjiao3 c, ljhouse_xiaoqu x, ljhouse_district d
 where c.rid = x.rid and x.positionInfo1 = d.name
 group by d.id,x.positionInfo1,x.positionInfo2,SUBSTR(c.dealDate FROM 1 FOR 7)
 order by d.id;
alter table t_shequmonthprice add index idx_shequmonth(`dealmonth`,`positionInfo1`,`positionInfo2`);
select * from t_shequmonthprice t;
#按月按社区统计均价按基干表t_shequmonthprice2
create table t_shequmonthprice2 as
select q.id,q.positionInfo1,q.positionInfo2,q.month,ifnull(sum(chengjiaonum),0) chengjiaonum,
       ifnull(ceil(avg(p.unitPrice)),0) unitPrice
  from t_shequmonth q left join t_shequmonthprice p 
    on q.positionInfo1 = p.positionInfo1 and q.positionInfo2 = p.positionInfo2 and q.month = p.dealmonth
 where q.month <= '2017.02'
 group by q.month,q.id,q.positionInfo1,q.positionInfo2;
alter table t_shequmonthprice2 add index idx_shequmonthprice2(`id`,`positionInfo1`,`month`);
select * from t_shequmonthprice2 t;
#按月按社区统计均价带环比同比t_shequmonthprice3
create table t_shequmonthprice3 as
select a.id,a.month,a.positionInfo1,a.positionInfo2,a.chengjiaonum,
       a.unitPrice,b.unitPrice unitPrice_preMonth,c.unitPrice unitPrice_preYear,
       (a.unitPrice-b.unitPrice)/b.unitPrice*100 month2month,(a.unitPrice-c.unitPrice)/c.unitPrice*100 year2year
  from (select t.*,str_to_date(t.month,'%Y.%m') monthd from t_shequmonthprice2 t) a 
       left join
       (select t.*,str_to_date(t.month,'%Y.%m') monthd from t_shequmonthprice2 t) b 
       on ((year(a.monthd) - year(b.monthd))*12+(month(a.monthd) - month(b.monthd)) = 1) 
           and a.id = b.id and a.positionInfo1 = b.positionInfo1 and a.positionInfo2 = b.positionInfo2
       left join
       (select t.*,str_to_date(t.month,'%Y.%m') monthd from t_shequmonthprice2 t) c
       on ((year(a.monthd) - year(c.monthd))*12+(month(a.monthd) - month(c.monthd)) = 12) 
           and a.id = c.id and a.positionInfo1 = c.positionInfo1 and a.positionInfo2 = c.positionInfo2
 order by a.month desc,a.id;
select * from t_shequmonthprice3 t where t.positionInfo2 in ('天宫院','牛街');

select t.month,t.positionInfo1,t.positionInfo2,t.chengjiaonum,t.unitPrice,format(t.month2month,1),format(t.year2year,1)
  from t_shequmonthprice3 t 
 where t.chengjiaonum > 0
 order by t.positionInfo1,t.positionInfo2,t.month desc,t.year2year desc;

select t.month,
       group_concat(concat(positionInfo1,positionInfo2,lpad(t.unitPrice,6,' ')) order by t.month,t.positionInfo1 desc separator ', ')
  from t_shequmonthprice3 t 
 group by t.month
 order by t.month desc;
#------------------
#按月按区统计均价t_qumonthprice
create table t_qumonthprice as
select d.id,a.* from 
(
select q.name,q.month,ifnull(sum(chengjiaonum),0) chengjiaonum,ifnull(ceil(avg(p.unitPrice)),0) unitPrice
  from t_qumonth q left join t_xiaoqumonthprice p 
    on q.name = p.positionInfo1 and q.month = p.dealmonth
 where q.month <= '2017.02'
 group by q.name,q.month
) a,ljhouse_district d
where a.name = d.name
order by d.id,a.month desc;
select * from t_qumonthprice t;
#按月按区统计均价带环比同比t_qumonthprice2
create table t_qumonthprice2 as
select a.id,a.month,a.name,a.chengjiaonum,
       a.unitPrice,b.unitPrice unitPrice_preMonth,c.unitPrice unitPrice_preYear,
       (a.unitPrice-b.unitPrice)/b.unitPrice*100 month2month,(a.unitPrice-c.unitPrice)/c.unitPrice*100 year2year
  from (select t.*,str_to_date(t.month,'%Y.%m') monthd from t_qumonthprice t) a
       left join 
       (select t.*,str_to_date(t.month,'%Y.%m') monthd from t_qumonthprice t) b
       on ((year(a.monthd) - year(b.monthd))*12+(month(a.monthd) - month(b.monthd)) = 1)  and a.id = b.id
       left join 
       (select t.*,str_to_date(t.month,'%Y.%m') monthd from t_qumonthprice t) c
       on ((year(a.monthd) - year(c.monthd))*12+(month(a.monthd) - month(c.monthd)) = 12) and a.id = c.id
 order by a.month desc,a.id;
select * from t_qumonthprice2 t;

#按月统计各区均价报表
select month, group_concat(lpad(unitPrice,6,' ') order by t.id separator ', ') 
       '#西城#, #东城#, #海淀#, #朝阳#, #石景山#, #丰台#, #通州#, #门头沟#, #顺义#, #大兴#,#房山#, #昌平#, #亦庄#, #燕郊#'
from t_qumonthprice2 t
group by t.month
order by t.month desc;
#按月统计各区均价报表带环比同比
select month, group_concat(concat(lpad(unitPrice,6,' '),'|',lpad(format(t.month2month,1),4,' '),'|',lpad(format(t.year2year,1),4,' ')) order by t.id separator ', ') 
       '——#西城#——,  ——#东城#—— ,  ——#海淀#——,  ——#朝阳#——, ——#石景山#——,——#丰台#——, ——#通州#——, ——#门头沟#——,——#顺义#——, ——#大兴#——, ——#房山#——, ——#昌平#——,  ——#亦庄#——,  ——#燕郊#——'
from t_qumonthprice2 t
group by t.month
order by t.month desc;
#----------------------------------------------------------
#创建基表T200
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
#按月生成时间列表t_month
create table if not exists t_month as
select date_format(date_add(min,interval t200.id-1 month),'%Y.%m') month
 from (select str_to_date('2011.1.1','%Y.%m.%d') min,str_to_date('2017.4.1','%Y.%m.%d') max) t,
       t200
where date_add(min,interval t200.id-1 month) <= max;
#按区按月生成基干表t_qumonth
create table t_qumonth as
select q.name,y.month from 
ljhouse_district q,t_month y
order by q.id,y.month;
#按社区按月生成基干表t_shequmonth
create table t_shequmonth as
select sq.*,y.month from 
(
select distinct d.id,positionInfo1,positionInfo2 
  from ljhouse_xiaoqu x,ljhouse_district d
 where x.positionInfo1=d.name
) sq,t_month y
order by sq.id,y.month;
#按社区按月生成基干表t_xiaoqumonth
create table t_xiaoqumonth as
select xq.*,y.month from 
(
select distinct id,positionInfo1,positionInfo2,title
  from ljhouse_xiaoqu x
) xq,t_month y
order by xq.id,y.month;
#--------------------------------------------------------------
select * from ljhouse_xiaoqu t where t.fetchPage = 3 and t.totalPage > t.fetchPage;#1989
select * from ljhouse_chengjiao3 t where t.update_time > str_to_date('2017-3-14 12:25:0','%Y-%m-%d %H:%i:%s');

#---------------------------test-------------------------------
select sum(id),avg(id),avg(case when id>5 then null else id end) from t200 where id < 10;
select replace('120平米','平米','');
select (year(max) - year(min))*12+(month(max) - month(min)) monthdiff
  from (select str_to_date('2010.11','%Y.%m') min,str_to_date('2011.1.11','%Y.%m.%d') max) t;

