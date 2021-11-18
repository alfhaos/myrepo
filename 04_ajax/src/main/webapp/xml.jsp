<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src = "<%=  request.getContextPath()%>/js/jquery-3.6.0.js"></script>
 <style>
	table {
		border: 1px solid #000;
		border-collapse: collapse;
		margin-top: 10px;
	}
	th, td {
		border: 1px solid #000;
		padding: 5px;
	}
	table img {
		width: 100px;
	}
    </style>
    
<title>Insert title here</title>
</head>
<body>


	<h1>xml</h1>
	<button id="btn1">실행</button>
	<div class = "celeb-container">
		<table>
			<thead>
				<tr>
					<th>이름</th>
					<th>전화번호</th>
					<th>나이</th>
					<th>프로필</th>
					<th>결혼여부</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>

	
	</div>
	<script>
	$(btn1).on('click', (e) => {
		
		
		$.ajax({
			url : "<%= request.getContextPath() %>/xml/celebList.do",
			dataType : "xml",		// jquery : xml형식의 문자열을 parsing한 후, document객체로 success함수에 전달
			
			success(data){
				console.log(data);
				console.log(typeof data); // object
				
				// 루트태그 찾기
				const $root = $(data).find(":root");
				console.log($root);
				
				const $celebs = $root.find("celeb");
				console.log($celebs);
				
				const $tbody = $(".celeb-container table tbody");
				$tbody.empty(); // 초기화
				
				
				$celebs.each((i, celeb) => {
					const name = $(celeb).children("name").text();
					const phone = $(celeb).children("phone").text();
					const age = $(celeb).children("age").text();
					const profile = $(celeb).children("profile").text();
					const married = $(celeb).children("married").text();
				
					// console.log(phone);

					
					const tr = `<tr>
						
						<td>\${name}</td>
						<td>\${phone}</td>
						<td>\${age}</td>
						<td>
							<img src="\${profile}" alt="" />
						</td>
						<td>
							<input type="checkbox" \${married === 'true' ? 'checked' : ''} />
						</td>
					</tr>`;
					
					console.log(tr);
					
					$tbody.append(tr);
					
					
				});
				
			},
			error : console.log
			
		});
		
	});
	
	</script>
	
	
	<h2>일일 박스오피스 조회</h2>
	<div>
		<input type="date" id = "targetDt" />
	</div>
	
	<div class="boxoffice-container">
		<table>
			<thead>
				<tr>
					<th>순위</th>
					<th>영화제목</th>
					<th>누적관객수(만)</th>
				</tr>
			</thead>
			
			<tbody>
			
			</tbody>
		</table>
	</div>
	
	<script>
	$(targetDt).change((e) => {
		
		const targetDt = $(e.target).val().replaceAll("-","");
		
		
		$.ajax({
			url:"http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml",
			data:{
				key:"b70edffe286e632f06442497c8bbd1a4",
				targetDt : targetDt
			},
			success(data){
				console.log(data);
				
		
				const $list = $(data).find("dailyBoxOfficeList");
				const $boxOfficeList = $list.children();
				console.log($boxOfficeList);
				
				const $tbody = $(".boxoffice-container table tbody");
				$tbody.empty(); // 초기화
				
				
				
				
				$boxOfficeList.each((i, movie) => {
					const mvTitle = $(movie).children("movieNm").text();
					const rank = $(movie).children("rank").text();
					let audiAcc = $(movie).children("audiAcc").text();
					var acc = Math.floor(audiAcc / 10000) < 1 ? "0." + audiAcc % 10000 + "만" : Math.floor(audiAcc / 10000)  + "만" ;
				
			
					// console.log(phone);

					
					const tr = `<tr>
						
						<td>\${rank}</td>
						<td>\${mvTitle}</td>
						<td>\${acc}</td>
					</tr>`;
					
					console.log(tr);
					
					$tbody.append(tr);
					
					
				});
				
				
			},
			error: console.log
			
		});
	});
	
	</script>
	
</body>
</html>