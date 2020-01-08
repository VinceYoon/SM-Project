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
				<span class="title">방화 관리</span>
			</h2>
			<!-- s: group01 -->
			<div class="group01">
				<div class="title"></div>
				<!-- s: search_box -->
				<div class="search_box">
					<!-- s: form -->
					<form id="frmSearch" name="frmSearch" method ="get" action="/fire/fireCtrList">
						<ul>
							<li>
								<dt>연도</dt>
								<dd>
									<div class="selectbox" style="width:100px;">
										<label for="year">${paramVO.year }</label>
										<select id="year" name="year">
											<c:forEach var="item" begin="2019" end="${currYear }">
												<option value="${item }" <c:if test="${item eq paramVO.year }">selected="selected"</c:if>>${item }</option>
											</c:forEach>
										</select>
									</div>
								</dd>
							</li>
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
					<table class="tbl05">
						<colgroup>
							<col width="8%">
							<col width="7%">
							<col width="7%">
							<col width="7%">
							<col width="7%">
							<col width="7%">
							<col width="7%">
							<col width="7%">
							<col width="7%">
							<col width="7%">
							<col width="7%">
							<col width="7%">
							<col width="7%">
						</colgroup>
						<thead>
							<tr>
								<th rowspan="2">업체명</th>
								<th colspan="12">${paramVO.year }</th>
							</tr>
							<tr>
								<th>1월</th>
								<th>2월</th>
								<th>3월</th>
								<th>4월</th>
								<th>5월</th>
								<th>6월</th>
								<th>7월</th>
								<th>8월</th>
								<th>9월</th>
								<th>10월</th>
								<th>11월</th>
								<th>12월</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${fireCtrList }" varStatus="status">
								<tr>
									<td>${item.companyNm }</td>
									<td <c:if test="${not empty item.jan }">class="status04"</c:if> <c:if test="${empty item.jan }">class="status01"</c:if>>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.jan,0,8) }</div>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.jan,9,fn:length(item.jan)) }</div>
										<c:if test="${AUTH.auth eq '02' or AUTH.auth eq '04' }">
											<c:if test="${not empty item.jan }"><button type="button" class="btn_table_red" style="margin-top: 5px;" data-companycheckseq="${item.janSeq }" onclick="javascript:clearFireCtr(this); return false;">취소</button></c:if>
											<c:if test="${empty item.jan and currMonth eq '01' }"><button type="button" class="btn_table_blue" style="margin-top: 5px;" data-companyseq="${item.companySeq }" onclick="javascript:addFireCtr(this); return false;">확인</button></c:if>
										</c:if>
									</td>
									<td <c:if test="${not empty item.feb }">class="status04"</c:if> <c:if test="${empty item.feb }">class="status01"</c:if>>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.feb,0,8) }</div>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.feb,9,fn:length(item.feb)) }</div>
										<c:if test="${AUTH.auth eq '02' or AUTH.auth eq '04' }">
											<c:if test="${not empty item.feb }"><button type="button" class="btn_table_red" style="margin-top: 5px;" data-companycheckseq="${item.febSeq }" onclick="javascript:clearFireCtr(this); return false;">취소</button></c:if>
											<c:if test="${empty item.feb and currMonth eq '02' }"><button type="button" class="btn_table_blue" style="margin-top: 5px;" data-companyseq="${item.companySeq }" onclick="javascript:addFireCtr(this); return false;">확인</button></c:if>
										</c:if>
									</td>
									<td <c:if test="${not empty item.mar }">class="status04"</c:if> <c:if test="${empty item.mar }">class="status01"</c:if>>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.mar,0,8) }</div>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.mar,9,fn:length(item.mar)) }</div>
										<c:if test="${AUTH.auth eq '02' or AUTH.auth eq '04' }">
											<c:if test="${not empty item.mar }"><button type="button" class="btn_table_red" style="margin-top: 5px;" data-companycheckseq="${item.marSeq }" onclick="javascript:clearFireCtr(this); return false;">취소</button></c:if>
											<c:if test="${empty item.mar and currMonth eq '03' }"><button type="button" class="btn_table_blue" style="margin-top: 5px;" data-companyseq="${item.companySeq }" onclick="javascript:addFireCtr(this); return false;">확인</button></c:if>
										</c:if>
									</td>
									<td <c:if test="${not empty item.apr }">class="status04"</c:if> <c:if test="${empty item.apr }">class="status01"</c:if>>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.apr,0,8) }</div>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.apr,9,fn:length(item.apr)) }</div>
										<c:if test="${AUTH.auth eq '02' or AUTH.auth eq '04' }">
											<c:if test="${not empty item.apr }"><button type="button" class="btn_table_red" style="margin-top: 5px;" data-companycheckseq="${item.aprSeq }" onclick="javascript:clearFireCtr(this); return false;">취소</button></c:if>
											<c:if test="${empty item.apr and currMonth eq '04' }"><button type="button" class="btn_table_blue" style="margin-top: 5px;" data-companyseq="${item.companySeq }" onclick="javascript:addFireCtr(this); return false;">확인</button></c:if>
										</c:if>
									</td>
									<td <c:if test="${not empty item.may }">class="status04"</c:if> <c:if test="${empty item.may }">class="status01"</c:if>>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.may,0,8) }</div>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.may,9,fn:length(item.may)) }</div>
										<c:if test="${AUTH.auth eq '02' or AUTH.auth eq '04' }">
											<c:if test="${not empty item.may }"><button type="button" class="btn_table_red" style="margin-top: 5px;" data-companycheckseq="${item.maySeq }" onclick="javascript:clearFireCtr(this); return false;">취소</button></c:if>
											<c:if test="${empty item.may and currMonth eq '05' }"><button type="button" class="btn_table_blue" style="margin-top: 5px;" data-companyseq="${item.companySeq }" onclick="javascript:addFireCtr(this); return false;">확인</button></c:if>
										</c:if>
									</td>
									<td <c:if test="${not empty item.jun }">class="status04"</c:if> <c:if test="${empty item.jun }">class="status01"</c:if>>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.jun,0,8) }</div>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.jun,9,fn:length(item.jun)) }</div>
										<c:if test="${AUTH.auth eq '02' or AUTH.auth eq '04' }">
											<c:if test="${not empty item.jun }"><button type="button" class="btn_table_red" style="margin-top: 5px;" data-companycheckseq="${item.junSeq }" onclick="javascript:clearFireCtr(this); return false;">취소</button></c:if>
											<c:if test="${empty item.jun and currMonth eq '06' }"><button type="button" class="btn_table_blue" style="margin-top: 5px;" data-companyseq="${item.companySeq }" onclick="javascript:addFireCtr(this); return false;">확인</button></c:if>
										</c:if>
									</td>
									<td <c:if test="${not empty item.jul }">class="status04"</c:if> <c:if test="${empty item.jul }">class="status01"</c:if>>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.jul,0,8) }</div>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.jul,9,fn:length(item.jul)) }</div>
										<c:if test="${AUTH.auth eq '02' or AUTH.auth eq '04' }">
											<c:if test="${not empty item.jul }"><button type="button" class="btn_table_red" style="margin-top: 5px;" data-companycheckseq="${item.julSeq }" onclick="javascript:clearFireCtr(this); return false;">취소</button></c:if>
											<c:if test="${empty item.jul and currMonth eq '07' }"><button type="button" class="btn_table_blue" style="margin-top: 5px;" data-companyseq="${item.companySeq }" onclick="javascript:addFireCtr(this); return false;">확인</button></c:if>
										</c:if>
									</td>
									<td <c:if test="${not empty item.aug }">class="status04"</c:if> <c:if test="${empty item.aug }">class="status01"</c:if>>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.aug,0,8) }</div>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.aug,9,fn:length(item.aug)) }</div>
										<c:if test="${AUTH.auth eq '02' or AUTH.auth eq '04' }">
											<c:if test="${not empty item.aug }"><button type="button" class="btn_table_red" style="margin-top: 5px;" data-companycheckseq="${item.augSeq }" onclick="javascript:clearFireCtr(this); return false;">취소</button></c:if>
											<c:if test="${empty item.aug and currMonth eq '08' }"><button type="button" class="btn_table_blue" style="margin-top: 5px;" data-companyseq="${item.companySeq }" onclick="javascript:addFireCtr(this); return false;">확인</button></c:if>
										</c:if>
									</td>
									<td <c:if test="${not empty item.sep }">class="status04"</c:if> <c:if test="${empty item.sep }">class="status01"</c:if>>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.sep,0,8) }</div>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.sep,9,fn:length(item.sep)) }</div>
										<c:if test="${AUTH.auth eq '02' or AUTH.auth eq '04' }">
											<c:if test="${not empty item.sep }"><button type="button" class="btn_table_red" style="margin-top: 5px;" data-companycheckseq="${item.sepSeq }" onclick="javascript:clearFireCtr(this); return false;">취소</button></c:if>
											<c:if test="${empty item.sep and currMonth eq '09' }"><button type="button" class="btn_table_blue" style="margin-top: 5px;" data-companyseq="${item.companySeq }" onclick="javascript:addFireCtr(this); return false;">확인</button></c:if>
										</c:if>
									</td>
									<td <c:if test="${not empty item.oct }">class="status04"</c:if> <c:if test="${empty item.oct }">class="status01"</c:if>>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.oct,0,8) }</div>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.oct,9,fn:length(item.oct)) }</div>
										<c:if test="${AUTH.auth eq '02' or AUTH.auth eq '04' }">
											<c:if test="${not empty item.oct }"><button type="button" class="btn_table_red" style="margin-top: 5px;" data-companycheckseq="${item.octSeq }" onclick="javascript:clearFireCtr(this); return false;">취소</button></c:if>
											<c:if test="${empty item.oct and currMonth eq '10' }"><button type="button" class="btn_table_blue" style="margin-top: 5px;" data-companyseq="${item.companySeq }" onclick="javascript:addFireCtr(this); return false;">확인</button></c:if>
										</c:if>
									</td>
									<td <c:if test="${not empty item.nov }">class="status04"</c:if> <c:if test="${empty item.nov }">class="status01"</c:if>>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.nov,0,8) }</div>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.nov,9,fn:length(item.nov)) }</div>
										<c:if test="${AUTH.auth eq '02' or AUTH.auth eq '04' }">
											<c:if test="${not empty item.nov }"><button type="button" class="btn_table_red" style="margin-top: 5px;" data-companycheckseq="${item.novSeq }" onclick="javascript:clearFireCtr(this); return false;">취소</button></c:if>
											<c:if test="${empty item.nov and currMonth eq '11' }"><button type="button" class="btn_table_blue" style="margin-top: 5px;" data-companyseq="${item.companySeq }" onclick="javascript:addFireCtr(this); return false;">확인</button></c:if>
										</c:if>
									</td>
									<td <c:if test="${not empty item.dec }">class="status04"</c:if> <c:if test="${empty item.dec }">class="status01"</c:if>>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.dec,0,8) }</div>
										<div class="ellipsis_txt6 tgnc">${fn:substring(item.dec,9,fn:length(item.dec)) }</div>
										<c:if test="${AUTH.auth eq '02' or AUTH.auth eq '04' }">
											<c:if test="${not empty item.dec }"><button type="button" class="btn_table_red" style="margin-top: 5px;" data-companycheckseq="${item.decSeq }" onclick="javascript:clearFireCtr(this); return false;">취소</button></c:if>
											<c:if test="${empty item.dec and currMonth eq '12' }"><button type="button" class="btn_table_blue" style="margin-top: 5px;" data-companyseq="${item.companySeq }" onclick="javascript:addFireCtr(this); return false;">확인</button></c:if>
										</c:if>
									</td>
								</tr>
							</c:forEach>
								
							<c:if test="${fn:length(fireCtrList) == 0}">
								<tr>
									<td colspan="13" style="text-align: center" >목록이 없습니다.</td>
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
							<span class="font18">방화 관리 목록 다운로드</span>
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
			pageNo : pageNo || '${param.pageNo}'
		};

		return $.param(param);
	}
	
	// 페이징
	function paging(pageNo){
		location.href = '/fire/fireCtrList?' + getParam(pageNo);
	}
	
	// 엑셀다운로드
	function excelDown(){
		$('#frmSearch').attr('action', '/fire/fireCtrListExcelDownload').submit();
	}
	
	// 지역(시)검색 조건 변경 이벤트
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
	
	// 방화 관리 완료 취소 처리
	function clearFireCtr(obj){
		var companyCheckSeq = $(obj).data('companycheckseq');
		if(companyCheckSeq != ""){
			$.ajax({
				url: '/fire/fireCtrDelFormAjax',
				dataType: 'json',
				data: {'companyCheckSeq': companyCheckSeq },
				type: 'get',
				async : false,
				success: function(data) {
			        if(data.status == 'success'){
			        	alert("취소 처리되었습니다.");
						// page reload
			        	location.href = location.href;
			        }else{
			        	alert(data.msg);
			        }
			    }
			})
		}else{
		}
	}
	
	// 방화 완료 처리
	function addFireCtr(obj){
		var companySeq = $(obj).data('companyseq');
		if(companySeq != ""){
			$.ajax({
				url: '/fire/fireCtrRegFormAjax',
				dataType: 'json',
				data: {'companySeq': companySeq },
				type: 'get',
				async : false,
				success: function(data) {
			        if(data.status == 'success'){
			        	alert("완료 처리되었습니다.");
						// page reload
			        	location.href = location.href;
			        }else{
			        	alert(data.msg);
			        }
			    }
			})
		}else{
		}
	}
	
</script>