package com.kh.mvc.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mvc.board.model.service.BoardService;

/**
 * Servlet implementation class BoardCommentDelete
 */
@WebServlet("/board/boardCommentDelete")
public class BoardCommentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int commentNo = Integer.parseInt(request.getParameter("commentNo"));
		int boardNo = Integer.parseInt(request.getParameter("no"));
		
		//2. 업무로적
		int result = BoardService.commentDel(commentNo);
		String msg = result > 0 ? "삭제 성공" : "삭제 실패";
		
		
		request.setAttribute("msg", msg);
		
		String location = request.getContextPath() + "/board/boardView?no=" + boardNo;
		response.sendRedirect(location);
		
	}

}
