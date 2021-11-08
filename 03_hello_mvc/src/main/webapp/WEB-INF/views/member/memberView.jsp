<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<section id=enroll-container>
	<h2>회원 정보</h2>
	<form id="memberUpdateFrm" method="post">
		<table>
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" name="memberId" id="memberId" value="<%= loginMember.getMemberId() %>" readonly>
				</td>
			</tr>
			<tr>
				<th>패스워드</th>
				<td>
					<input type="password" name="password" id="password" value="<%= loginMember.getPassword() %>" required>
				</td>
			</tr>
			<tr>
				<th>패스워드확인</th>
				<td>	
					<input type="password" id="passwordCheck" value="<%= loginMember.getPassword() %>" required><br>
				</td>
			</tr> 
			<tr>
				<th>이름</th>
				<td>	
				<input type="text"  name="memberName" id="memberName" value="<%= loginMember.getMemberName() %>"  required><br>
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>	
				<input type="date" name="birthday" id="birthday" value="<%= loginMember.getBirthday() %>"><br>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" placeholder="abc@xyz.com" name="email" id="email" value="<%= loginMember.getEmail() %>"><br>
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td>	
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" value="<%= loginMember.getPhone() %>" required><br>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>	
					<input type="text" placeholder="" name="address" id="address" value="<%= loginMember.getAddress() %>"><br>
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>

			       		 <input type="radio" name="gender" id="gender0" value="M" <%= "M".equals(loginMember.getGender()) ? "checked" : "" %>>
						 <label for="gender0">남</label>
						 <input type="radio" name="gender" id="gender1" value="F" <%= "F".equals(loginMember.getGender()) ? "checked" : "" %>>
						 <label for="gender1">여</label>
	
						
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<input type="checkbox" name="hobby" id="hobby0" value="운동" <%= loginMember.getHobby().contains("운동") ? "checked" : "" %> ><label for="hobby0">운동</label>
					<input type="checkbox" name="hobby" id="hobby1" value="등산" <%= loginMember.getHobby().contains("등산") ? "checked" : "" %> ><label for="hobby1">등산</label>
					<input type="checkbox" name="hobby" id="hobby2" value="독서" <%= loginMember.getHobby().contains("독서") ? "checked" : "" %>><label for="hobby2">독서</label><br />
					<input type="checkbox" name="hobby" id="hobby3" value="게임" <%= loginMember.getHobby().contains("게임") ? "checked" : "" %> ><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="여행" <%= loginMember.getHobby().contains("여행") ? "checked" : "" %> ><label for="hobby4">여행</label><br />


				</td>
			</tr>
		</table>
        <input type="button" onclick="updateMember();" value="정보수정"/>
        <input type="button" onclick="deleteMember();" value="탈퇴"/>
	</form>
</section>
<script>

/**
 *	회원정보 수정폼 제출
 *	/mvc/member/memberUpdate -> redirect(중복요청 방지 - URL변경)
 *	/member/memberView페이지로 리다이렉트할 것!
 * 	DML(POST)방식을 이용할때는 공식처럼 꼭 redirect를 해야한다고 생각하면된다.
 */
 
 const updateMember = () => {
	// 폼의 action값을 할당후 제출!
		$(memberUpdateFrm)
			.attr("action", "<%= request.getContextPath() %>/member/memberUpdate")
			.submit();
 };

 const deleteMember = () => {
	 $(memberUpdateFrm)
		.attr("action", "<%= request.getContextPath() %>/member/memberDelete")
		.submit();
	 
 }


/**
 * name = memberEnrollFrm 유효성검사
 * - id/비번 영문자/숫자 4글자 이상
 * - 이름 한글 2글자 이상
 * - 전화번호 숫자확인
 */
 
 $(document.memberUpdateFrm).submit((e) => {

		
		//password
		const $passoword = $(password);
		const $passwordCheck = $(passwordCheck);
		if(!/^[a-zA-Z0-9!@#$]{4,}$/.test($password.val())){
			alert("유효한 패스워드를 입력하세요.");
			return false;
		}
		if($password.val() != $passwordCheck.val()){
			alert("패스워드가 일치하지 않습니다.");
			return false;
		}
		
		//memberName
		const $memberName = $(memberName);
		if(!/^[가-힣]{2,}$/.test($memberName.val())){
			alert("이름은 한글 2글자 이상이어야 합니다.");
			return false;
		}
		
		//phone
		const $phone = $(phone);

		if(!/^010[0-9]{8}$/.test($phone.val())){
			alert("유효한 전화번호가 아닙니다.");
			return false;
		}
		return true;
	});
 
 
</script>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
