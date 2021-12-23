package com.kh.mybatis.student.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.service.IStudentService;

public class StudentListController extends AbstractController {

	// 의존객체
	private IStudentService studentService;
	
	// 의존객체 주입
	public StudentListController(IStudentService studentService) {
		this.studentService = studentService;
//		System.out.println("[StudentListController] studentService = " + studentService.hashCode());
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
		System.out.println("[StudentListController] map = " + map);
		
		// 2. view단 전달
		request.setAttribute("map", map);
		
		return "student/studentList";
	}
}
