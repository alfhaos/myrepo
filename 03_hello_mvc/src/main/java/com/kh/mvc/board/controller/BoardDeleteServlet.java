package com.kh.mvc.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mvc.board.model.service.BoardService;
import com.kh.mvc.board.model.vo.Attachment;

/**
 * Servlet implementation class BoardDeleteServlet
 */
@WebServlet("/board/boardDelete")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			// 1.사용자입력처리
			int no = Integer.parseInt(request.getParameter("no"));

			// 2.업무로직
			// 	a. 업로드파일 삭제: java.io.File api 파일제거
			List<Attachment> attachments = boardService.selectAttachmentByBoardNo(no);
			if(attachments != null && !attachments.isEmpty()) {
				String saveDirectory = getServletContext().getRealPath("/upload/board");
				for(Attachment attach : attachments) {
					String renamedFilename = attach.getRenamedFilename();
					File delFile = new File(saveDirectory, renamedFilename);
					boolean deleted = delFile.delete();
					System.out.println("[BoardDeleteServlet] 파일삭제 (" + renamedFilename + ") : " + deleted);
				}
			}
			
			// 	b. board 레코드(행) 삭제 (attachment는 on delete cascade에 의해 자동으로 제거된다.)
			int result = boardService.deleteBoard(no);
			String msg = result > 0 ? "게시물 삭제 성공!" : "게시물 삭제 실패!";

			// 3. redirect : /mvc/board/boardList로 이동
			request.getSession().setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/board/boardList");

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	
	
	}

}

