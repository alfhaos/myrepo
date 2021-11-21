package com.kh.ajax.smart.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.ajax.smart.model.service.SmartService;
import com.kh.ajax.smart.model.vo.Smart;

/**
 * Servlet implementation class JsonSmartListServlet
 */
@WebServlet("/smart/smartList.do")
public class JsonSmartListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SmartService smartService = new SmartService();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 1. 사용자 입력값 처리
		String pname = request.getParameter("pname");
		int amount = Integer.parseInt(request.getParameter("amount"));
		
		
		// 2. 업무로직
		int result;
		Smart smart = new Smart(pname,amount);
		
		result = smartService.smartEnroll(smart);
		
		String msg = result > 0 ? "등록성공" : "등록실패";
		
		
		
		
		List<Smart> list = new ArrayList<>();
		
		list = smartService.smartList();
	
		
	
		
		System.out.println("[JsonSmartList@Servlet] smart = " + list);
		
		response.setContentType("application/json; charset=utf-8");
		new Gson().toJson(list, response.getWriter());
	}

}
