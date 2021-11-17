package com.kh.mvc.board.model.dao;

import static com.kh.mvc.common.JDBCTemplate.close;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kh.mvc.board.model.exception.BoardException;
import com.kh.mvc.board.model.vo.Attachment;
import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.board.model.vo.BoardComment;
import com.kh.mvc.member.model.dao.MemberDao;

public class BoardDao {
	private Properties prop = new Properties();
	
	public BoardDao(){
		// /build/classes 하위에서 파일을 조회
		File file = new File(MemberDao.class.getResource("/board-query.properties").getPath());
		try {
			prop.load(new FileReader(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Board> SelectAllAttachment(Connection conn, Map<String, Object> param) {

		List<Board> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		Board board;
		String sql = prop.getProperty("selectAllBoard");
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) param.get("startNum"));
			pstmt.setInt(2, (int) param.get("endNum"));
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				board = new Board();
				board.setNo(rset.getInt("no"));
				board.setTitle(rset.getString("title"));
				board.setWriter(rset.getString("writer"));
				board.setContent(rset.getString("content"));
				board.setReadCount(rset.getInt("read_count"));
				board.setRegDate(rset.getDate("reg_date"));
				board.setAttachCount(rset.getInt("attach_count"));
				
				
				board.setCommentCount(rset.getInt("comment_count"));
				list.add(board);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			close(rset);
			close(pstmt);
		}
		
		
		
		return list;
	}

	public int SelectTotalBoardCount(Connection conn) {

		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("SelectTotalBoardCount");
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			close(rset);
			close(pstmt);
		}
		
		
		return result;
	}

	public int insertBoard(Connection conn, Board board) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertBoard");
		int result = 0;
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException("게시물 등록 오류!",e);
			
		}
		finally {
			close(pstmt);
			
		}
		
		return result;
	}

	public int selectLastBoardNo(Connection conn) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectLastBoardNo");
		ResultSet rset = null;
		int boardNo = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				boardNo = rset.getInt(1);
			}
		} catch(SQLException e) {
			throw new BoardException("최근 게시글 번호 조회 오류!",e);
		}
		finally {
			close(rset);
			close(pstmt);
			
		}
		return boardNo;
	}

	public int insertAttachments(Connection conn, Attachment attach) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertAttachment");
		int result = 0;
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, attach.getBoardNo());
			pstmt.setString(2, attach.getOriginalFilename());
			pstmt.setString(3, attach.getRenamedFilename());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BoardException("첨부파일 등록 오류!",e);
			
		}
		finally {
			close(pstmt);
			
		}
		
		return result;
	}

	public Board SelectBoardView(Connection conn, int no) {
		PreparedStatement pstmt = null;
		Board board = null;
		ResultSet rset = null;
		String sql = prop.getProperty("SelectBoardView");
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				board = new Board();
				board.setNo(rset.getInt("no"));
				board.setTitle(rset.getString("title"));
				board.setWriter(rset.getString("writer"));
				board.setContent(rset.getString("content"));
				board.setReadCount(rset.getInt("read_count"));
			}
			
		} catch (SQLException e) {
			throw new BoardException("게시글 불러오기 오류!",e);
		}
		finally {
			
			close(rset);
			close(pstmt);
		}
		
		
		
		return board;
	}

	public List<Attachment> selectAttachmentByBoardNo(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectAttachmentByBoardNo");
		ResultSet rset = null;
		List<Attachment> attachments = new ArrayList<>();
		
		try{
			//미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(sql);
			//쿼리문미완성
			pstmt.setInt(1, boardNo);
			//쿼리문실행
			//완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			rset = pstmt.executeQuery();
			
			while(rset.next()){
				Attachment attach = new Attachment();
				attach.setNo(rset.getInt("no"));
				attach.setBoardNo(rset.getInt("board_no"));
				attach.setOriginalFilename(rset.getString("original_filename"));
				attach.setRenamedFilename(rset.getString("renamed_filename"));
				attach.setRegDate(rset.getDate("reg_date"));
				attachments.add(attach);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(rset);
			close(pstmt);
		}
		return attachments;

	}

	public Board SelectOneBoardAttachments(Connection conn, int no) {
		PreparedStatement pstmt = null;
		Board board = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOnBoardAttachments");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				board = new Board();
				board.setNo(rset.getInt("no"));
				board.setTitle(rset.getString("title"));
				board.setWriter(rset.getString("writer"));
				board.setContent(rset.getString("content"));
				board.setReadCount(rset.getInt("read_count"));
				board.setRegDate(rset.getDate("reg_date"));
				
				int attachNo = rset.getInt("attach_no");
				
				if(attachNo != 0) {
					// 첨부파일이 있는 경우 1행 또는 2행이다.
					List<Attachment> attachments = new ArrayList<>();
					do {
						Attachment attach = new Attachment();
						attach.setNo(rset.getInt("attach_no"));
						attach.setBoardNo(rset.getInt("board_no"));
						attach.setOriginalFilename(rset.getString("original_filename"));
						attach.setRenamedFilename(rset.getString("renamed_filename"));
						attach.setRegDate(rset.getDate("reg_date"));
						attachments.add(attach);
						
					} while(rset.next());
					board.setAttachments(attachments);
				}
			}
			
		} catch (SQLException e) {
			throw new BoardException("게시글 불러오기 오류!",e);
		}
		finally {
			
			close(rset);
			close(pstmt);
		}
		
		
		
		return board;
		
	}

	public int UpdateReadCount(Connection conn, int no) {
		PreparedStatement pstmt = null;
		Board board = null;
		int result = 0;
		String sql = prop.getProperty("updateReadCount");
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			close(pstmt);
		}
		
		
		
		return result;
	}

	public Attachment selectOneAttachment(Connection conn, int no) {
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectOneAttachment");
		ResultSet rset = null;
		List<Attachment> attachments = new ArrayList<>();
		Attachment attach = null;
		
		try{
			//미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(sql);
			//쿼리문미완성
			pstmt.setInt(1, no);
			//쿼리문실행
			//완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			rset = pstmt.executeQuery();
			
			while(rset.next()){
				attach = new Attachment();
				attach.setNo(rset.getInt("no"));
				attach.setBoardNo(rset.getInt("board_no"));
				attach.setOriginalFilename(rset.getString("original_filename"));
				attach.setRenamedFilename(rset.getString("renamed_filename"));
				attach.setRegDate(rset.getDate("reg_date"));
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(rset);
			close(pstmt);
		}
		
		return attach;
		

	}
	
	
	

    public int deleteBoard(Connection conn, int no) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("deleteBoard"); 
		
		try {
			//미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			//쿼리문미완성
			pstmt.setInt(1, no);
			
			//쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			//DML은 executeUpdate()
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		
		return result;
	}

	public int updateBoard(Connection conn, Board board) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateBoard");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getNo());
			
			result = pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			close(pstmt);
		}
		
		
		
		return result;
	}

	public int deleteAttachment(Connection conn, int delFileNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("deleteAttachment"); 
		
		try {
			//미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			//쿼리문미완성
			pstmt.setInt(1, delFileNo);
			
			//쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			//DML은 executeUpdate()
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		
		return result;
	}

	public List<BoardComment> selectBoardCommentList(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectBoardCommentList");
		ResultSet rset = null;
		List<BoardComment> commentList = new ArrayList<>();
		BoardComment bc;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			
			while(rset.next()) {
				 bc = new BoardComment();
				 
				 bc.setBoardNo(rset.getInt("board_no"));
				 bc.setCommentLevel(rset.getInt("comment_level"));
				 bc.setCommentRef(rset.getInt("comment_ref"));
				 bc.setContent(rset.getString("content"));
				 bc.setNo(rset.getInt("no"));
				 bc.setRegDate(rset.getDate("reg_date"));
				 bc.setWriter(rset.getString("writer"));
				 commentList.add(bc);
				 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			close(rset);
			close(pstmt);
			
		}
		
		return commentList;
	}

	public int insertBoardComment(Connection conn, BoardComment bc) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertBoardComment");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bc.getCommentLevel());
			pstmt.setString(2, bc.getWriter());
			pstmt.setString(3, bc.getContent());
			pstmt.setInt(4, bc.getBoardNo());
			// pstmt.setInt(5, bc.getCommentRef() == 0 ? null : bc.getCommentRef()); 	// 이러면 int 에 null이 들어가서 nullpointException
			pstmt.setObject(5, bc.getCommentRef() == 0 ? null : bc.getCommentRef());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			close(pstmt);
			
		}
		
		
		return result;
	}

	public int commentDel(Connection conn, int commentNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("commentDel");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentNo);
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			close(pstmt);
		}
		
		
		
		return result;
	}

}
