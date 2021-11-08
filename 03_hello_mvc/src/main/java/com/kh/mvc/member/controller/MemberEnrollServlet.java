package com.kh.mvc.member.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mvc.member.model.service.MemberService;
import com.kh.mvc.member.model.vo.Member;
import com.kh.mvc.member.model.vo.MemberEnroll;

/**
 * Servlet implementation class MemberEnrollServlet
 */
@WebServlet("/member/memberEnroll")
public class MemberEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * GET /mvc/member/memberEnroll
	 * 
	 * - 회원가입폼을 제공
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		request
			.getRequestDispatcher("/WEB-INF/views/member/memberEnroll.jsp")
			.forward(request,response);
	}

	/**
	 * Post /mvc/member/memberEnroll
	 * - DB에 레코드를 기록
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		
		// 1. 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		try {
		// 2. 사용자 입력값 처리
		String memberId = request.getParameter("memberId");
		String password = request.getParameter("password");
		String memberName = request.getParameter("memberName");
		String strbirthday = request.getParameter("birthday");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		String[] arr = request.getParameterValues("hobby");
		
		String hobby = (arr != null) ? String.join(",", arr) : "";
		
		// birthday : String -> java.sql.Date
		Date birthday = "".equals(strbirthday) ? null : Date.valueOf(strbirthday);
		
	
	
		
		
		MemberEnroll member = new MemberEnroll(memberId,password,memberName,birthday,email,phone,address,gender,hobby);
		//MemberEnroll member = new MemberEnroll(memberId,password,memberName,MemberService.USER_ROLE,birthday,email,phone,address,gender,hobby);
		
		// 3.업무로직 service객체의 insertMember호출 & 생성한 member객체 전달
		int result = MemberService.insertMember(member);
		String msg = result > 0 ? "회원가입성공!" : "회원가입 실패!";
		
		session.setAttribute("msg", msg);
		
	
		// 4.redirect 및 msg처리
		
		String location = request.getContextPath() + "/";
		response.sendRedirect(location);
		} catch(Exception e) {
			e.printStackTrace();
			throw e; //tomcat이 error.jsp로 위임하도록 처리
		}
	}

}
