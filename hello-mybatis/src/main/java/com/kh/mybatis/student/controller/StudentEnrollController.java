package com.kh.mybatis.student.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.service.IStudentService;
import com.kh.mybatis.student.model.vo.Student;

public class StudentEnrollController extends AbstractController {

	private IStudentService studentService;
	
	public StudentEnrollController(IStudentService studentService) {
		this.studentService = studentService;
		System.out.println("[StudentEnrollController] studentService = " + studentService.hashCode());
	}
	
	
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[StudentEnrollController]");
		return "student/studentEnroll";
	}


	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.사용자입력값 처리
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		Student student = new Student();
		student.setName(name);
		student.setTel(tel);
		
		// 2.업무로직
		int result = studentService.insertStudent(student);
		String msg = result > 0 ? "학생 등록 성공!" : "학생 등록 실패!";
		HttpSession session = request.getSession();
		session.setAttribute("msg", msg);
		
		// 3.응답처리
		return "redirect:/student/studentEnroll.do";
	}
	
	

	
}
