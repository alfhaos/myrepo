package com.kh.mvc.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mvc.member.model.service.MemberService;

/**
 * Servlet implementation class MemberDelete
 */
@WebServlet("/member/memberDelete")
public class MemberDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 인코딩은 필터로 인해 생략
		
		// 2. 사용자 입력값 가져요기
	
		
		String memberId = request.getParameter("memberId");
		
		// 3.업무로직 요청 : 서비스객체의 updateMember호출 & Member객체 전달
		int result = MemberService.deleteMember(memberId);
		String msg = result > 0 ? "회원 삭제 성공" : "회원 삭제 실패";
		
		// 4. 리다이렉트 하기
		HttpSession session = request.getSession();
		if(result > 0) {
			session.removeAttribute("loginMember");
			session.setAttribute("msg", msg);
			
	//		String location = request.getContextPath() + "/member/logout";
	//		response.sendRedirect(location);
			
			//saveId cookie 제거
			Cookie c = new Cookie("saveId",memberId);
			c.setPath(request.getContextPath());
			c.setMaxAge(0);	// 쿠키의 유효기간 0 => 삭제
			response.addCookie(c);
		}
		else {
			session.setAttribute("msg","회원정보 삭제에 실패했습니다.");
		}
		response.sendRedirect(request.getContextPath() + "/");
		
		
		
		
		
		
	}

}
