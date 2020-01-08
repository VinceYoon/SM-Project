package com.smProject.dao;

import java.util.List;
import java.util.Map;

import com.smProject.dao.util.CamelMap;


public interface UserDao {

	/**
	 * 사용자 목록 수
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int selectUserCnt(Map<String, Object> param) throws Exception;

	/**
	 * 사용자 목록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectUserList(Map<String, Object> param) throws Exception;

	/**
	 * 사용자 상세 정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CamelMap selectUserView(Map<String, Object> param) throws Exception;

	/**
	 * 직급 목록 조회
	 * @param param
	 * @return
	 */
	public List<CamelMap> selectPositionList(Map<String, Object> param) throws Exception;

	/**
	 * 사용자 구분 목록 조회
	 * @param param
	 * @return
	 */
	public List<CamelMap> selectAuthList(Map<String, Object> param) throws Exception;

	/**
	 * 아이디 중복 확인
	 * @param param
	 * @return
	 */
	public CamelMap selectUserIdChk(Map<String, Object> param) throws Exception;

	/**
	 * 계정 등록 처리
	 * @param param
	 */
	public void insertUser(Map<String, Object> param) throws Exception;

	/**
	 * 계정 수정 처리
	 * @param param
	 */
	public void updateUser(Map<String, Object> param) throws Exception;

	/**
	 * 계정 삭제 처리
	 * @param params
	 */
	public void deleteUser(Map<String, Object> params) throws Exception;

	/**
	 * 사용자 전체 목록 조회(앱)
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectUserAllList() throws Exception;

	/**
	 * 사용자 전체 목록 조회(웹)
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectUserListAll(Map<String, Object> param) throws Exception;

}
