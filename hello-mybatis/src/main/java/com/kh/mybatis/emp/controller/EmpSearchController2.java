package com.kh.mybatis.emp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.emp.model.service.EmpService;


/**
 * interface - 구현클래스
 * 1. IStudentService - StudentService
 * 2. StudentService - StudentServiceImpl
 * 
 * @author gksqu
 *
 */
public class EmpSearchController2 extends AbstractController {

	private EmpService empService;

	public EmpSearchController2(EmpService empService) {
		this.empService = empService;
		System.out.println("[EmpSearchController1] empService = " + empService.hashCode());
	}

	/**
	 * select * from emp 
	 */
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 1. 사용자 입력값
		Map<String,Object> param = new HashMap<>();
		param.put("searchType", request.getParameter("searchType"));
		param.put("searchKeyword", request.getParameter("searchKeyword"));
		param.put("gender", request.getParameter("gender"));
		param.put("salary", request.getParameter("gender"));
		param.put("salaryCompare", request.getParameter("salaryCompare"));
		param.put("hireDate", request.getParameter("hire_date"));
		param.put("hireDateCompare", request.getParameter("hiredateCompare"));
		System.out.println("[EmpSearchController2] param = " + param);
		// 2. 업무로직
		List<Map<String,Object>> list = empService.search2(param);
		System.out.println("[EmpSearchController2] list = " + list);
		
		// 3. jsp위임
		request.setAttribute("list", list);
		
		return "emp/search2";
	}
	

	
	
}
