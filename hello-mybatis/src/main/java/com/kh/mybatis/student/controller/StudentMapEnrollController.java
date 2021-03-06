package com.kh.mybatis.student.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.service.IStudentService;

public class StudentMapEnrollController extends AbstractController {

	private IStudentService studentService;

	public StudentMapEnrollController(IStudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.사용자입력값
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		
		Map<String, Object> studentMap = new HashMap<>();
		studentMap.put("name", name);
		studentMap.put("tel", tel);
		System.out.println("[StudentMapEnrollController] studentMap = " + studentMap);
		
		// 2.업무로직
		int result = studentService.insertStudent(studentMap);
		String msg = (result > 0) ? "학생Map 등록 성공!" : "학생Map 등록 실패!";
		request.getSession().setAttribute("msg", msg);
		
		return "redirect:/student/studentEnroll.do";
	}
	
	
	
	
	
	
	
}
