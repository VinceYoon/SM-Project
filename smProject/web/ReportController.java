package com.smProject.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smProject.auth.AuthUtil;
import com.smProject.dao.util.CamelMap;
import com.smProject.service.ReportService;
import com.smProject.sitemesh.SiteMeshDecoratorSelector;
import com.smProject.sitemesh.SiteMeshFilter;
import com.smProject.util.StringUtil;
import com.smProject.util.exception.BizException;
import com.smProject.vo.ResultDataVO;
import com.smProject.vo.ResultVO;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	ReportService reportService;
	
	@Autowired
	private MessageSourceAccessor mr;
	
	/**
	 * 보고서 목록
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportList", method = RequestMethod.GET)
	public String reportList(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws Exception {

		model.addAllAttributes(reportService.getReportList(param));

		return request.getAttribute("VIEW_PATH") + "/report/reportList";

	}
	
	/**
	 * 보고서 완료 취소 처리
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportCancelFormAjax")
	@ResponseBody
	public ResultVO reportCancelFormAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "reportSeq")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}
		
		try {
			reportService.cancelReport(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
	
	/**
	 * 보고서 마감일 지정 처리
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveLimitDtFormAjax")
	@ResponseBody
	public ResultVO saveLimitDtFormAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "reportSeq", "limitDt")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}
		
		try {
			reportService.saveLimitDt(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
	
	/**
	 * 리포트 관리 목록 엑셀 다운로드
	 *
	 * @param param
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportListExcelDownload", method = RequestMethod.GET)
	public String reportListExcelDownload(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		request.setAttribute(SiteMeshFilter.SITE_MESH_DECORATOR_KEY, SiteMeshDecoratorSelector.NONE);

		model.addAttribute("list", reportService.getReportListAll(param));

		return request.getAttribute("VIEW_PATH") + "/report/reportListExcel";
	}
	
	/**
	 * 히스토리 조회
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCompanyHistoryAjax")
	@ResponseBody
	public ResultVO getCompanyHistoryAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<CamelMap> map = new ArrayList<CamelMap>();
		try {
			map = reportService.getReportListAll(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultDataVO<List<CamelMap>>(ResultVO.SUCCESS, map);
	}
	
	/**
	 * 점검 완료 처리
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportConfProcAjax")
	@ResponseBody
	public ResultVO reportConfProcAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "reportSeq")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}
		
		try {
			param.put("userId", AuthUtil.getAuth(request).getUserId());
			param.put("auth", AuthUtil.getAuth(request).getAuth());
			reportService.reportConfProcAjax(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
}
