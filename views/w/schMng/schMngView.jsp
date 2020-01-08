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
				<span class="title">일정 상세</span>
				<span class="path">
					<strong>일정관리</strong>
					<i>&lt;</i> ${AUTH.userNm}<i>&lt;</i> ${AUTH.positionNm} 
					<img src="${STATIC_COMMON_URL}/images/icon_path.png">
				</span>
			</h2>
			
			<!-- s: group01 -->
			<div class="group01">
				<div class="title">
					<span class="font09">
						<c:if test="${schMng.scheduleGubun eq '01'}">[공사] </c:if>
						<c:if test="${schMng.scheduleGubun eq '02'}">[점검] </c:if>
						<c:if test="${schMng.scheduleGubun eq '03'}">[휴가] </c:if>
						<c:if test="${schMng.scheduleGubun eq '04'}">[방화관리] </c:if>
						${schMng.companyNm }
					</span>
					
					<div class="fr pb10 ">
						<button class="btn_con_search" onClick="history.go(-1); return false ;">목록으로</button>
					</div>
					<c:if test="${schMng.confirmYn eq 'Y'}">
						<br/>
						<span class="font23" style="font-weight: bold;"> * 완료된 일정입니다. (${fn:substring(schMng.confirmDate,0,10) }&nbsp;${schMng.confirmNm }&nbsp;${schMng.confirmPositionNm })</span>
					</c:if>
				</div>
			
				<!-- s: list_detail -->
				<div class="list_detail">
					<ul class="list_detail_info">
						<li>
							<dt>일정 구분</dt>
							<dd>
								<c:if test="${schMng.scheduleGubun eq '01'}"><font style="color:#${schMng.colorCd }">●&nbsp;&nbsp;</font>공사</c:if>
								<c:if test="${schMng.scheduleGubun eq '02'}"><font style="color:#${schMng.colorCd }">●&nbsp;&nbsp;</font>점검</c:if>
								<c:if test="${schMng.scheduleGubun eq '03'}"><font style="color:#${schMng.colorCd }">●&nbsp;&nbsp;</font>휴가</c:if>
								<c:if test="${schMng.scheduleGubun eq '04'}"><font style="color:#${schMng.colorCd }">●&nbsp;&nbsp;</font>방화관리</c:if>
							</dd>
						</li>
						<li>
							<dt>업체 명</dt>
							<dd>
								<span style="float: left;">${schMng.companyNm }</span>
								<div><img src="${STATIC_COMMON_URL}/images/icon_info_s.png" style="margin-left: 7px; cursor: pointer;" onClick="javascript:historyView(this);"></div>
								<!-- s: histoty layer pop-up -->
								<div class ="favorite_md_ly" style="width: 1000px;" id ="favorite_md_ly">
									<div class="btn_close_box"><a href="#" onclick="closeHistory(); return false;"><img src="${STATIC_COMMON_URL}/images/btn_pop_close.png"></a></div>
									<ul style="margin-left: 0px;border-top: 0px;border-bottom: 0px;background-color: white;" id="favorite_md_ul">
									</ul>
								</div>
								<!-- e: histoty layer pop-up -->
							</dd>
						</li>
						<li>
							<dt>업체 주소</dt>
							<dd><span style="float: left;">${schMng.companyAddr }</span></dd>
						</li>
						<li>
							<dt>
								<c:if test="${schMng.scheduleGubun eq '01'}">담당자</c:if>
								<c:if test="${schMng.scheduleGubun eq '02'}">담당자</c:if>
								<c:if test="${schMng.scheduleGubun eq '03'}">휴가자</c:if>
								<c:if test="${schMng.scheduleGubun eq '04'}">담당자</c:if>
							</dt>
							<dd>${schMng.mngNm }</dd>
						</li>
						<li>
							<dt>일정</dt>
							<dd>
								<span class="start_date">
									${fn:substring(schMng.startDt,0,4)}-${fn:substring(schMng.startDt,4,6 )}-${fn:substring(schMng.startDt,6,8)}<c:if test="${schMng.startTime ne '' or not empty schMng.startTime}">&nbsp;${fn:substring(schMng.startTime,0,2)}:${fn:substring(schMng.startTime,2,4)}</c:if>
								</span>
								<span class="close_date">${fn:substring(schMng.endDt,0,4)}-${fn:substring(schMng.endDt,4,6)}-${fn:substring(schMng.endDt,6,8)}<c:if test="${schMng.endTime ne '' or not empty schMng.endTime}">&nbsp;${fn:substring(schMng.endTime,0,2)}:${fn:substring(schMng.endTime,2,4)}</c:if></span>
							</dd>
						</li>
						<li>
							<dt>동행자</dt>
							<dd>
								<c:if test="${fn:length(schMngUser) > 0 }">
									<c:forEach items="${schMngUser }" var="item" varStatus="vst">
										${item.userNm }&nbsp;${item.positionNm }
										<c:if test="${!vst.last }">,&nbsp;</c:if>
									</c:forEach>
								</c:if>
							</dd>
						</li>
					</ul>
					<ul class="list_detail_more">
						<li>
							<div class="text_view">
								<% pageContext.setAttribute("newLineChar", "\n"); %>
								${fn:replace( schMng.scheduleMemo , newLineChar, '<br/>')}
							</div>
						</li>
					</ul>
				</div>
				<!-- e: list_detail -->
			</div>
			<!-- e: group01 -->	
		</section>
		<!-- e: type01 -->
	</div>
	<!-- e: wrap_in01 -->	
</div>		
<!-- e: wrap -->

<script type="text/javascript">

	// 히스토리
	function historyView(obj){
		closeHistory();
		
		$.ajax({
			url: '/report/getCompanyHistoryAjax',
			dataType: 'json',	
			data: { companySeq : '${schMng.companySeq}' },
			type: 'POST',
			async : false,
			success: function(data) {
				if(data.status == 'success'){
					var resultData = data.resultData;
					var html = '';
					html += '<div style="overflow-y: auto; max-height: 360px;">';
					html += '<table class="tbl01">';
					html += '<colgroup>';
					html += '<col width="5%">';
					html += '<col width="10%">';
					html += '<col width="10%">';
					html += '<col width="8%">';
					html += '<col width="10%">';
					html += '<col width="10%">';
					html += '<col width="9%">';
					html += '<col width="13%">';
					html += '</colgroup>';
					html += '<thead>';
					html += '<tr>';
					html += '<th>No.</th>';
					html += '<th>일정 시작</th>';
					html += '<th>일정 종료</th>';
					html += '<th>담당자</th>';
					html += '<th>점검 상태</th>';
					html += '<th>보고서 상태</th>';
					html += '<th>보고서 확인일</th>';
					html += '<th>보고서 제출일</th>';
					html += '</tr>';
					html += '</thead>';
					html += '<tbody>';
					
					if(resultData.length == 0){
						html += '<tr>';
						html += '<td colspan="8">조회된 내역이 없습니다.</td>';
						html += '</tr>';
						return;
					}
					
					$.each(resultData, function(key, val){
						html += '<tr>';
						html += '<td>' + val.reportSeq + '</td>';
						var startDt = val.startDt.substring(0,4) + '-' + val.startDt.substring(4,6) + '-' + val.startDt.substring(6,8);
						var endDt = val.endDt.substring(0,4) + '-' + val.endDt.substring(4,6) + '-' + val.endDt.substring(6,8);
						var startTime = '';
						if(val.startTime == '' || val.startTime == null){
							startTime = '';
						}else{
							startTime = val.startTime.substring(0,2) + ':' + val.startTime.substring(2,4);
						}
						var endTime = '';
						if(val.endTime == '' || val.endTime == null){
							endTime = '';
						}else{
							endTime = val.endTime.substring(0,2) + ':' + val.endTime.substring(2,4);
						}
						html += '<td>' + startDt + ' ' + startTime + '</td>';
						html += '<td>' + endDt + ' ' + endTime + '</td>';
						var mngNm = '';
						if(val.mngNm == '' || val.mngNm == null){
							mngNm = '';
						}else{
							mngNm = val.mngNm;
						}
						html += '<td>' + mngNm + '</td>';
						var confirmYn = '';
						if(val.confirmYn == 'Y'){
							confirmYn = '완료 (' + val.confirmNm + ' ' + val.confirmPositionNm + ")";
						}else{
							confirmYn = '미완료';
						}
						html += '<td>' + confirmYn + '</td>';
						var mngYn = '';
						if(val.mngYn == 'Y'){
							mngYn = '완료';
						}else{
							mngYn = '미완료';
						}
						html += '<td>' + mngYn + '</td>';
						var mngDt = '';
						if(val.mngDt == null){
							mngDt = '';
						}else{
							mngDt = val.mngDt;
						}
						html += '<td>' + mngDt + '</td>';
						var limitDt = '';
						if(val.limitDt == null){
							limitDt = '';
						}else{
							limitDt = val.limitDt;
						}
						html += '<td>' + limitDt + '</td>';
						html += '</tr>';
					});
					
					html += '</tbody>';
					html += '</table>';
					
					$('#favorite_md_ul').html(html);
					$('#favorite_md_ly').css('left', obj.offsetLeft-39);
					$('#favorite_md_ly').show();
				}else{
					alert(data.msg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
	
	// 히스토리 레이어 팝업 닫기
	function closeHistory(){
		$('#favorite_md_ly').hide();
	}
	
</script>