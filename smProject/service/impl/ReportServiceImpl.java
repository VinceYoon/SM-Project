package com.smProject.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.smProject.common.Constant;
import com.smProject.dao.ReportDao;
import com.smProject.dao.util.CamelMap;
import com.smProject.service.ReportService;
import com.smProject.util.CommonUtil;
import com.smProject.util.DateUtil;
import com.smProject.util.PageNavigator;
import com.smProject.util.StringUtil;
import com.smProject.util.exception.BizException;
import com.smProject.vo.SearchVO;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	ReportDao reportDao;
	
	@Autowired
	private MessageSourceAccessor mr;

	@Override
	public Map<String, Object> getReportList(Map<String, Object> param) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 일자 검색 조건 세팅
		String startDt = (String) param.get("startDt");
		String endDt = (String) param.get("endDt");
		
		if(StringUtil.isEmpty(startDt) && StringUtil.isEmpty(endDt)){
			Date date = DateUtil.getCurrentDate();
			param.put("startDt", DateUtil.getDateFormat(DateUtil.addMonth(date, -1), "Y-M-D"));
			param.put("endDt", DateUtil.getDateFormat(DateUtil.getDate(), "Y-M-D"));
		}
		
		// 건수 조회
		int totalCount = reportDao.selectReportCnt(param);
		
		// 페이징 처리를 위한 세팅
		SearchVO search = new SearchVO();
		
		CommonUtil.pageSetting(param, search);

		map.put("reportList", reportDao.selectReportList(param));
		map.put("totalCount", totalCount);
		map.put("navi", new PageNavigator(search, totalCount));
		
		map.put("paramVO", param);
		
		return map;
	}

	@Override
	public void cancelReport(Map<String, Object> param) throws Exception {
		
		reportDao.cancelReport(param);
	}

	@Override
	public void saveLimitDt(Map<String, Object> param) throws Exception {
		
		reportDao.saveLimitDt(param);
	}

	@Override
	public List<CamelMap> getReportListAll(Map<String, Object> param) throws Exception {
		return reportDao.selectReportListAll(param);
	}

	@Override
	public void reportConfProcAjax(Map<String, Object> param) throws Exception {
		
		
		CamelMap map = reportDao.selectReportStat(param);
		
		// 보고서가 조회 안되는 경우
		if (map == null || StringUtil.isEmpty(map.get("confirmYn"))) {
			throw new BizException(mr.getMessage("msg.report.no"));
		}

		// 보고서 권한 체크 - 스케줄 담당자인 경우만 보고서 완료 가능
		if (!param.get("auth").equals(Constant.ADMIN_AUTH)) {
			if (!map.get("mngId").equals(param.get("userId"))) {
				throw new BizException(mr.getMessage("msg.report.no.auth"));
			}

		}
		
		// 이미 완료 된 보고서 인지 확인
		if ("Y".equals(map.get("confirmYn"))) {
			throw new BizException(mr.getMessage("msg.report.dupChk"));
		}

		// 완료 마감일이 지났는지 확인
		if (!StringUtil.isEmpty(map.get("limitDt"))) {
			String limitDt = map.get("limitDt").toString();
			String toDay = DateUtil.getDate();

			if (DateUtil.intervalDate(limitDt, toDay) > 0) {
				throw new BizException(mr.getMessage("msg.report.limit.end") + " 보고서 완료마감일 : " + limitDt);
			}

		}
		
		// 완료 처리
		reportDao.updateReportConf(param);
	}
	
	@Override
	public Map<String, Object> getMobileReportList(Map<String, Object> param) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportList", reportDao.selectMobileReportList(param));

		return map;
	}
	
}
