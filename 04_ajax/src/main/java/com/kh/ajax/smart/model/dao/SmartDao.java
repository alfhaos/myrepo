package com.kh.ajax.smart.model.dao;
import static com.kh.ajax.common.JDBCTemplate.close;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.ajax.smart.model.vo.Smart;




public class SmartDao {
	private Properties prop = new Properties();
	
	public SmartDao(){
		// /build/classes 하위에서 파일을 조회
		File file = new File(SmartDao.class.getResource("/smart-query.properties").getPath());
		try {
			prop.load(new FileReader(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int smartEnroll(Connection conn, Smart smart) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("smartEnroll");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,smart.getName());
			pstmt.setInt(2, smart.getAmount());
			
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
	public List<Smart> smartList(Connection conn) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("smartList");
		List<Smart> list = new ArrayList<>();
		Smart smart;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				smart = new Smart();
				smart.setName(rs.getString("pname"));
				smart.setAmount(rs.getInt("amount"));
				smart.setDate(rs.getDate("pdate"));
				list.add(smart);		
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			close(rs);
			close(pstmt);
		}
		
		
		
		return list;
	}
	public List<Smart> selectSmartRank(Connection conn) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectSmartRank");
		List<Smart> list = new ArrayList<>();
		Smart smart;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				smart = new Smart();
				smart.setName(rs.getString("pname"));
				smart.setAmount(rs.getInt("amount"));
				smart.setRank(rs.getInt("rownum"));
				
				list.add(smart);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			close(rs);
			close(pstmt);
			
		}
		
		
		
		
		
		
		return list;
	}

}
