package com.kh.mvc.member.model.service;

import static com.kh.mvc.common.JDBCTemplate.close;
import static com.kh.mvc.common.JDBCTemplate.commit;
import static com.kh.mvc.common.JDBCTemplate.getConnection;
import static com.kh.mvc.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kh.mvc.member.model.dao.MemberDao;
import com.kh.mvc.member.model.vo.Member;
import com.kh.mvc.member.model.vo.MemberEnroll;

public class MemberService {
	
	public static final String USER_ROLE = "U";
	public static final String ADMIN_ROLE = "A";
	
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

	public static List<Member> selectAllmember(Map<String, Object> param) {

		
		Connection conn = getConnection();
		List<Member> li = memberDao.selectAllmember(conn,param);
		close(conn);
		return li;
		
	}

	public int updateMemberRole(Member member) {
		
		Connection conn = null;
		int result = 0;
		
		conn = getConnection();
		result = memberDao.updateMemberRole(conn,member);
		
		close(conn);
		
		
		return result;
	}

	public static String selectMemberPassword(String memberId) {
		Connection conn = null;
		String pwd;
		
		conn = getConnection();
		pwd = memberDao.selectMemberPassword(conn,memberId);
		
		close(conn);
		return pwd;
	}

	public static int updateMemberNewPassword(String memberId, String inputNewPwd) {
		Connection conn = null;
		int result;
		
		conn = getConnection();
		
		result = memberDao.upDateMemberNewPassword(conn,memberId,inputNewPwd);
		
		close(conn);
		
		return result;
	}

	public List<Member> searchMember(Map<String, Object> param) {
		Connection conn = getConnection();
		List<Member> list = memberDao.searchMember(conn,param);
		
		
		close(conn);
		return list;
	}

	public int selectTotalMemberCount() {
		
		Connection conn = getConnection();
		int totalCount = memberDao.selectTotalMemberCount(conn);
		
		
		close(conn);
		return totalCount;
	}
}
