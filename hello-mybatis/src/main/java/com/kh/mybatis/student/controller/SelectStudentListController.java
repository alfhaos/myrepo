package com.kh.mybatis.student.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.service.IStudentService;
import com.kh.mybatis.student.model.vo.Student;

public class SelectStudentListController extends AbstractController {
	
	private IStudentService studentService;

	public SelectStudentListController(IStudentService studentService) {
		this.studentService = studentService;
	}

	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 업무로직 : 목록조회
		List<Student> list = studentService.selectStudentList();
		System.out.println("[SelectStudentListController] list = " + list);
		
		// 2. 응답메세지 작성
		response.setContentType("application/json; charset=utf-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		gson.toJson(list, response.getWriter());
		
		return null;
	}
	
}
