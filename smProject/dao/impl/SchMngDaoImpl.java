package com.smProject.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.smProject.dao.SchMngDao;
import com.smProject.dao.util.CamelMap;

@Repository
public class SchMngDaoImpl implements SchMngDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<CamelMap> selectSchMngList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("schMng.selectSchMngList", param);
	}

	@Override
	public CamelMap selectSchMngView(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectOne("schMng.selectSchMngView", param);
	}
	
	@Override
	public List<CamelMap> selectSchGubunList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("schMng.selectSchGubunList", param);
	}

	@Override
	public List<CamelMap> selectColorList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("schMng.selectColorList", param);
	}

	@Override
	public void insertSchMng(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.insert("schMng.insertSchMng", param);
	}

	@Override
	public void insertSchMngUser(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.insert("schMng.insertSchMngUser", param);
	}
	
	@Override
	public List<CamelMap> selectMobileSchMngList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("schMng.selectMobileSchMngList", param);
	}

	@Override
	public String selectSchMngConfirm(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectOne("schMng.selectSchMngConfirm", param);
	}

	@Override
	public void updateConfirm(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.update("schMng.updateConfirm", param);
	}
	
	@Override
	public List<CamelMap> selectSchMngUserList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("schMng.selectSchMngUserList", param);
	}

	@Override
	public void updateSchMng(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.update("schMng.updateSchMng", param);
	}

	@Override
	public void deleteSchMngUser(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.delete("schMng.deleteSchMngUser", param);
	}

	@Override
	public void deleteSchMng(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.update("schMng.deleteSchMng", param);
	}

	@Override
	public List<CamelMap> selectSchMngListAll(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("schMng.selectSchMngListAll", param);
	}

	@Override
	public List<CamelMap> selectSchMngVacationListAll(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("schMng.selectSchMngVacationListAll", param);
	}

	@Override
	public void changeConfSchMng(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.update("schMng.changeConfSchMng", param);
	}
}
