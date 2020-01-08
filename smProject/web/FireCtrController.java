package com.smProject.web;

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
import com.smProject.service.FireCtrService;
import com.smProject.sitemesh.SiteMeshDecoratorSelector;
import com.smProject.sitemesh.SiteMeshFilter;
import com.smProject.util.StringUtil;
import com.smProject.util.exception.BizException;
import com.smProject.vo.ResultVO;

@Controller
@RequestMapping("/fire")
public class FireCtrController {
	
	@Autowired
	FireCtrService fireCtrService;
	
	@Autowired
	private MessageSourceAccessor mr;
	
	/**
	 * 방화 관리 목록
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fireCtrList", method = RequestMethod.GET)
	public String fireCtr(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws Exception {

		model.addAllAttributes(fireCtrService.getFireCtrList(param));

		return request.getAttribute("VIEW_PATH") + "/fire/fireCtrList";

	}
	
	/**
	 * 방화 관리 완료 취소 처리
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fireCtrDelFormAjax")
	@ResponseBody
	public ResultVO fireCtrDelFormAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "companyCheckSeq")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}
		
		try {
			fireCtrService.deleteFireCtr(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
	
	/**
	 * 방화 관리 완료 처리
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fireCtrRegFormAjax")
	@ResponseBody
	public ResultVO fireCtrRegFormAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "companySeq")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}
		
		try {
			param.put("userId", AuthUtil.getAuth(request).getUserId());
			fireCtrService.insertFireCtr(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
	
	/**
	 * 방화 관리 목록 엑셀 다운로드
	 *
	 * @param param
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fireCtrListExcelDownload", method = RequestMethod.GET)
	public String fireCtrListExcelDownload(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		request.setAttribute(SiteMeshFilter.SITE_MESH_DECORATOR_KEY, SiteMeshDecoratorSelector.NONE);

		model.addAttribute("list", fireCtrService.getFireCtrListAll(param));
		model.addAttribute("year", param.get("year"));

		return request.getAttribute("VIEW_PATH") + "/fire/fireCtrListExcel";
	}
}
