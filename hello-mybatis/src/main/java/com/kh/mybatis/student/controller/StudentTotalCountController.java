package com.kh.mybatis.student.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.service.IStudentService;

public class StudentTotalCountController extends AbstractController {

	// 의존객체
	private IStudentService studentService;
	
	// 의존객체 주입
	public StudentTotalCountController(IStudentService studentService) {
		this.studentService = studentService;
	}
	
	
	/**
	 * select count(*) from student
	 * - Map<String, Object>
	 * - int
	 */
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 업무로직 : 전체 학생수 조회
		Map<String, Object> map = studentService.selectTotalCount();
		System.out.println("[StudentTotalCountController] map = " + map);
		
		// 2. 응답메세지 작성
		response.setContentType("application/json; charset=utf-8");
		new Gson().toJson(map, response.getWriter());
		
		return null;
	}
}
