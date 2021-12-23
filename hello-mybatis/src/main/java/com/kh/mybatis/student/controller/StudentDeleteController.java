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

public class StudentDeleteController extends AbstractController {

	private IStudentService studentService;

	public StudentDeleteController(IStudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1. 사용자 입력값처리
		int no = Integer.valueOf(request.getParameter("no"));
		//2. 업무로직
		int result = studentService.deleteStudent(no);
		//3. 사용자 피드백: json 응답 전송
		response.setContentType("application/json; charset=utf-8");
		Map<String, String> map = new HashMap<>();
		map.put("msg", "학생 삭제 성공!");
		new Gson().toJson(map, response.getWriter());
		return null;
	}
	
	
}
