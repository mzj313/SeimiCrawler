CREATE TABLE `area` (
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '行政代码',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `pcode` int(11) DEFAULT NULL COMMENT '父代码',
  `level` int(11) DEFAULT NULL COMMENT '城市等级',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-----------------------------------------------------------------
select a.*,
       case when code1='00' and code2='00' then '000000'  #省
            when code1!='00' and code2='00' then concat(code0,'00','00') #市
            when code1!='00' and code2!='00' then concat(code0,code1,'00') #县
       end pcode,
       case when code1='00' and code2='00' then 1  #省
            when code1!='00' and code2='00' then 2 #市
            when code1!='00' and code2!='00' then 3 #县
       end level
 from (
select t.code,t.name,substr(t.code,1,2) code0,substr(t.code,3,2) code1,substr(t.code,5,2) code2 from area t
) a
;
update area t set pcode=(case when substr(t.code,3,2)='00' and substr(t.code,5,2)='00' then '000000'  #省
            when substr(t.code,3,2)!='00' and substr(t.code,5,2)='00' then concat(substr(t.code,1,2),'00','00') #市
            when substr(t.code,3,2)!='00' and substr(t.code,5,2)!='00' then concat(substr(t.code,1,2),substr(t.code,3,2),'00') #县
       end),level=(case when substr(t.code,3,2)='00' and substr(t.code,5,2)='00' then 1  #省
            when substr(t.code,3,2)!='00' and substr(t.code,5,2)='00' then 2 #市
            when substr(t.code,3,2)!='00' and substr(t.code,5,2)!='00' then 3 #县
       end)
;