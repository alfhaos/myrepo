package com.kh.mvc.member.model.dao;

import static com.kh.mvc.common.JDBCTemplate.close;

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

import com.kh.mvc.member.model.MemberException;
import com.kh.mvc.member.model.vo.Member;
import com.kh.mvc.member.model.vo.MemberEnroll;


public class MemberDao {
	
	private static Properties prop = new Properties();
	
	public MemberDao(){
		// /build/classes 하위에서 파일을 조회
		String filepath = MemberDao.class.getResource("/member-query.properties").getPath();
		System.out.println(filepath);
		try {
			prop.load(new FileReader(filepath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * DQL
	 * - PreparedStatment
	 * - sql : String
	 * - ResultSet 
	 * - Member VO
	 * 
	 * 
	 * 
	 * @param conn
	 * @param memberId
	 * @return
	 */

	public Member selectOneMember(Connection conn, String memberId) {
		String sql = prop.getProperty("SelectOneMember");
		PreparedStatement pstmt = null;
		Member member = null;
		ResultSet rset = null;
			
		
		
		
		
		

		
		try {
			// 1. pstmt 객채생성 & 미완성쿼리 전달 & 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,memberId);
			
			// 2. 쿼리실행 executeQuery :  ResultSet 리턴(DQL)
			rset = pstmt.executeQuery();
			

			// 3. rset -> vo
			while(rset.next()) {
				member = new Member();
				member.setMemberId(rset.getString("member_id"));
				member.setPassword(rset.getString("password"));
				member.setMemberName(rset.getString("member_name"));
				member.setMemberRole(rset.getString("member_role"));
				member.setGender(rset.getString("gender"));
				member.setBirthday(rset.getDate("birthday"));
				member.setEmail(rset.getString("email"));
				member.setPhone(rset.getString("phone"));
				member.setAddress(rset.getString("address"));
				member.setHobby(rset.getString("hobby"));
				member.setEnrollDate(rset.getDate("enroll_date"));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			// 4. 자원반납
			close(rset);
			close(pstmt);
			
		}
		
		
		
		
		return member;
		
	}


	public int insertMember(Connection conn, MemberEnroll member) {
		String sql = prop.getProperty("InsertMember");
		PreparedStatement pstmt = null;
		int result = 0;
		
		
		try {
			//1. PreparedStatment객체 생성(미완성 쿼리 & 값대입)
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,member.getMemberId());
			pstmt.setString(2,member.getPassword());
			pstmt.setString(3,member.getMemberName());
			pstmt.setString(4,member.getGender());
			pstmt.setDate(5, member.getBirthday());
			pstmt.setString(6,member.getEmail());
			pstmt.setString(7,member.getTel());
			pstmt.setString(8,member.getAddress());
			pstmt.setString(9,member.getHobby());
			
			//2. 쿼리실행(DML - int , DQL - ResultSet)  & VO객체 변환
			result = pstmt.executeUpdate();
			
			System.out.println("Dao의 리졸트 값 : " + result);
			
			
			
		} catch (SQLException e) {
			throw new MemberException("회원가입 오류 발생",e);
		}
		finally {
			
			
			close(pstmt);
			
			
		}
	
		
		
		return result;
	}




	public int updateMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("UpdateMember");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getMemberName());
			pstmt.setString(2, member.getGender());
			pstmt.setDate(3, member.getBirthday());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getPhone());
			pstmt.setString(6, member.getAddress());
			pstmt.setString(7, member.getHobby());
			pstmt.setString(8, member.getMemberId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	public int deleteMember(Connection conn, String memberId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("DeleteMember");
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,memberId);
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
	
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		
		
		return result;
	}


	public List<Member> selectAllmember(Connection conn, Map<String, Object> param) {
		List<Member> list = new ArrayList<>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("SelectAllMember");
		Member member;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) param.get("startNum"));
			pstmt.setInt(2, (int) param.get("endNum"));
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				member = new Member();
				member.setMemberId(rset.getString("member_id"));
				member.setPassword(rset.getString("password"));
				member.setMemberName(rset.getString("member_name"));
				member.setMemberRole(rset.getString("member_role"));
				member.setGender(rset.getString("gender"));
				member.setBirthday(rset.getDate("birthday"));
				member.setEmail(rset.getString("email"));
				member.setPhone(rset.getString("phone"));
				member.setAddress(rset.getString("address"));
				member.setHobby(rset.getString("hobby"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				list.add(member);
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


	public int updateMemberRole(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("UpdateMemberRole");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,member.getMemberRole());
			pstmt.setString(2,member.getMemberId());
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


	public String selectMemberPassword(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		String pwd = null;
		String sql = prop.getProperty("SelectMemberPwd");
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			rset = pstmt.executeQuery();
			
			
			while(rset.next()) {
				pwd = rset.getString("password");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			close(pstmt);
			
		}
		
		
		
		return pwd;
	}


	public int upDateMemberNewPassword(Connection conn, String memberId, String inputNewPwd) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("UpdateMemberPassword");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, inputNewPwd);
			pstmt.setString(2, memberId);
			
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


	public List<Member> searchMember(Connection conn, Map<String, Object> param) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("searchMember");
		ResultSet rset = null;
		List<Member> list = new ArrayList<>();
		String searchType = (String) param.get("searchType");
		String searchKeyword = (String) param.get("searchKeyword");
		Member member;
		switch(searchType) {
		case "memberId":
			sql += " member_id like '%" + searchKeyword + "%'";
			
			break;
		case "memberName":
			sql += " member_name like '%" + searchKeyword + "%'";
			break;
		case "gender":
			sql += " gender = '" + searchKeyword + "'";
			break;

		}
		System.out.println("sql@dao = " + sql);
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				member = new Member();
				member.setMemberId(rset.getString("member_id"));
				member.setPassword(rset.getString("password"));
				member.setMemberName(rset.getString("member_name"));
				member.setMemberRole(rset.getString("member_role"));
				member.setGender(rset.getString("gender"));
				member.setBirthday(rset.getDate("birthday"));
				member.setEmail(rset.getString("email"));
				member.setPhone(rset.getString("phone"));
				member.setAddress(rset.getString("address"));
				member.setHobby(rset.getString("hobby"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				list.add(member);
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


	public int selectTotalMemberCount(Connection conn) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectTotalMemberCount");
		ResultSet rset = null;
		int totalCount = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next())
				totalCount = rset.getInt(1); // 컬럼인덱스 1부터
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			close(rset);
			close(pstmt);
		}
		
		
		
		return totalCount;
	}

}
