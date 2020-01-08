package com.smProject.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.smProject.dao.UserDao;
import com.smProject.dao.util.CamelMap;
import com.smProject.service.UserService;
import com.smProject.util.CommonUtil;
import com.smProject.util.PageNavigator;
import com.smProject.util.SecretUtil;
import com.smProject.util.StringUtil;
import com.smProject.util.exception.BizException;
import com.smProject.vo.SearchVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private Properties config;
	
	@Autowired
	private MessageSourceAccessor mr;
	
	@Autowired
	UserDao userDao;

	@Override
	public Map<String, Object> getUserList(Map<String, Object> param) throws Exception {

		// 건수 조회
		int totalCount = userDao.selectUserCnt(param);
		
		// 페이징 처리를 위한 세팅
		SearchVO search = new SearchVO();
		CommonUtil.pageSetting(param, search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userList", userDao.selectUserList(param));
		map.put("totalCount", totalCount);
		map.put("navi", new PageNavigator(search, totalCount));
		
		return map;
	}

	@Override
	public CamelMap loginProc(Map<String, Object> param) throws Exception {

		CamelMap map = userDao.selectUserView(param);
		
		// 아이디 확인
		if (map == null || StringUtil.isEmpty(map.get("userId")) || "Y".equals(map.get("delYn"))) {
			throw new BizException(mr.getMessage("msg.user.empty"));
		}
		
		// 비밀번호 확인
		String pwd = SecretUtil.encrypt((String) param.get("pwd"), SecretUtil.ENC_TYPE_SHA_256);
		if(!pwd.equals(map.get("pwd"))) {
			throw new BizException(mr.getMessage("msg.user.pw.error"));
		}
		
		return map;
	}

	@Override
	public Map<String, Object> getPositionList(Map<String, Object> param) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("positionList", userDao.selectPositionList(param));
		
		return map;
	}

	@Override
	public Map<String, Object> getAuthList(Map<String, Object> param) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("authList", userDao.selectAuthList(param));
		
		return map;
	}

	@Override
	public CamelMap getUserIdChk(Map<String, Object> param) throws Exception {

		CamelMap map = userDao.selectUserIdChk(param);
		
		return map;
	}

	@Override
	public void insertUser(Map<String, Object> param) throws Exception {

		// 비밀번호 - SHA256
		String pwd = SecretUtil.encrypt((String) param.get("pwd"), SecretUtil.ENC_TYPE_SHA_256);
		param.put("pwd", pwd);
		
		userDao.insertUser(param);
	}

	@Override
	public CamelMap getUser(Map<String, Object> param) throws Exception {
		
		CamelMap map = userDao.selectUserView(param);
		
		return map;
	}

	@Override
	public void updateUser(Map<String, Object> param) throws Exception {
		
		// 비밀번호 - SHA256
		if(!StringUtil.isEmpty(param.get("pwd"))) {
			String pwd = SecretUtil.encrypt((String) param.get("pwd"), SecretUtil.ENC_TYPE_SHA_256);
			param.put("pwd", pwd);
		}
		
		userDao.updateUser(param);
	}

	@Override
	public void deleteUser(Map<String, Object> params) throws Exception {

		userDao.deleteUser(params);
	}

	@Override
	public List<CamelMap> getUserAllList() throws Exception {
		return userDao.selectUserAllList();
	}

	@Override
	public List<CamelMap> getUserListAll(Map<String, Object> param) throws Exception {
		return userDao.selectUserListAll(param);
	}

}
