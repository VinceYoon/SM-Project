<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="strUtil" uri="/WEB-INF/tld/strUtil.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- s: wrap -->
<div class="wrap">
	<!-- s: wrap_in01 -->
	<div class="wrap_in01">
		<!-- s: type01 -->
		<section class="type01">
			<h2>
				<span class="title">보고서</span>
			</h2>
			<!-- s: group01 -->
			<div class="group01">
				<div class="title"></div>
				<!-- s: search_box -->
				<div class="search_box">
					<!-- s: form -->
					<form id="frmSearch" name="frmSearch" method ="get" action="/report/reportList">
						<ul>
							<li>
								<dt>일정</dt>
								<dd>
									<input type="text" id="startDt" name="startDt" class="datepicker" style="width:110px;" readonly="readonly"  /> &nbsp;~&nbsp;
									<input type="text" id="endDt" name="endDt" class="datepicker" style="width:110px;" readonly="readonly"  />
								</dd>
							</li>
						</ul>
					</form>
					<!-- e: form -->
					<div class="fr pt10 pb10">
						<button class="btn_con_search" onclick="searchForm();">검색</button>
					</div>
				</div>
				<!-- e: search_box -->
				<!-- s: main_tbl -->
				<div class="main_tbl">
					<!-- s: table -->
					<table class="tbl01">
						<colgroup>
							<col width="5%">
							<col width="20%">
							<col width="10%">
							<col width="10%">
							<col width="8%">
							<col width="12%">
							<col width="12%">
							<col width="9%">
							<col width="13%">
						</colgroup>
						<thead>
							<tr>
								<th>No.</th>
								<th>업체명</th>
								<th>일정 시작</th>
								<th>일정 종료</th>
								<th>담당자</th>
								<th>점검 상태</th>
								<th>보고서 상태 </th>
								<th>보고서 확인일</th>
								<th>보고서 제출일</th>
							</tr>	
						</thead>
						<tbody>
							<c:forEach var="item" items="${reportList }" varStatus="status">
								<tr>
									<td>
										${item.reportSeq }
									</td>
<%-- 									<td class="${item.statusLabel }"> --%>
<!-- 										<div class="ellipsis_txt"> -->
<%-- 											${item.companyNm } --%>
<!-- 										</div> -->
<!-- 									</td> -->
									<td>
											${item.companyNm }
									</td>

									<td>
										${fn:substring(item.startDt,0,4)}-${fn:substring(item.startDt,4,6 )}-${fn:substring(item.startDt,6,8)}&nbsp;${fn:substring(item.startTime,0,2)}:${fn:substring(item.startTime,2,4)}
									</td>
									<td>
										<span>
										${fn:substring(item.endDt,0,4)}-${fn:substring(item.endDt,4,6)}-${fn:substring(item.endDt,6,8)}&nbsp;${fn:substring(item.endTime,0,2)}:${fn:substring(item.endTime,2,4)}
										</span>
									</td>
									<td>
										${item.mngNm }
									</td>
									<td>
										<c:choose>
											<c:when test="${item.confirmYn eq 'Y' }">
												 완료
												<br> 
												(${item.confirmNm }&nbsp;${item.confirmPositionNm })
											</c:when>
											<c:otherwise>
												 미완료
											</c:otherwise>											
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${item.mngYn eq 'Y' }">
												완료
												<br>
												<c:if test="${AUTH.auth eq '02'}">
													<button class="btn_table_red" style="margin-top: 2px;" onclick="cancelStatus(${item.reportSeq }, this); return false;">
														<img src="${STATIC_COMMON_URL}/images/btn_icon_setting2.png">  완료 취소
													</button>
												</c:if>
											</c:when>
											<c:otherwise>
												미완료
												<br>
												<c:if test="${AUTH.auth eq '02'}">
													<button type="button" class="btn_table_blue" style="margin-top: 2px;" onclick="reportConfProc(${item.reportSeq }, this); return false;">
														<img src="${STATIC_COMMON_URL}/images/btn_icon_setting2.png">  완료
													</button>
												</c:if>
											</c:otherwise>	
										</c:choose>
									</td>
									<td>
										${item.mngDt }
									</td>
									<td>
										<c:choose>
											<c:when test="${AUTH.auth eq '02'}">
												<input type="text" id="limitDt" name="limitDt"  style="width:80px; text-align: center; vertical-align: middle;" value="${item.limitDt }" maxlength="10" >
												<button type="button" class="btn_table_blue" onclick="saveLimitDt(${item.reportSeq },this); return false;">지정</button>
											</c:when>
											<c:otherwise>
												${item.limitDt }
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${fn:length(reportList) == 0}">
								<tr>
									<td colspan="9" style="text-align: center" >목록이 없습니다.</td>
								</tr>
							</c:if>
						</tbody>
					</table>
					<!-- e: table -->
					
					<!-- s: page_navi -->
					<div class="page_navi">
						${navi.prevBlock}
						${navi.pageList}
						${navi.nextBlock}
					</div>
					<!-- e: page_navi -->
					
					<!-- s: download_excel -->
					<ul class="download_excel">
						<li>
							<button class="btn_admin_excel" style="width:200px" onclick="excelDown();"><img src="${STATIC_COMMON_URL}/images/icon_excel.png"> 엑셀 다운로드</button>&nbsp;&nbsp;&nbsp;
							<span class="font18">보고서 관리 목록 다운로드</span>
						</li>
					</ul>
					<!-- e: download_excel -->

				</div>
				<!-- e: main_tbl -->
			</div>
			<!-- e: group01 -->
		</section>
		<!-- e: type01 -->
	</div>
	<!-- e: wrap_in01 -->
</div>
<!-- e: wrap -->

<script type="text/javascript">
	// key
	var reportSeq ="";
	$(document).ready(function(){
		// 페이지 로딩
		pageInit();
		// 달력 초기화
		initDatepicker();
	});

	$("input[name='limitDt']").on("keyup", function() {
	    $(this).val(addDash($(this).val().replace(/[^0-9]/g,"")));
	});
	
	$("input").keydown(function(key) {
		if (key.keyCode == 13) {
			searchForm();
		}
	});
	
	// 페이지 로딩
	function pageInit(){
		// select 검색창 값 유지
		$('#startDt').val('${paramVO.startDt}');
		$('#endDt').val('${paramVO.endDt}');
	}
	
	//달력 초기화
	function initDatepicker(){
		$(".datepicker").datepicker({
	    	dateFormat: 'yy-mm-dd',
	    	showOn: 'both',
	       	buttonImage:'${STATIC_COMMON_URL}/images/btn_calendar.png',
	       	buttonImageOnly:true
		});

		setDatepickerImgStyle();
	}
	
	// 검색	
	function searchForm(){
		$('#frmSearch').submit();
	}
	
	// 파라미터 처리 및 조회
	function getParam(pageNo){
		var param = {
			startDt : $('#startDt').val(),
			endDt : $('#endDt').val(),
			pageNo : pageNo || '${param.pageNo}'
			
		};
		return $.param(param);
	}
	
	// 페이징
	function paging(pageNo){
		location.href = '/report/reportList?' + getParam(pageNo);
	}
	
	// 엑셀다운로드
	function excelDown(){
		$('#frmSearch').attr('action', '/report/reportListExcelDownload').submit();
	}
	
	// 날짜 입력 자동 세팅
	function addDash(date){
		if(date.toString().length < 6){
			return date.toString().replace(/(\d{4})(\d{1})/, '$1-$2');
		}else if(date.toString().length < 7){
			return date.toString().replace(/(\d{4})(\d{2})/, '$1-$2');
		}else if(date.toString().length < 8){
			return date.toString().replace(/(\d{4})(\d{2})(\d{1})/, '$1-$2-$3');
		}else{
			return date.toString().replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
		}
	}
	
	// 보고서 완료 처리
	function reportConfProc(param, obj){
		reportSeq = String(param);
		if(confirm('보고서를 완료 상태로 변경하시겠습니까?')){
			$.ajax({
				url: '/report/reportConfProcAjax',
				dataType: 'json',	
				data: {'reportSeq': reportSeq },
				type: 'POST',
				async : false,
				success: function(data) {
					if(data.status == 'success'){
						alert('완료 처리 되었습니다.');
						paging(1);
					}else{
						alert(data.msg);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("처리 중 오류 발생");
				}
			});
		}
	}
	
	// 완료 취소 처리
	function cancelStatus(param, obj){
		reportSeq = String(param);
		if(confirm('완료 취소 하시겠습니까?')){
			$.ajax({
				url: '/report/reportCancelFormAjax',
				dataType: 'json',	
				data: {'reportSeq': reportSeq },
				type: 'POST',
				async : false,
				success: function(data) {
					if(data.status == 'success'){
						alert('완료 취소 되었습니다.');
						$(obj).parents('td').html('미완료	');
						paging(1);
					}else{
						alert(data.msg);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("처리 중 오류 발생");
				}
			});
		}
	}
	
	// 마감일 지정 처리
	function saveLimitDt(param, obj){
		reportSeq = String(param);
		var limitDt = $(obj).parents('td').find('[name=limitDt]').val().replaceAll('-','');
		if(confirm('마감일을 지정 하시겠습니까?')){
			$.ajax({
				url: '/report/saveLimitDtFormAjax',
				dataType: 'json',	
				data: {'reportSeq': reportSeq, 'limitDt': limitDt },
				type: 'POST',
				async : false,
				success: function(data) {
					if(data.status == 'success'){
						alert('마감일 지정이 완료 되었습니다.');
						paging(1);
					}else{
						alert(data.msg);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("처리 중 오류 발생");
				}
			});
		}
	}
	
</script>