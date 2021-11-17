package com.kh.mvc.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mvc.board.model.exception.BoardException;
import com.kh.mvc.board.model.service.BoardService;
import com.kh.mvc.board.model.vo.Attachment;
import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardViewServlet
 */
@WebServlet("/board/boardView")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			// 1. 사용자 입력값처리
			Board board = null;
			List<Attachment> list = new ArrayList<>();
			int no = Integer.parseInt(request.getParameter("no"));
			// 2. 업무로직
			// 상세보기를 요청하면, 해당글에 대한 boardCookie가 존재 하지 않을때 조회수를 1증가한다.
			// a. 검사
			Cookie[] cookies = request.getCookies();
			boolean hasRead = false;
			String boardCookieVal = "";
			
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					String name = cookie.getName();
					String value = cookie.getValue();
					
					if("boardCookie".equals(name)) {
						boardCookieVal = value; // 기존 쿠키값
						
						if(value.contains("[" + no + "]")) {
							hasRead = true;
							break;
							
						}
					}
					
				}
			}
			// b. 조회수 증가 및 쿠키생성
			if(!hasRead) {
				int result = BoardService.UpdateReadCount(no);
				
				Cookie cookie = new Cookie("boardCookie", boardCookieVal + "[" + no + "]");
				cookie.setPath(request.getContextPath() + "/board/boardView");
				cookie.setMaxAge(365 * 24 * 60 * 60); // 365일짜리 영속 쿠키
				response.addCookie(cookie);
				System.out.println("[BoardViewServlet] 조회수 증가 및 boardCookie 생성");
			}
			
			
		
			
			
			// 2-a. 게시글(board) 조회
			// 2-b. 첨부파일(attachment) 조회
		
				
				
				//board = BoardService.SelectBoardView(no);
				board = BoardService.SelectOneBoardAttachments(no);
				
				// 게시글 가져오기에 실패한경우
				if(board == null) {
					throw new BoardException("해당 게시글이 존재하지 않습니다.");		
				}
				
				// XSS공격 대비 <> 변환처리
				String content = board.getContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
				
				
				// 개행문자 처리
				content = content.replaceAll("\n", "<br/>");
				board.setContent(content);
				
				
				// 댓글 목록 조회
				
				List<BoardComment> commentList =  BoardService.selectBoardCommentList(no);
		
				System.out.println("[BoardViewServlet@commentList] = " + commentList);
				
				
				
			// 3.view단 처리
				request.setAttribute("commentList", commentList);
				request.setAttribute("board", board);
				request
					.getRequestDispatcher("/WEB-INF/views/board/boardView.jsp")
					.forward(request, response);
			
			
		}
		catch(Exception e) {}
		
		

		
	}

}
