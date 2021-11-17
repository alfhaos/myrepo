package com.kh.mvc.board.model.service;

import static com.kh.mvc.common.JDBCTemplate.close;
import static com.kh.mvc.common.JDBCTemplate.commit;
import static com.kh.mvc.common.JDBCTemplate.getConnection;
import static com.kh.mvc.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.mvc.board.model.dao.BoardDao;
import com.kh.mvc.board.model.vo.Attachment;
import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.board.model.vo.BoardComment;


public class BoardService {
	private static BoardDao boardDao = new BoardDao();
	
	public List<Board> SelectAllAttachment(Map<String, Object> param) {
		List<Board> list = new ArrayList<>();
		Connection conn = getConnection();
		list = boardDao.SelectAllAttachment(conn,param);
		
				
				
		close(conn);
		
		return list;
	}

	public int selectTotalboardCount() {
		Connection conn = getConnection();
		int result;
		result = boardDao.SelectTotalBoardCount(conn);
		

		return result;
	}
	
	/**
	 * Transaction
	 * 
	 * - insertBoard
	 * - insertAttachment * n
	 * 
	 * @param board
	 * @return
	 */

	public static int insertBoard(Board board) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = getConnection();
			result = boardDao.insertBoard(conn,board);
			
			
			// 방금 insert된 boardNo 조회 : select seq_board_no.currval from dual
			int boardNo = boardDao.selectLastBoardNo(conn);
			System.out.println("[BoardService] boardNo = " + boardNo);
			board.setNo(boardNo); // servlet에서 참조할 수 있도록 한다.
			
			
			List<Attachment> attachments = board.getAttachments();
			
			if(attachments != null) {
				// insert into attachment values(seq_attachment_no.nextval, 0 , ?, ?, default)
				for(Attachment attach : attachments) {
					attach.setBoardNo(boardNo); 	// FK컬럼값 설정(중요)
					result = boardDao.insertAttachments(conn,attach);
					
				}
			}
			
			
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e;
		}
		finally {
			close(conn);
		}
		return result;
	}

	public static Board SelectBoardView(int no) {
		Connection conn = null;
		Board board;
		
		try {
			conn = getConnection();
			
			board = boardDao.SelectBoardView(conn,no);
			
			List<Attachment> attachments = boardDao.selectAttachmentByBoardNo(conn, no);
			board.setAttachments(attachments);
			
			commit(conn);
		}catch(Exception e) {
			rollback(conn);
			throw e;
		}
		finally {
			close(conn);
		}
		
		return board;
	}

	public static Board SelectOneBoardAttachments(int no) {
		Connection conn = null;
		Board board;
		
		try {
			conn = getConnection();
			
			board = boardDao.SelectOneBoardAttachments(conn,no);
			
			
			commit(conn);
		}catch(Exception e) {
			rollback(conn);
			throw e;
		}
		finally {
			close(conn);
		}
		
		return board;
	}

	public static int UpdateReadCount(int no) {
		Connection conn = null;
		int result = 0;
		
		conn = getConnection();
		result = boardDao.UpdateReadCount(conn,no);
		close(conn);
		
		
		return result;
	}

	public static Attachment selectOneAttachment(int no) {
		Connection conn = getConnection();
		

		Attachment attach = boardDao.selectOneAttachment(conn,no);
		close(conn);
		
		
		
		
		return attach;
	}

	public List<Attachment> selectAttachmentByBoardNo(int no) {
		Connection conn = getConnection();
		List<Attachment> attachments = boardDao.selectAttachmentByBoardNo(conn, no);
		close(conn);
		return attachments;
		
	}
	
	public int deleteBoard(int no) {
		Connection conn = null;
		int result = 0;
		try {
			conn = getConnection();
			result = boardDao.deleteBoard(conn, no);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public static int updateBoard(Board board,int no) {
		Connection conn = getConnection();
		int result = 0;
		
		try {
		result = boardDao.updateBoard(conn,board);
		
		List<Attachment> attachments = board.getAttachments();
		
		if(attachments != null) {
			// insert into attachment values(seq_attachment_no.nextval, 0 , ?, ?, default)
			for(Attachment attach : attachments) {
				attach.setBoardNo(no); 	// FK컬럼값 설정(중요)
				result = boardDao.insertAttachments(conn,attach);
				
			}
		}
		
		
		} catch(Exception e) {
			
		}
		finally {
			close(conn);
		}
		
		return result;
	}

	public static int deleteAttachment(int delFileNo) {
		Connection conn = null;
		int result = 0;
		try {
			conn = getConnection();
			result = boardDao.deleteAttachment(conn, delFileNo);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public static List<BoardComment> selectBoardCommentList(int boardNo) {
		Connection conn = getConnection();
		List<BoardComment> commentList = boardDao.selectBoardCommentList(conn,boardNo);
		
		close(conn);
		
		
		return commentList;
	}

	public static int insertBoardComment(BoardComment bc) {
		Connection conn = getConnection();
		int result = 0;
		
		result = boardDao.insertBoardComment(conn,bc);
		
		close(conn);
		
		
		
		return result;
	}

	public static int commentDel(int commentNo) {
		Connection conn = getConnection();
		int result = 0;
		
		result = boardDao.commentDel(conn,commentNo);
		
		close(conn);
		return result;
	}



}
