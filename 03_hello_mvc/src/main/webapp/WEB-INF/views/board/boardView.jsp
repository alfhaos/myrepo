<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ page import = "java.util.List" %>
<%@page import="com.kh.mvc.board.model.vo.Attachment"%> 
<%@page import="com.kh.mvc.board.model.vo.BoardComment"%> 
<%
	Board board = (Board) request.getAttribute("board");
	
	
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/board.css" />
<section id="board-container">
	<h2>게시판</h2>
	<table id="tbl-board-view">
		<tr>
			<th>글번호</th>
			<td><%= board.getNo() %></td>
		</tr>
		<tr>
			<th>제 목</th>
			<td><%= board.getTitle() %></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><%= board.getWriter() %></td>
		</tr>
		<tr>
			<th>조회수</th>
			<td><%= board.getReadCount() %></td>
		</tr>
		<%
		List<Attachment> attachments = board.getAttachments();
		if(attachments != null && !attachments.isEmpty()){
			for(int i = 0; i< attachments.size(); i++){
				Attachment attach = attachments.get(i);
	
		%>
		<tr>
			<th>첨부파일</th>
			<td>
				<%-- 첨부파일이 있을경우만, 이미지와 함께 original파일명 표시 --%>
		
						<img alt="첨부파일" src="<%=request.getContextPath() %>/images/file.png" width=16px>
						<a href="<%=request.getContextPath() %>/board/fileDownload?no=<%= attach.getNo() %>"><%= attach.getOriginalFilename() %></a>
				
			</td>
		</tr>
		<%
			}
		}
		%>
		<tr>
			<th>내 용</th>
			<td><%= board.getContent() %></td>
		</tr>
		<tr>
			<%-- 작성자와 관리자만 마지막행 수정/삭제버튼이 보일수 있게 할 것 --%>
				<% 	if(
				loginMember != null && 
				(loginMember.getMemberId().equals(board.getWriter()) || MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole()))
			){ %>
			<th colspan="2">
				<input type="button" value="수정하기" onclick="updateBoard()">
				<input type="button" value="삭제하기" onclick="deleteBoard()">
			</th>
			<%} %>
		</tr>
	</table>
	
	
	<hr style="margin-top:30px;" />	
    
	<div class="comment-container">
        <div class="comment-editor">
            <form 
            	action="<%=request.getContextPath()%>/board/boardCommentEnroll"
             	method="post" 
             	name="boardCommentFrm">
                <input type="hidden" name="boardNo" value="<%= board.getNo() %>" />
                <input type="hidden" name="writer" value="<%= loginMember != null ? loginMember.getMemberId() : ""  %>" />
                <input type="hidden" name="commentLevel" value="1" />
                <input type="hidden" name="commentRef" value="0" />    
				<textarea name="content" cols="60" rows="3" style="resize : none;"></textarea>
                <button type="submit" id="btn-comment-enroll1">등록</button>
            </form>
        </div>

	</div>
	
	
	
	<!-- table#tbl-comment -->
	<%
	List<BoardComment> commentList = (List<BoardComment>) request.getAttribute("commentList");
	if(commentList != null && !commentList.isEmpty()){
	
	%>
	<table id = "tbl-comment">
	<%
		for(BoardComment bc : commentList){
			if(bc.getCommentLevel() == 1){
	%>
		<tr class ="level1">
			<td>
				<sub class="comment-writer"><%= bc.getWriter() %></sub>
				<sub class="comment-date"><%= bc.getRegDate() %></sub>
				<br />
				<%-- 댓글내용 --%>
					<%= bc.getContent() %>
			</td>
			<td>
				<button class="btn-reply" value ="<%= bc.getNo() %>">답글</button>
				<% if(loginMember.getMemberId().equals(bc.getWriter()) || MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole())) {%>
					<button type="submit" class="btn-delete" value ="<%= bc.getNo() %>">삭제</button>
				<%} %>
			</td>
		</tr>
		<% } else{%>
		<tr class = "level2">
			<td>
				<sub class="comment-writer"><%= bc.getWriter() %></sub>
				<sub class="comment-date"><%= bc.getRegDate() %></sub>
				<br />
				<%-- 대댓글내용 --%>
				<%= bc.getContent() %>
				<% if(loginMember.getMemberId().equals(bc.getWriter()) || MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole())) {%>
				
					<button class="btn-delete" value ="<%= bc.getNo() %>" name = "btnDel">삭제</button>
				<%} %>
			</td>
			
		</tr>
		
		<%
			}
		}
		%>
		<td></td>
	</table>
	
	<%
		}
	%>
	
</section>
<form
	name = "boardDelFrm"
	method = "POST" 
	action="<%= request.getContextPath() %>/board/boardDelete">
	<input type="hidden" name = "no" value = "<%= board.getNo() %>" />
	</form>
	<form
	name = "boardUpdateFrm"
	method = "GET" 
	action="<%= request.getContextPath() %>/board/boardUpdate">
	<input type="hidden" name = "no" value = "<%= board.getNo() %>" />
	</form>
	<form
	name = "boardCommentDelFrm"
	method = "POST" 
	action="<%= request.getContextPath() %>/board/boardCommentDelete">
	<input type="hidden" name = "no" value = "<%= board.getNo() %>" />
	</form>

<script>

/**
 * 대댓글 입력
 * 이벤트발생객체 e.target -> button.btn-reply
 * 
 */
 
 $(".btn-delete").click((e) => {
	 const commentNo = $(e.target).val();
	 const inputNo = `<input type="hidden" name="commentNo" value="\${commentNo}" />`;
	 $(document.boardCommentDelFrm)
	 					.append(inputNo)
	 					.submit();
 });

 
 $(".btn-reply").click((e) => {
	<% if(loginMember == null){%> 
	 	loginAlert();
	 	return;
	 <% } %>
	 
	 const commentRef = $(e.target).val();
	 
	 const tr = `<tr>
	 <td colspan = "2" style = "text-align:left">
	 
	     <form 
		 	action="<%=request.getContextPath()%>/board/boardCommentEnroll"
		  	method="post">
		    <input type="hidden" name="boardNo" value="<%= board.getNo() %>" />
		    <input type="hidden" name="writer" value="<%= loginMember != null ? loginMember.getMemberId() : "" %>" />
		    <input type="hidden" name="commentLevel" value="2" />
		    <input type="hidden" name="commentRef" value="\${commentRef}" />    
			<textarea name="content" cols="60" rows="2"></textarea>
		    <button type="submit" class ="btn-comment-enroll2">등록</button>
	 	</form>
	 
	 </td>
	 
</tr>`;
	console.log(tr);	 
	
	// e.target인 버튼태그의 부모 tr을 찾고, 다음 형제요소로 추가
	const $basedTr = $(e.target).parent().parent();
	const $tr = $(tr);
	$tr
		.insertAfter($basedTr)
		.find("form")
		.submit((e) => {
			const $content = $("[name=content]", e.target);
			if(!/^(.|\n)+$/.test($content.val())){
				alert("댓글을 작성해주세요.");
				e.preventDefault();
				
			}
			
		});
	
	// 클릭이벤트핸들러 제거!
	$(e.target).off("click");
	
 });






$("[name = content]",document.boardComentFrm).focus((e) => {
	
	<% if(loginMember == null){ %>
		loginAlert();
		return;
	<% } %>
});


$(document.boardCommentFrm).submit((e) => {
	
	<% if(loginMember == null){ %>
		loginAlert();
		// e.preventDefault();
		return false;
	<% }%>
	
		const $content = $("[name=content]", e.target);
		if(!/^(.|\n)+$/.test($content.val())){
			alert("댓글을 작성해주세요.");
			e.preventDefault();
			
		}
	
});

const loginAlert = () => {
	alert("로그인후 사용가능합니다.");
	$(memberId).focus();
	
};


const deleteBoard = () => {
	if(confirm("이 게시물을 정말 삭제하시겠습니까?")){
		$(document.boardDelFrm).submit();
		
	}
}

const updateBoard = () => {
	location.href = "<%= request.getContextPath() %>/board/boardUpdate?no=<%= board.getNo() %>";

}

</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
