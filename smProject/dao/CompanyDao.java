package com.smProject.dao;

import java.util.List;
import java.util.Map;

import com.smProject.dao.util.CamelMap;

public interface CompanyDao {

	/**
	 * 업체 목록 수
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int selectCompanyCnt(Map<String, Object> param) throws Exception;
	
	/**
	 * 업체 목록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectCompanyList(Map<String, Object> param) throws Exception;

	/**
	 * 업체 상세 정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CamelMap selectCompanyView(Map<String, Object> param) throws Exception;
	
	/**
	 * 지역 구분 목록 (서울경기, 서울시, 경기도) 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectCityList(Map<String, Object> param) throws Exception;

	/**
	 * 지역 상세 목록 (구, 군) 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectTownList(Map<String, Object> param) throws Exception;

	/**
	 * 업체 구분 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectCompanyTypeList(Map<String, Object> param) throws Exception;

	/**
	 * 업체 관리 등록 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void insertCompany(Map<String, Object> param) throws Exception;

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
	 * 업체별 이력 관리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Object selectSchCompanyList(Map<String, Object> param) throws Exception;

	/**
	 * 업체 전체 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectCompanyListAll(Map<String, Object> param) throws Exception;

	
	/**
	 * 전체 구 / 시 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CamelMap> selectGuSiAllList(Map<String, Object> param) throws Exception;

}
