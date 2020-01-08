package com.smProject.service;

import java.util.List;
import java.util.Map;

import com.smProject.dao.util.CamelMap;

public interface CompanyService {

	/**
	 * 지역 구분 (서울경기, 서울시, 경기도)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getCityList(Map<String, Object> param) throws Exception;
	
	/**
	 * 지역 상세(구, 군)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> getTownList(Map<String, Object> param) throws Exception;
	
	/**
	 * 업체 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getCompanyList(Map<String, Object> param) throws Exception;

	/**
	 * 업체 구분 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getCompanyTypeList(Map<String, Object> param) throws Exception;

	/**
	 * 업체 관리 등록 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void insertCompany(Map<String, Object> param) throws Exception;

	/**
	 * 업체 정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CamelMap getCompany(Map<String, Object> param) throws Exception;

	/**
	 * 업체 관리 수정 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void updateCompany(Map<String, Object> param) throws Exception;

	/**
	 * 업체 관리 삭제 처리
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void deleteCompany(Map<String, Object> params) throws Exception;

	/**
	 * 업체별 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSchCompanyList(Map<String, Object> param) throws Exception;

	/**
	 * 업체 전체 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> getCompanyListAll(Map<String, Object> param) throws Exception;

}
