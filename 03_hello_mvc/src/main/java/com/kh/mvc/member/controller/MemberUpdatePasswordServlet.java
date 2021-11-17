package com.kh.mvc.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mvc.member.model.service.MemberService;
import com.kh.mvc.member.model.vo.Member;

/**
 * Servlet implementation class MemberUpdatePasswordServlet
 */
@WebServlet("/member/updatePassword")
public class MemberUpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request
		.getRequestDispatcher("/WEB-INF/views/member/updatePassword.jsp")
		.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.인코딩처리
		request.setCharacterEncoding("utf-8");
		
		// 2. 사용자 입력처리
		int result;
		String dbPwd,msg;
		String inputOldPwd = request.getParameter("oldPassword");
		String inputNewPwd = request.getParameter("newPassword");
		String memberId = request.getParameter("memberId");
		
		
		// 3. 업무로직
		HttpSession session = request.getSession();
		dbPwd = MemberService.selectMemberPassword(memberId);
		
		// jsp에 input태그 안보이게 따로 안하고 그냥 세션에서 로그인 정보 가져올수 있음
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		
		System.out.println("입력한 패스워드    : " + inputOldPwd);
		System.out.println("디비에 있는 패스워드 : " + dbPwd);
		
		
		if(dbPwd.equals(inputOldPwd)) {
			result = MemberService.updateMemberNewPassword(memberId,inputNewPwd);
			msg = result > 0 ? "비밀번호 업데이트 성공" : "비밀번호 업데이트 실패";
			session.setAttribute("msg", msg);
		}
		else {
			msg = "기존비밀번호가 틀리셨습니다.";
			session.setAttribute("msg", msg);
			
			
		}
		
		// 4. 리다이렉트
		String location = request.getContextPath() + "/member/memberView";
		response.sendRedirect(location);
	}

}
