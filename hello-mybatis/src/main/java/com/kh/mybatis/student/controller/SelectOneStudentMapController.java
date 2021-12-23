package com.kh.mybatis.student.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.service.IStudentService;

public class SelectOneStudentMapController extends AbstractController {

	private IStudentService studentService;

	public SelectOneStudentMapController(IStudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.사용자입력값
		int no = Integer.valueOf(request.getParameter("no"));
		System.out.println("[SelectOneStudentMapController] no = " + no);
		
		// 2.업무로직
		Map<String, Object> studentMap = studentService.selectOneStudentMap(no);
		System.out.println("[SelectOneStudentMapController] studentMap = " + studentMap);
		
		// 3.응답처리 : json문자열을 응답메세지 출력
		response.setContentType("application/json; charset=utf-8");
		Gson gson = new Gson();
		gson.toJson(studentMap, response.getWriter());
		
		return null;
	}
	
	
	
}
