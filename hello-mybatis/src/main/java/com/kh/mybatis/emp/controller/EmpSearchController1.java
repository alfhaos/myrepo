package com.kh.mybatis.emp.controller;

import java.io.IOException;
import java.sql.Date;
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
public class EmpSearchController1 extends AbstractController {

	private EmpService empService;

	public EmpSearchController1(EmpService empService) {
		this.empService = empService;
		System.out.println("[EmpSearchController1] empService = " + empService.hashCode());
	}

	/**
	 * select * from emp 
	 */
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//0. 사용자입력값
		String searchType = request.getParameter("searchType");
		String searchKeyword = request.getParameter("searchKeyword");
		String gender = request.getParameter("gender");
		int salary = 0;
		try {
			salary = Integer.parseInt(request.getParameter("salary"));
		} catch (NumberFormatException e) {}
		String salaryCompare = request.getParameter("salaryCompare");
		String temp = request.getParameter("hire_date");
		String hiredateCompare = request.getParameter("hiredateCompare");
		
		Date hire_date = null;
		if(temp != null && !"".equals(temp)) 
			hire_date = Date.valueOf(temp);  
		
		Map<String, Object> param = new HashMap<>();
		param.put("searchType", searchType);
		param.put("searchKeyword", searchKeyword);
		param.put("gender", gender);
		param.put("salary", salary);
		param.put("salaryCompare", salaryCompare);
		param.put("hire_date", hire_date);
		param.put("hiredateCompare", hiredateCompare);
		System.out.println("[EmpSearchController1] param = " + param);
		
		//1. 업무로직
		List<Map<String, Object>> list = null;
		list = empService.search1(param);
		if((searchType == null || searchKeyword == null) && gender == null) {
			list = empService.selectEmpMapList();
		}
		else {
			list = empService.search1(param);
		}
		System.out.println("[EmpSearchController1] list = " + list);
		
		//2. jsp 처리
		request.setAttribute("list", list);

		return "emp/search1";
	}
	

	
	
}
