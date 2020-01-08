package com.smProject.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.smProject.dao.util.CamelMap;

public interface UserService {

	/**
	 * 사용자 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getUserList(Map<String, Object> param) throws Exception;

	/**
	 * 로그인 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CamelMap loginProc(Map<String, Object> param) throws Exception;

	/**
	 * 직급 목록 조회
	 * @param param
	 * @return
	 */
	public Map<String, Object> getPositionList(Map<String, Object> param) throws Exception;

	/**
	 * 사용자 구분 목록 조회
	 * @param param
	 * @return
	 */
	public Map<String, Object> getAuthList(Map<String, Object> param) throws Exception;

	/**
	 * 아이디 중복 확인
	 * @param param
	 * @return
	 */
	public CamelMap getUserIdChk(Map<String, Object> param) throws Exception;

	/**
	 * 계정 등록 처리
	 * @param param
	 * @throws Exception
	 */
	public void insertUser(Map<String, Object> param) throws Exception;

	/**
	 * 사용자 정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CamelMap getUser(Map<String, Object> param) throws Exception;

	/**
	 * 계정 수정 처리
	 * @param param
	 */
	public void updateUser(Map<String, Object> param) throws Exception;

	/**
	 * 계정 삭제 처리
	 * @param params
	 * @throws Exception
	 */
	public void deleteUser(Map<String, Object> params) throws Exception;

	/**
	 * 사용자 전체 목록 조회(앱)
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> getUserAllList() throws Exception;

	/**
	 * 사용자 전체 목록 조회(웹)
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> getUserListAll(Map<String, Object> param) throws Exception;

}
