package com.smProject.service;

import java.util.List;
import java.util.Map;

import com.smProject.dao.util.CamelMap;

public interface FireCtrService {

	/**
	 * 방화 관리 목록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getFireCtrList(Map<String, Object> param) throws Exception;

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
	 * 방화 관리 전체 목록 조회(웹)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> getFireCtrListAll(Map<String, Object> param) throws Exception;

	/**
	 * 모바일 방화 관리 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMobileCtrList(Map<String, Object> param)  throws Exception;

}
