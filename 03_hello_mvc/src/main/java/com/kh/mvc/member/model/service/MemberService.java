package com.kh.mvc.member.model.service;

import com.kh.mvc.member.model.dao.MemberDao;
import com.kh.mvc.member.model.vo.Member;
import com.kh.mvc.member.model.vo.MemberEnroll;

import static com.kh.mvc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class MemberService {
	
//	public static final String USER_ROLE = "U";
//	public static final String ADMIN_ROLE = "A";
//	Role을 채우는 또다른 방법
	private static MemberDao memberDao = new MemberDao();

	public static Member selectOneMember(String memberId) {
		
		// 1. Connection 객체 생성
		Connection conn = getConnection();
		
		// 2. Dao에 쿼리실행 요청
		Member member = memberDao.selectOneMember(conn,memberId);
		
		// 3. Connection자원반납
		
		close(conn);
		
		return member;
	}

	public static int insertMember(MemberEnroll member) {
		// 1. Connection 객체 생성
		Connection conn = getConnection();
		int result = 0;
		
		try {
	
		// 2. Dao에 쿼리실행 요청
		result = memberDao.insertMember(conn,member);
		
		// 3. 트랜잭션 처리
		commit(conn);
		// 3. Connection자원반납
		}
		catch(Exception e) {
			rollback(conn);
		}
		finally{close(conn);}
		
		System.out.println("서비스의 리졸트값 : " + result);
		return result;
		
	}



	public static int updateMember(Member member) {
		Connection conn = null;
		int result = 0;
		try {
			conn = getConnection();
			result = memberDao.updateMember(conn, member);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		
		return result;
	}

	public static int deleteMember(String memberId) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = getConnection();
			result = memberDao.deleteMember(conn,memberId);
			
			commit(conn);
		}
		catch(Exception e) {
			rollback(conn);
			throw e;
		}
		finally {
			close(conn);
			
		}
		return result;
	}
}
