<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<html>
<body>
	<h1>Hello Mybatis</h1>
	<h2>student</h2>
	<ul>
		<li><a href="${pageContext.request.contextPath}/student/studentEnroll.do">학생등록</a></li>
		<li><a href="${pageContext.request.contextPath}/student/studentOne.do">학생조회(1명)</a></li>
		<li><a href="${pageContext.request.contextPath}/student/studentList.do">학생조회(여러명)</a></li>
	</ul>
	
	<h2>emp</h2>
	<ul>
		<li><a href="${pageContext.request.contextPath}/emp/search1.do">/emp/search1.do</a></li>
		<li><a href="${pageContext.request.contextPath}/emp/search2.do">/emp/search2.do</a></li>
		<li><a href="${pageContext.request.contextPath}/emp/search3.do">/emp/search3.do</a></li>
	</ul>
	
</body>
</html>
