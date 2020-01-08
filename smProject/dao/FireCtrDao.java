package com.smProject.dao;

import java.util.List;
import java.util.Map;

import com.smProject.dao.util.CamelMap;

public interface FireCtrDao {

	/**
	 * 방화 관리 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectFireCtrList(Map<String, Object> param) throws Exception;

	/**
	 * 방화 관리 완료 취소 처리
	 * @param params
	 * @throws Exception
	 */
	public void deleteFireCtr(Map<String, Object> param) throws Exception;

	/**
	 * 방화 관리 완료 처리
	 * @param param
	 * @throws Exception
	 */
	public void insertFireCtr(Map<String, Object> param) throws Exception;

	/**
	 * 방화 관리 해당 월 중복 확인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String selectFireCtrDupChk(Map<String, Object> param) throws Exception;

	/**
	 * 방화 관리 전체 목록 조회(웹)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> getFireCtrListAll(Map<String, Object> param) throws Exception;

	
	/**
	 * 방화 관리 전체 목록 조회(모바일)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectFireCtrMobileListAll(Map<String, Object> param) throws Exception;

}
