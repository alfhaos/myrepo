package com.kh.ajax.smart.model.service;

import static com.kh.ajax.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.kh.ajax.smart.model.dao.SmartDao;
import com.kh.ajax.smart.model.vo.Smart;

public class SmartService {
	private SmartDao smartDao = new SmartDao();
	
	public int smartEnroll(Smart smart) {
		int result = 0;
		Connection conn = getConnection();
		
		result = smartDao.smartEnroll(conn,smart);
		
		close(conn);
		
		return result;
	}

	public List<Smart> smartList() {
		List<Smart> list = new ArrayList<>();
		Connection conn = getConnection();
		
		list = smartDao.smartList(conn);
		close(conn);
		
		return list;
	}

	public List<Smart> selectSmartRank() {
		List<Smart> list = new ArrayList<>();
		Connection conn = getConnection();
		
		list = smartDao.selectSmartRank(conn);
		close(conn);
		
		return list;
	}

}
