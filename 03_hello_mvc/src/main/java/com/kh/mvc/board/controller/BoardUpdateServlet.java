package com.kh.mvc.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mvc.board.model.service.BoardService;
import com.kh.mvc.board.model.vo.Attachment;
import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.common.MvcFileRenamePolicy;
import com.kh.mvc.common.MvcUtils;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * Servlet implementation class BoardUpdateServlet
 */
@WebServlet("/board/boardUpdate")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no = Integer.parseInt(request.getParameter("no"));
		
		Board board = BoardService.SelectOneBoardAttachments(no);
		
		System.out.println("BoardUpdateServlet@board = " + board);
		request.setAttribute("board", board);
		request
				.getRequestDispatcher("/WEB-INF/views/board/boardUpdate.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// A. server computer에 사용자 업로드 파일 저장
		String saveDirectory = getServletContext().getRealPath("/upload/board");
		
		int maxPostSize = 1024 * 1024 * 10; // 10MB
		String encoding = "utf-8";
		
		// 파일명 재지정 정책 객체
		FileRenamePolicy policy = new MvcFileRenamePolicy();
		
		MultipartRequest multipartRequest = new MultipartRequest(request,saveDirectory,maxPostSize,encoding,policy);
		
		
		// 1.사용자 입력값 -> Board 객체
		// MultipartRequest객체 생성하는 경우, 기존 request가 아닌 MultipartRequest객체 에서 값을 가져와야한다.
		int no = Integer.parseInt(multipartRequest.getParameter("no"));
		String title = multipartRequest.getParameter("title");
		String writer = multipartRequest.getParameter("writer");
		String content = multipartRequest.getParameter("content");
		String[] delFiles = multipartRequest.getParameterValues("delFile");
		
		
		System.out.println("자바서블릿너멉!!!! = " + no);
		Board board = new Board(no,title,writer,content,0,null);
		
		
		// 저장된 파일정보 -> Attachment객체 생성 -> List<Attachment>객체에 추가 -> Board객체에 추가
//		File upFile1 = multipartRequest.getFile("upFile1");
//		File upFile2 = multipartRequest.getFile("upFile2");
//		
//		if(upFile1 != null || upFile2 != null) {
//			List<Attachment> attachments = new ArrayList<>();
//			
//			if(upFile1 != null) {
//				Attachment attach1 = MvcUtils.makeAttachment(multipartRequest, "upFile1");
//				attachments.add(attach1);
//			}
//			if(upFile2 != null) {
//				Attachment attach2 = MvcUtils.makeAttachment(multipartRequest, "upFile2");
//				attachments.add(attach2);
//			}
//			
//			board.setAttachments(attachments);
//			
//		}
		Enumeration fileNames = multipartRequest.getFileNames();
		List<Attachment> attachments = new ArrayList<>();
		
		while(fileNames.hasMoreElements()) {
			String fileName = (String) fileNames.nextElement();
			System.out.println("[BoardUpdateServlet] fileName = " + fileName);
			File upFile = multipartRequest.getFile(fileName);
			if(upFile != null) {
				Attachment attach = MvcUtils.makeAttachment(multipartRequest, fileName);
				attach.setBoardNo(no);
				attachments.add(attach);
			}
		}
		if(!attachments.isEmpty())
			board.setAttachments(attachments);
		
		// 2. 업무로직
		// a. 기존 첨부파일 삭제
		if(delFiles != null) {
			for(String delFile : delFiles) {
				int delFileNo = Integer.parseInt(delFile);
				
				Attachment attach = BoardService.selectOneAttachment(delFileNo);
				
				// 가. 첨부파일 삭제 : {saveDirectory} / {renamedFilename}
				String renamedFilename = attach.getRenamedFilename();
				File DelFile = new File(saveDirectory, renamedFilename);
				boolean removed = DelFile.delete();
				System.out.println("[BoardUpdateServlet] " + renamedFilename + "삭제 : " + removed);
				
				// 나. DB 첨부파일 레코드 삭제
				int result = BoardService.deleteAttachment(delFileNo);
				
				
			}
		}
		
		
		// b. db 레코드 수정(update board + insert attachment)
		
		
		int result = BoardService.updateBoard(board,no);
		
		String msg = result > 0 ? "게시물 수정 성공!" : "게시물 수정 실패!";
		
		//3.view단
		request.setAttribute("msg", msg);
		String location = request.getContextPath() + "/board/boardView?no="+board.getNo();
		response.sendRedirect(location);
		
		
	}

}
