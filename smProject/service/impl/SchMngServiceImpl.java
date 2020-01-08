package com.smProject.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.smProject.dao.ReportDao;
import com.smProject.dao.SchMngDao;
import com.smProject.dao.util.CamelMap;
import com.smProject.service.SchMngService;
import com.smProject.util.DateUtil;
import com.smProject.util.StringUtil;
import com.smProject.util.exception.BizException;

@Service
public class SchMngServiceImpl implements SchMngService {

	@Autowired
	SchMngDao schMngDao;
	
	@Autowired
	ReportDao reportDao;
	
	@Autowired
	private MessageSourceAccessor mr;

	@Override
	public List<CamelMap> getSchMngList(Map<String, Object> param) throws Exception {
		
		List<CamelMap> map = schMngDao.selectSchMngList(param);
		
		return map;
	}

	@Override
	public Map<String, Object> getSchMng(Map<String, Object> param) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 일정 조회
		map.put("schMng", schMngDao.selectSchMngView(param));
		// 동반자 조회
		map.put("schMngUser", schMngDao.selectSchMngUserList(param));
		// 일정 구분 조회
		map.put("schGubunList", schMngDao.selectSchGubunList(param));
		// 색상 조회
		map.put("colorList", schMngDao.selectColorList(param));
		
		return map;
	}
	
	@Override
	public  Map<String, Object> getSchGubunList(Map<String, Object> param) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 일정 구분 조회
		map.put("schGubunList", schMngDao.selectSchGubunList(param));
		// 색상 조회
		map.put("colorList", schMngDao.selectColorList(param));
		
		// 일자 검색 조건 세팅
		String startDt = (String) param.get("startDt");
		String endDt = (String) param.get("endDt");
		
		if(StringUtil.isEmpty(startDt) && StringUtil.isEmpty(endDt)){
			Date date = DateUtil.getCurrentDate();
			param.put("startDt", DateUtil.getDateFormat(DateUtil.getDate(), "Y-M-D"));
			param.put("endDt", DateUtil.getDateFormat(DateUtil.getDate(), "Y-M-D"));
		}
		
		map.put("paramVO", param);
		
		return map;
	}

	@Override
	@Transactional
	public void insertSchMng(Map<String, Object> param) throws Exception {
		
		//일정 등록
		if("".equals(param.get("companySeq").toString())){
			param.put("companySeq", "999999");
		}
		schMngDao.insertSchMng(param);
		// 보고서 등록
		reportDao.insertReport(param);
		// 담당자 등록
		if(param.get("partnerIds") != null){
			String[] partners = (String[]) param.get("partnerIds");
			for (String partner : partners) {
				param.put("userId", partner);
				schMngDao.insertSchMngUser(param);
			}
		}
	}
	
	@Override
	public Map<String, Object> getMobileSchMngList(Map<String, Object> param) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		// 일정 조회
		map.put("schList", schMngDao.selectMobileSchMngList(param));

		return map;
	}

	@Override
	public void schMngConfProc(Map<String, Object> param) throws Exception {

		// 1. 처리 할수 있는 사용자, 확인된 처리가 아닌지 확인
		String confirmYn = schMngDao.selectSchMngConfirm(param);
		if (StringUtil.isEmpty(confirmYn)) {
			throw new BizException(mr.getMessage("msg.schMng.empty"));
		}

		if ("Y".equals(confirmYn)) {
			throw new BizException(mr.getMessage("msg.schMng.dupChk"));
		}
		
		schMngDao.updateConfirm(param);

	}

	@Override
	@Transactional
	public void updateSchMng(Map<String, Object> param) throws Exception {
		
		//일정 수정
		schMngDao.updateSchMng(param);
		// 보고서 수정
		reportDao.updateReport(param);
		// 담당자 등록
		String[] partners = (String[]) param.get("partnerIds");
		// 동행자 삭제
		schMngDao.deleteSchMngUser(param);
		for (String partner : partners) {
			param.put("userId", partner);
			// 동행자 등록
			schMngDao.insertSchMngUser(param);
		}
	}

	@Override
	public void deleteSchMng(Map<String, Object> param) throws Exception {
		//일정 삭제
		schMngDao.deleteSchMng(param);
	}

	@Override
	public Map<String, Object> getSchMngListAll(Map<String, Object> param) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("schList", schMngDao.selectSchMngListAll(param));
		map.put("schMngUser", schMngDao.selectSchMngUserList(param));
		
		return map;
	}

	@Override
	public Map<String, Object> getSchMngVacationListAll(Map<String, Object> param) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("schList", schMngDao.selectSchMngVacationListAll(param));
		
		return map;
	}

	@Override
	public void changeConfSchMng(Map<String, Object> param) throws Exception {
		//일정 완료/완료 취소
		schMngDao.changeConfSchMng(param);
	}

}
