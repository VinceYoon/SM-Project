package com.smProject.dao;

import java.util.List;
import java.util.Map;

import com.smProject.dao.util.CamelMap;

public interface SchMngDao {

	/**
	 * 일정 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectSchMngList(Map<String, Object> param) throws Exception;

	/**
	 * 일정 상세 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CamelMap selectSchMngView(Map<String, Object> param) throws Exception;

	/**
	 * 일정 구분 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectSchGubunList(Map<String, Object> param) throws Exception;

	/**
	 * 색상 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectColorList(Map<String, Object> param) throws Exception;

	/**
	 * 일정 등록 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void insertSchMng(Map<String, Object> param) throws Exception;

	/**
	 * 일정 동행자 등록 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void insertSchMngUser(Map<String, Object> param) throws Exception;
	
	/**
	 * 일정 목록 조회 - 모바일
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectMobileSchMngList(Map<String, Object> param) throws Exception;

	/**
	 * 일정 확인 여부 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String selectSchMngConfirm(Map<String, Object> param) throws Exception;

	/**
	 * 일정 확인 처리
	 * @param param
	 * @throws Exception
	 */
	public void updateConfirm(Map<String, Object> param) throws Exception;

	/**
	 * 일정 동행자 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectSchMngUserList(Map<String, Object> param) throws Exception;

	/**
	 * 일정 수정 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void updateSchMng(Map<String, Object> param) throws Exception;

	/**
	 * 일정 동행자 삭제 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void deleteSchMngUser(Map<String, Object> param) throws Exception;
	/**
	 * 일정 삭제 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void deleteSchMng(Map<String, Object> param) throws Exception;

	/**
	 * 일정 관리 목록 엑셀 다운로드
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectSchMngListAll(Map<String, Object> param) throws Exception;

	/**
	 * 일정 관리 목록 엑셀 다운로드 (휴가자)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectSchMngVacationListAll(Map<String, Object> param) throws Exception;

	/**
	 * 일정 완료/완료 취소 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void changeConfSchMng(Map<String, Object> param) throws Exception;

}
