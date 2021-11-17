package com.kh.mvc.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mvc.board.model.service.BoardService;
import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.board.model.vo.BoardComment;
import com.kh.mvc.common.MvcUtils;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/board/boardList")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자입력값
		final int numPerPage = 5;
		int cPage = 1;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {}
		
		int startNum = (cPage - 1) * numPerPage + 1;
		int endNum = cPage * numPerPage;
		
		Map<String, Object> param = new HashMap<>();
		param.put("startNum", startNum);
		param.put("endNum", endNum);
			
		
		
		// 2. 업무로직
		
		
		// 2-a. content 영역 : 페이징 쿼리
		List<Board> list = new ArrayList<>();
		list = boardService.SelectAllAttachment(param);
		
		System.out.println("BoardServlet@list : " +list);
		// 2-b. pagebar 영역 : MvcUtils.getPagebar호출
		// totalContent
		// url
		int totalContent = boardService.selectTotalboardCount();
		
		String url = request.getRequestURI();
		
		String pagebar = MvcUtils.getPagebar(cPage,numPerPage, totalContent, url);
		
		// 3. view단 처리
		request.setAttribute("list", list);
		request.setAttribute("pagebar", pagebar);
		
		request
				.getRequestDispatcher("/WEB-INF/views/board/boardList.jsp")
				.forward(request, response);
		
	}

}
