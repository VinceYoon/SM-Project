package com.smProject.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.smProject.dao.UserDao;
import com.smProject.dao.util.CamelMap;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int selectUserCnt(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectOne("user.selectUserCnt", param);
	}
	
	@Override
	public List<CamelMap> selectUserList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("user.selectUserList", param);
	}

	@Override
	public CamelMap selectUserView(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectOne("user.selectUserView", param);
	}

	@Override
	public List<CamelMap> selectPositionList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("user.selectPositionList", param);
	}

	@Override
	public List<CamelMap> selectAuthList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("user.selectAuthList", param);
	}

	@Override
	public CamelMap selectUserIdChk(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectOne("user.selectUserIdChk", param);
	}

	@Override
	public void insertUser(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.insert("user.insertUser", param);
	}

	@Override
	public void updateUser(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.update("user.updateUser", param);
	}

	@Override
	public void deleteUser(Map<String, Object> params) throws Exception {
		sqlSessionTemplate.update("user.deleteUser", params);
	}

	@Override
	public List<CamelMap> selectUserAllList() throws Exception {
		return sqlSessionTemplate.selectList("user.selectUserAllList");
	}

	@Override
	public List<CamelMap> selectUserListAll(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("user.selectUserListAll", param);
	}

}
