<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>Ajax</h1>
	<h2>동기/비동기처리</h2>
	<button id="btn">실행</button>
	
	
	<h2>비동기 통신</h2>
	
	<ul>
		<li><a href="<%= request.getContextPath() %>/html.jsp">html</a></li>
		<li><a href="<%= request.getContextPath() %>/csv.jsp">csv</a></li>
		<li><a href="<%= request.getContextPath() %>/json.jsp">json</a></li>
		<li><a href="<%= request.getContextPath() %>/.jsp">html</a></li>
	</ul>
	<script>
	/**
	* 동기식 synchronous 호출함수의 결과를 기다렸다 실행
	* 비동기식 asynchronous 호출함수의 결과를 기다리지 않고 실행
	* - timer api : setTimeout, setInterval
	* - DOM : event 처리
	* - XMLHttpRequest 객체 처리
	*/
	btn.addEventListener("click", (e) => {
		
		let s = 1;
		
		// s = foo(s); // 동기식 처리
		
		
		//비동기식 처리
		setTimeout(() => {
			s *= 100;
			alert("setTimeout : s = " + s);
		}, 0);
		
		alert(s);
		
		
	});
	
	const foo = (s) => s * 100;
	</script>	
	
</body>
</html>