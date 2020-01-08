package com.smProject.dao;

import java.util.List;
import java.util.Map;

import com.smProject.dao.util.CamelMap;

public interface ReportDao {

	/**
	 * 보고서 목록 수
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int selectReportCnt(Map<String, Object> param) throws Exception;

	/**
	 * 보고서 목록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectReportList(Map<String, Object> param) throws Exception;

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
	public List<CamelMap> selectReportListAll(Map<String, Object> param) throws Exception;

	/**
	 * 보고서 등록 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void insertReport(Map<String, Object> param) throws Exception;

	/**
	 * 보고서 상세 상태 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CamelMap selectReportStat(Map<String, Object> param) throws Exception;

	/**
	 * 보고서 확인 처리
	 * @param param
	 * @throws Exception
	 */
	public void updateReportConf(Map<String, Object> param) throws Exception;

	/**
	 * 보고서 목록 조회 (모바일)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectMobileReportList(Map<String, Object> param) throws Exception;

	/**
	 * 보고서 수정 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void updateReport(Map<String, Object> param) throws Exception;

}
