package com.smProject.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.smProject.dao.CompanyDao;
import com.smProject.dao.util.CamelMap;

@Repository
public class CompanyDaoImpl implements CompanyDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int selectCompanyCnt(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectOne("company.selectCompanyCnt", param);
	}
	
	@Override
	public List<CamelMap> selectCompanyList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("company.selectCompanyList", param);
	}
	
	@Override
	public CamelMap selectCompanyView(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectOne("company.selectCompanyView", param);
	}
	
	@Override
	public List<CamelMap> selectCityList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("company.selectCityList", param);
	}

	@Override
	public List<CamelMap> selectTownList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("company.selectTownList", param);
	}

	@Override
	public List<CamelMap> selectCompanyTypeList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("company.selectCompanyTypeList", param);
	}

	@Override
	public void insertCompany(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.insert("company.insertCompany", param);
	}

	@Override
	public void updateCompany(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.update("company.updateCompany", param);
	}

	@Override
	public void deleteCompany(Map<String, Object> params) throws Exception {
		sqlSessionTemplate.update("company.deleteCompany", params);
	}

	@Override
	public Object selectSchCompanyList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("company.selectSchCompanyList", param);
	}

	@Override
	public List<CamelMap> selectCompanyListAll(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("company.selectCompanyListAll", param);
	}

	@Override
	public List<CamelMap> selectGuSiAllList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("company.selectGuSiAllList", param);
	}
}
