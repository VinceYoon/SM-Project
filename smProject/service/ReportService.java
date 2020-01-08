package com.smProject.service;

import java.util.List;
import java.util.Map;

import com.smProject.dao.util.CamelMap;

public interface ReportService {

	/**
	 * 보고서 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getReportList(Map<String, Object> param) throws Exception;

	/**
	 * 보고서 완료 취소 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void cancelReport(Map<String, Object> param) throws Exception;

	/**
	 * 보고서 마감일 지정 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void saveLimitDt(Map<String, Object> param) throws Exception;

	/**
	 * 리포트 관리 목록 엑셀 다운로드
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> getReportListAll(Map<String, Object> param) throws Exception;

	/**
	 * 보고서 완료 처리
	 * @param param
	 * @throws Exception
	 */
	public void reportConfProcAjax(Map<String, Object> param) throws Exception;

	/**
	 * 보고서 목록 조회 (모바일)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMobileReportList(Map<String, Object> param) throws Exception;
	
}
