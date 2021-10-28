package com.kh.web;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestPerson3Servlet
 */
@WebServlet("/menu.do")
public class menu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 0. 인코딩 처리
		
		request.setCharacterEncoding("UTF-8");
		
		// 1. 요청처리 : 사용자입력값 -> 자바변수
		

		String mainMenu = request.getParameter("mainMenu");
		String sideMenu = request.getParameter("sideMenu");
		String drinkMenu = request.getParameter("drinkMenu");

		int price = 0;
		
		
		switch(mainMenu) {
		case "한우버거":
			price += 5000;
			break;
		case "밥버거":
			price += 4500;
			break;
		case "치즈버거":
			price += 4000;
			break;
		default:
			break;
		}
		
		switch(sideMenu) {
		case "감자튀김":
			price += 1500;
			break;
		case "어니언링":
			price += 1700;
			break;
		default:
			break;
		}
		switch(drinkMenu) {
		case "콜라":
			price += 1000;
			break;
		case "사이다":
			price += 1000;
			break;
		case "커피":
			price += 1500;
			break;
		case "밀크쉐이크":
			price += 2500;
			break;
		default:
			break;
		}
		
		
		System.out.println("mainMenu = " + mainMenu);
		System.out.println("sideMenu = " + sideMenu);
		System.out.println("drinkMenu = " + drinkMenu);

		
		
		
		// 2. 업무로직 : db조회 + 1000연산
		

		
		// 3. 응답처리 : jsp 위임(RequestDispatcher 객체의 forward)
		// jsp에서 사용할 데이터를 속성으로 저장
		

		request.setAttribute("price", price);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/menuEnd.배포.jsp");
		
		requestDispatcher.forward(request, response);
		
		
		
		
		
		
	}

}
