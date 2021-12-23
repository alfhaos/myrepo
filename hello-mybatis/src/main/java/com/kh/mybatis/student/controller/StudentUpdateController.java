package com.kh.mybatis.student.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.service.IStudentService;
import com.kh.mybatis.student.model.vo.Student;

public class StudentUpdateController extends AbstractController {

	private IStudentService studentService;

	public StudentUpdateController(IStudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1. 사용자입력값 처리
		int no = Integer.parseInt(request.getParameter("no"));
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setTel(tel);
		System.out.println("[StudentUpdateController] student = " + student);
		
		//2. 업무로직
		int result = studentService.updateStudent(student);
		
		//3. 사용자피드백
		response.setContentType("application/json; charset=utf-8");
		Map<String, Object> map = new HashMap<>();
		map.put("msg", "사용자 정보 수정 성공!");
		new Gson().toJson(map, response.getWriter());

		return null;
	}
	
	
}
