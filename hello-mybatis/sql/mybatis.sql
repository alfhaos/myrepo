--=============================================
-- hello-mybatis
--=============================================
-- student table 생성
create table student(
    no number,
    name varchar2(50) not null,
    tel char(11) not null,
    reg_date date default sysdate,
    constraint pk_student_no primary key(no)
    
);



--=============================================
-- web 계정에서 kh 계정의 일부테이블 사용하기 
--=============================================

select * from kh.employee;

select * from kh.department;

select * from kh.job;

-- kh계정 권한 부여
grant select on kh.employee to web;

grant select on kh.department to web;

grant select on kh.job to web;


-- synonym 동의어객체
-- 별칭객체
-- create synonym은 resource롤에 포함되지 않는 권한
create synonym emp for kh.employee;


-- system계정
grant create synonym to web;



			select
			job_code as "jobCode",
			job_name as "jobName"
		from
			kh.job
		order by
			job_code asc;

