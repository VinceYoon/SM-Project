package com.smProject.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.smProject.dao.FireCtrDao;
import com.smProject.dao.util.CamelMap;

@Repository
public class FireCtrDaoImpl implements FireCtrDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<CamelMap> selectFireCtrList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("fireCtr.selectFireCtrList", param);
	}

	@Override
	public void deleteFireCtr(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.delete("fireCtr.deleteFireCtr", param);
	}

	@Override
	public void insertFireCtr(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.insert("fireCtr.insertFireCtr", param);
	}

	@Override
	public String selectFireCtrDupChk(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectOne("fireCtr.selectFireCtrDupChk", param);
	}

	@Override
	public List<CamelMap> getFireCtrListAll(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("fireCtr.getFireCtrListAll", param);
	}

	@Override
	public List<CamelMap> selectFireCtrMobileListAll(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("fireCtr.selectFireCtrMobileListAll", param);
	}
	
}
