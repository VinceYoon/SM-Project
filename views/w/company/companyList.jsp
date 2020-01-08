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
				<span class="title">업체 관리</span>
			</h2>
			<!-- s: group01 -->
			<div class="group01">
				<div class="title"></div>
				<!-- s: search_box -->
				<div class="search_box">
					<!-- s: form -->
					<form id="frmSearch" name="frmSearch" method ="get" action="/company/companyList">
						<ul>
							<li>
							<dt>검색 구분</dt>
							<dd>
								<div class="selectbox" style="width:80px;"> 
									<label for="citySelect">전체</label> 
									<select id="citySelect" name="citySelect"> 
										<option value="" selected>전체</option>
										<c:forEach var="item" items="${cityList }" varStatus="status">
											<option value="${item.code }">${item.codeNm }</option> 
										</c:forEach>
									</select>
								</div>
								<div class="selectbox" style="width:210px; margin-left:5px;"> 
									<label for="townSelect">전체</label> 
									<select id="townSelect" name="townSelect">
										<option value="" selected>전체</option>
									</select>
								</div>
							</dd>
							</li>
							<li>
								<dt>업체명</dt>
								<dd>
									<input type="text" style="width: 200px;" name="companyNm" maxlength="10" value="${param.companyNm}"/>
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
							<c:if test="${AUTH.auth eq '02'}">
								<col width="3%">
							</c:if>
							<col width="10%">
							<col width="3%">
							<col width="5%">
							<col width="7%">
							<col width="20%">
							<col width="5%">
							<col width="10%">
							<col width="3%">
							<col width="20%">
						</colgroup>
						<thead>
							<tr>
								<c:if test="${AUTH.auth eq '02'}">
									<th>선택</th>
								</c:if>
								<th>업체명</th>
								<th>점검이력</th>
								<th>시</th>
								<th>구/군</th>
								<th>주소</th>
								<th>담당자</th>
								<th>연락처</th>
								<th>구분</th>
								<th>특이사항</th>
						</thead>
						<tbody>
							<c:forEach var="item" items="${companyList }" varStatus="status">
								<tr>
									<c:if test="${AUTH.auth eq '02'}">
										<td><input type="checkbox" id="${item.companySeq }" name="companySeq" value="${item.companySeq }"/><label for="${item.companySeq }"><span></span></label></td>
									</c:if>
									<c:choose>
										<c:when test="${AUTH.auth eq '02'}">
											<td><a href="#" onclick="editForm('${item.companySeq }'); return false">${item.companyNm }</a></td>
										</c:when>
										<c:otherwise>
											<td>${item.companyNm }</td>
										</c:otherwise>
									</c:choose>
									<td>
										<div><img src="${STATIC_COMMON_URL}/images/icon_info_s.png" style="margin-left: 7px; cursor: pointer;" onClick="javascript:historyView('${item.companySeq }', this);"></div>
										<!-- s: histoty layer pop-up -->
										<div class ="favorite_md_ly" style="width: 1000px; margin-left: -19px;" id ="favorite_md_ly">
											<div class="btn_close_box"><a href="#" onclick="closeHistory(); return false;"><img src="${STATIC_COMMON_URL}/images/btn_pop_close.png"></a></div>
											<ul style="margin-left: 0px;border-top: 0px;border-bottom: 0px;background-color: white;" id="favorite_md_ul">
											</ul>
										</div>
										<!-- e: histoty layer pop-up -->
									</td>
									<td>${item.codeSiNm }</td>
									<td>${item.codeGuNm }</td>
									<td>${item.companyAddr }</td>
									<td>${item.mngNm }</td>
									<td>${item.phone }</td>
									<td>${item.companyTypeNm }</td>
									<td>${item.etc }</td>
								</tr>
							</c:forEach>	
							<c:if test="${fn:length(companyList) == 0}">
								<tr>
									<td colspan="10" style="text-align: center" >목록이 없습니다.</td>
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

					<!-- s: btn_box_con -->
					<c:if test="${AUTH.auth eq '02'}">	
						<div class="btn_box_con">
							<button class="btn_admin_red" onClick="location.href='/company/companyRegForm'">업체등록</button>
							<button class="btn_admin_gray" onClick="delForm();">업체삭제</button>
						</div>
					</c:if>
					<!-- e: btn_box_con -->
					
					<!-- s: download_excel -->
					<ul class="download_excel">
						<li>
							<button class="btn_admin_excel" style="width:200px" onclick="excelDown();"><img src="${STATIC_COMMON_URL}/images/icon_excel.png"> 엑셀 다운로드</button>&nbsp;&nbsp;&nbsp;
							<span class="font18">업체 목록 다운로드</span>
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
	$(document).ready(function(){
		//셀렉트 박스 초기화
		initSelbox();
		// 페이지 로딩
		pageInit();
	});

	$("input").keydown(function(key) {
		if (key.keyCode == 13) {
			searchForm();
		}
	});
	
	// 페이지 로딩
	function pageInit(){
		// select 검색창 값 유지
		$('#citySelect').val('${param.citySelect}').change();
		$('#townSelect').val('${param.townSelect}').change();
	}
	
	// 검색	
	function searchForm(){
		$('#frmSearch').submit();
	}
	
	// 파라미터 처리 및 조회
	function getParam(pageNo){
		var param = {
			citySelect : '${param.citySelect}',
			townSelect : '${param.townSelect}',
			companyNm : '${param.companyNm}',
			pageNo : pageNo || '${param.pageNo}'
		};

		return $.param(param);
	}
	
	// 페이징
	function paging(pageNo){
		location.href = '/company/companyList?' + getParam(pageNo);
	}
	
	// 히스토리
	function historyView(companySeq, obj){
		closeHistory();
		
		$.ajax({
			url: '/report/getCompanyHistoryAjax',
			dataType: 'json',	
			data: { companySeq : companySeq },
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
					$('#favorite_md_ly').css('top', obj.offsetParent.offsetTop+340);
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
	
	// 엑셀다운로드
	function excelDown(){
		$('#frmSearch').attr('action', '/company/companyListExcelDownload').submit();
	}
	
	// 업체명 클릭 - 업체 관리 수정 처리
	function editForm(companySeq){
		var param = {
				companySeq: companySeq,
				queryString: getParam()
		};
			
		location.href = '/company/companyEditForm?' + $.param(param);
	}
	
	//지역(시)검색 조건 변경 이벤트
	$('#citySelect').change(function(){
		citySelect();
	});
	
	// 지역 선택시 지역 세부 목록 ajax 처리
	function citySelect(){
		var city = $('#citySelect').val();
		if(city != ""){
			$.ajax({
				url: '/company/townAjax',
				dataType: 'json',
				data: {'city': city },
				type: 'get',
				async : false,
				success: function(data) {
			        if(data.status == 'success'){
						$('#townSelect').removeOption(/./);
						$('#townSelect').addOption("", "전체");
						$.each(data.resultData, function(index, item){
			        		$('#townSelect').addOption(item.code, item.codeNm);
			        	});
						
						$('#townSelect').selectOptions('').change();
			        }else{
			        	alert(data.msg);
			        }
			    }
			})
		}else{
			$('#townSelect').removeOption(/./);
			$('#townSelect').addOption("", "전체");
			$('#townSelect').selectOptions('').change();
		}
	}
	
	// 업체 삭제 처리
	function delForm(){
		if(confirm('삭제하시겠습니까?')){
			var $companySeqs = $('input:checkbox:checked');
			
			if($companySeqs.length == 0){
				alert('삭제할 업체를 선택해주세요.');
				return;
			}
			
			$.ajax({
				url: '/company/companyDelFormAjax',
				dataType: 'json',	
				data: $companySeqs.serialize(),
				type: 'POST',
				async : false,
				success: function(data) {
					if(data.status == 'success'){
						alert('삭제되었습니다.');
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