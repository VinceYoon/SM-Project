package com.smProject.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.smProject.dao.util.CamelMap;

public interface SchMngService {

	/**
	 * 일정 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> getSchMngList(Map<String, Object> param) throws Exception;

	/**
	 * 일정 상세 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSchMng(Map<String, Object> param) throws Exception;

	/**
	 * 일정 구분 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public  Map<String, Object> getSchGubunList(Map<String, Object> param) throws Exception;

	/**
	 * 일정 등록 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void insertSchMng(Map<String, Object> param) throws Exception;
	
	/**
	 * 모바일 일정 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMobileSchMngList(Map<String, Object> param) throws Exception;

	/**
	 * 일정 확인 처리
	 * @param param
	 * @throws Exception
	 */
	public void schMngConfProc(Map<String, Object> param) throws Exception;

	/**
	 * 일정 수정 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void updateSchMng(Map<String, Object> param) throws Exception;

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
	public Map<String, Object> getSchMngListAll(Map<String, Object> param) throws Exception;

	/**
	 * 일정 관리 목록 엑셀 다운로드 (휴가자)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSchMngVacationListAll(Map<String, Object> param) throws Exception;

	/**
	 * 일정 완료/완료 취소 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void changeConfSchMng(Map<String, Object> param) throws Exception;

}
