package com.kh.mybatis.student.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.student.model.service.IStudentService;

public class SelectStudentMapListController extends AbstractController {
	
	private IStudentService studentService;

	public SelectStudentMapListController(IStudentService studentService) {
		this.studentService = studentService;
	}

	/**
	 * 1. @mapper <select resultType="map"></select> 
	 * 
	 * 2. @mapper <select resultMap="studentMap"></select> ---> 기존 <resultMap>은 재사용가능
	 */
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 업무로직 : 목록조회
		List<Map<String, Object>> list = studentService.selectStudentMapList();
		System.out.println("[SelectStudentMapListController] list = " + list);
		
		// 2. 응답메세지 작성
		response.setContentType("application/json; charset=utf-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		gson.toJson(list, response.getWriter());
		
		return null;
	}
	
}
