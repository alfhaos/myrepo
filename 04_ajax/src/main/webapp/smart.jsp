<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <script src = "<%=  request.getContextPath()%>/js/jquery-3.6.0.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스마트 가전센터</title>
<style>
div.container{
	float: left;
	width: 29%;
	height:300px;
    margin: 10px;
    padding: 10px;
	background:lightgrey;
	text-align:center;
}
table {
	border:1px solid;
	margin:auto;
}
td,th {
	border:1px solid;
}
span#time{
	text-decoration:underline;
	margin: 15px;
    display: block;
}
</style>
</head>
<body>
<h1>스마트 가전 센터 주문페이지</h1>
<p>
	1. ajax를 이용해서 제품주문을 처리하고, 실시간 판매현황(최근5건)에 출력하세요. <br />
	2. 10초마다 판매랭킹리스트(상위5개제품과 누적판매량)를 갱신하는 ajax함수를 작성하세요. <br />
	(bonus) 현재시각을 표시하세요.
</p>
<div id="order-container" class="container">
	<h2>주문</h2>
	<table>
		<tr>
			<th>제품명</th>
			<td>
				<select name="pname" id="pname" required>
					<option value="iPhoneX">iPhoneX</option>
					<option value="iPhone7">iPhone7</option>
					<option value="iPhone7Plus">iPhone7Plus</option>
					<option value="GalaxyNote8">GalaxyNote8</option>
					<option value="Galaxy8">Galaxy8</option>
					<option value="샤오미맥스2">샤오미맥스2</option>
					<option value="LGV30">LGV30</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>주문량</th>
			<td><input type="number" id="amount" name="amount" min="1" value="1" required></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" id="btn-order" value="주문" />
			</td>
		</tr>
	</table>
	
	<script>
	$("#btn-order").click((e) => {
		
		$.ajax({
			url : "<%= request.getContextPath() %>/smart/smartList.do",
			data : {
				pname : $(pname).val(),
				amount : $(amount).val()
			},
			method: "POST",
			dataType : "json",
			success(data){
				
				const $div = $("#order-list").empty();
				const table = `<table id = "tbl-smart-list">
					<thead>
					<tr>
						<th>제품명</th>
						<th>수량</th>
						<th>제조일자</th>

					</tr>
				</thead>
				
				<tbody>
				
				</tbody>
			</table>`;
			
				$div.append(table);
				
				const $tbody = $("#tbl-smart-list tbody").empty();
				$(data).each((i, {name,amount,date}) =>{
					const tr = `<tr>
						
						<td>\${name}</td>
						<td>\${amount}</td>
						<td>\${date}</td>
						
					</tr>`;
					
					
					$tbody.append(tr);
				
					
				});
				
				
			},
			error : console.log
			
			
		});
		
		
	});
	
	</script>
</div>
<div class="container">
	<h2>주문리스트</h2>
	<div id="order-list"></div>
</div>
<div class="container">
	<h2>인기순위</h2>
	<span id="time"></span>
	<div id="rank-list"></div>
</div>


<script>
	setInterval("smartRankList()",1000);
	
	function smartRankList() {
		var now = new Date();
		var tt = now.getHours() > 12 ? "오후" : "오전";
		$.ajax({
			url : "<%= request.getContextPath() %>/smart/smartRankList.do",
			method: "POST",
			dataType : "json",
			success(data){
				
				$("#time").empty().text(now.getFullYear() + "년 " + (now.getMonth()+1) + "월 " + now.getDate() + "일 " + tt + " " + now.getHours() + "시 " + now.getMinutes() + "분 ");
				
				
				const $div = $("#rank-list").empty();
				const table = `<table id = "tbl-smart-ranklist">
					<thead>
					<tr>
						<th>순위</th>
						<th>제품명</th>
						<th>누적판매량</th>

					</tr>
				</thead>
				
				<tbody>
				
				</tbody>
			</table>`;
			
				$div.append(table);
				const $tbody = $("#tbl-smart-ranklist tbody").empty();
				$(data).each((i, {name,amount,rank}) =>{
					const tr = `<tr>
						
						<td>\${rank}</td>
						<td>\${name}</td>
						<td>\${amount}</td>
						
					</tr>`;
					
					
					$tbody.append(tr);
				
					
				});
				
			},
			error : console.log
		});
	}

</script>

</body>
</html>