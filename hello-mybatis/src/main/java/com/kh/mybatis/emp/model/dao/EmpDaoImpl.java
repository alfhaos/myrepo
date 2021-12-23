package com.kh.mybatis.emp.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class EmpDaoImpl implements EmpDao {

	@Override
	public List<Map<String, Object>> selectEmpMapList(SqlSession session) {
		return session.selectList("emp.selectEmpList");
	}

	@Override
	public List<Map<String, Object>> search1(SqlSession session, Map<String, Object> param) {
		return session.selectList("emp.search1", param);
	}

	@Override
	public List<Map<String, Object>> search2(SqlSession session, Map<String, Object> param) {
		// TODO Auto-generated method stub
		return session.selectList("emp.search2", param);
	}

	@Override
	public List<Map<String, String>> selectJobList(SqlSession session) {
		// TODO Auto-generated method stub
		return session.selectList("emp.selectJobList");
	}

	@Override
	public List<Map<String, Object>> search3(SqlSession session, Map<String, Object> param) {
		// TODO Auto-generated method stub
		return session.selectList("emp.search3", param);
	}

	@Override
	public Map<String, Object> selectOneEmpMap(SqlSession session, Map<String, Object> param) {
		// TODO Auto-generated method stub
		return session.selectOne("emp.selectOneEmpMap", param);
	}

	@Override
	public List<Map<String, String>> selectDeptList(SqlSession session) {
		// TODO Auto-generated method stub
		return session.selectList("emp.selectDeptList");
	}

	@Override
	public int updateEmp(SqlSession session, Map<String, Object> param) {
		// TODO Auto-generated method stub
		return session.update("emp.updateEmp",param);
	}
	
	
	
	
	

}
