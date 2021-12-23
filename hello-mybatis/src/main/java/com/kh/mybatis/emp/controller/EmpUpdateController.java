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

public class EmpUpdateController extends AbstractController {

	private EmpService empService;

	
	
	public EmpUpdateController(EmpService empService) {
		super();
		this.empService = empService;
	}



	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String,Object> param = new HashMap<>();
		param.put("empId", request.getParameter("empId"));
		
		
		// 2. 업무로직 : 사원 1명정보
		Map<String, Object> empMap = empService.selectOneEmpMap(param);
		
		// 직급코드 목록
		List<Map<String, String>> jobCodeList = empService.selectJobList();
		System.out.println("[EmpUpdateontroller] jobList =  " + jobCodeList);
		
		// 부서코드 목록
		List<Map<String, String>> deptList = empService.selectDeptList();
		System.out.println("[EmpUpdateontroller] deptList =  " + deptList);
		
		
		
		// 3. jsp위임
		request.setAttribute("empMap", empMap);
		request.setAttribute("jobCodeList", jobCodeList);
		request.setAttribute("deptList", deptList);
		
		return "emp/empUpdate";
		
	}



	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 사용자 입력값
		Map<String,Object> param = new HashMap<>();
		param.put("empId", request.getParameter("empId"));
		param.put("jobCode", request.getParameter("jobCode"));
		param.put("deptCode", request.getParameter("deptCode"));
		System.out.println("[EmpUpdateController] Post - param = " + param);
		
		
		// 2. 업무로직 : 수정
		int result = empService.updateEmp(param);
		
		return "redirect:/emp/empUpdate.do?empId=" + param.get("empId");
	}
	
	
	
	
}
