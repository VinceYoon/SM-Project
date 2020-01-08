package com.smProject.web;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.smProject.service.CompanyService;
import com.smProject.sitemesh.SiteMeshDecoratorSelector;
import com.smProject.sitemesh.SiteMeshFilter;
import com.smProject.util.StringUtil;
import com.smProject.util.exception.BizException;
import com.smProject.vo.ResultDataVO;
import com.smProject.vo.ResultVO;

@Controller
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	CompanyService companyService;
	
	@Autowired
	private MessageSourceAccessor mr;
	
	/**
	 * 업체 관리 목록
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/companyList", method = RequestMethod.GET)
	public String companyList(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws Exception {

		model.addAllAttributes(companyService.getCompanyList(param));

		return request.getAttribute("VIEW_PATH") + "/company/companyList";

	}

	/**
	 * 지역 상세 조회 
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/townAjax")
	@ResponseBody
	public ResultVO townList(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "city")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}

		List<CamelMap> map = new ArrayList<CamelMap>();
		try {
			map = companyService.getTownList(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultDataVO<List<CamelMap>>(ResultVO.SUCCESS, map);
	}
	
	/**
	 * 업체 관리 등록폼
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/companyRegForm", method = RequestMethod.GET)
	public String companyRegForm(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws Exception {
		
		// 업체 구분 (종합, 작동)
		model.addAllAttributes(companyService.getCompanyTypeList(param));
		
		// 지역 구분 (서울경기, 서울시, 경기도)
		model.addAllAttributes(companyService.getCityList(param));

		return request.getAttribute("VIEW_PATH") + "/company/companyRegForm";

	}
	
	/**
	 * 업체 관리 등록 처리
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/companyRegFormAjax")
	@ResponseBody
	public ResultVO companyRegProc(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "companyNm", "companyType", "mngNm", "phone", "codeSi", "codeGu", "companyAddr")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}

		try {
			companyService.insertCompany(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
	
	/**
	 * 업체 관리 수정 폼
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/companyEditForm", method = RequestMethod.GET)
	public String companyEditForm(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws Exception {

		// 업체 정보 조회
		model.addAllAttributes(companyService.getCompany(param));
				
		// 업체 구분 (종합, 작동)
		model.addAllAttributes(companyService.getCompanyTypeList(param));
		
		// 지역 구분 (서울경기, 서울시, 경기도)
		model.addAllAttributes(companyService.getCityList(param));
		
		return request.getAttribute("VIEW_PATH") + "/company/companyEditForm";
	}
	
	/**
	 * 업체 관리 수정 처리
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/companyEditFormAjax")
	@ResponseBody
	public ResultVO companyEditFormAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "companyNm", "companyType", "mngNm", "phone", "codeSi", "codeGu", "companyAddr")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}

		try {
			companyService.updateCompany(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
	
	/**
	 * 업체 관리 삭제 처리
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/companyDelFormAjax")
	@ResponseBody
	public ResultVO companyDelFormAjax(@RequestParam List<String> companySeq, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if(companySeq == null && companySeq.size() == 0){
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}
		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("params", companySeq);
			
			companyService.deleteCompany(params);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
	
	/**
	 * 업체 전체 목록 조회
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/companyListAjax", method = RequestMethod.GET)
	@ResponseBody
	public ResultVO companyListAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ResultDataVO<List<CamelMap>>(ResultVO.SUCCESS, companyService.getCompanyListAll(param));
	}
	
	/**
	 * 업체 목록 엑셀 다운로드
	 *
	 * @param param
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/companyListExcelDownload", method = RequestMethod.GET)
	public String companyListExcelDownload(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		request.setAttribute(SiteMeshFilter.SITE_MESH_DECORATOR_KEY, SiteMeshDecoratorSelector.NONE);

		model.addAttribute("list", companyService.getCompanyListAll(param));

		return request.getAttribute("VIEW_PATH") + "/company/companyListExcel";
	}
}
