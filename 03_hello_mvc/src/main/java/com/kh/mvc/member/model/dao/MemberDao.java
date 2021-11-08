package com.kh.mvc.member.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import static com.kh.mvc.common.JDBCTemplate.*;

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
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getMemberName());
			pstmt.setString(3, member.getGender());
			pstmt.setDate(4, member.getBirthday());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getPhone());
			pstmt.setString(7, member.getAddress());
			pstmt.setString(8, member.getHobby());
			pstmt.setString(9, member.getMemberId());
			
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

}
