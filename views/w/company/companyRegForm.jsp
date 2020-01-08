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
				<span class="title">업체 등록</span>
				<span class="path">
					<strong>업체관리</strong>
					<i>&lt;</i> ${AUTH.userNm}<i>&lt;</i> ${AUTH.positionNm} 
					<img src="${STATIC_COMMON_URL}/images/icon_path.png">
				</span>
			</h2>
			
			<!-- s: group01 -->
			<div class="group01">
				<div class="title"><div class="fr pb10 "><button class="btn_con_search" onClick="location.href='/company/companyList'">목록으로</button></div></div>
			
				<!-- s: list_detail -->
				<div class="list_detail">
					<!-- s: form -->
					<form id="frmReg" name="frmReg" method ="post" action="/company/companyRegFormAjax">
						<ul>
							<li>
								<dt>업체 명 <font class="font05">*</font></dt>
								<dd>
									<input type="text" id="companyNm" name="companyNm" style="width:200px;"/>
								</dd>
							</li>
							<li>
								<dt>구분 <font class="font05">*</font></dt>
								<dd>
									<div class="selectbox" style="width:200px;"> 
										<label for="type_gubun">선택</label> 
										<select id="companyType" name ="companyType">
											<option value="">선택</option>
											<c:forEach var="item" items="${companyTypeList }" varStatus="status">
												<option value="${item.code }">${item.codeNm }</option> 
											</c:forEach>
										</select>
									</div>
								</dd>
							</li>
							<li>
								<dt>담당자 <font class="font05">*</font></dt>
								<dd>
									<input type="text" id="mngNm" name="mngNm" style="width:200px;"/>
								</dd>
							</li>
							<li>
								<dt>연락처 <font class="font05">*</font></dt>
								<dd>
									<input type="text" id="phone" name="phone" style="width:200px;"/>
									<font class="font03"> * (예시) 010-0000-0000</font>
								</dd>
							</li>
							<li>
								<dt>지역(시) <font class="font05">*</font></dt>
								<dd>
									<div class="selectbox" style="width:200px;"> 
										<label for="codeSi">선택</label> 
										<select id="codeSi" name="codeSi">
											<option value="">선택</option>
											<c:forEach var="item" items="${cityList }" varStatus="status">
												<option value="${item.code }">${item.codeNm }</option> 
											</c:forEach>
										</select>
									</div>
								</dd>
							</li>
							<li>
								<dt>지역 상세(구/군) <font class="font05">*</font></dt>
								<dd>
									<div class="selectbox" style="width:200px;"> 
										<label for="codeGu"></label> 
										<select id="codeGu" name="codeGu"></select>
									</div>
								</dd>
							</li>
							<li>
								<dt>주소 <font class="font05">*</font></dt>
								<dd>
									<input type="text" id="companyAddr" name="companyAddr" style="width:400px;"/>
								</dd>
							</li>
							<li>
								<dt>특이사항</dt>
								<dd>
									<textarea rows="5" cols="62" id="etc" name="etc"></textarea>
								</dd>
							</li>
						</ul>
					</form>	
					<!-- e: form -->
				</div>
				<!-- e: list_detail -->
				
				<div class="btn_box_con">
					<button class="btn_admin_red" onClick="regForm();">등록</button> 
					<button class="btn_admin_gray" onClick="cancelForm();">취소</button>
				</div>
			</div>
			<!-- e: group01 -->	
		</section>
		<!-- e: type01 -->
	</div>
	<!-- e: wrap_in01 -->	
</div>		
<!-- e: wrap -->

<script type="text/javascript">
	
	$(function(){
		initSelbox();
	});
	
	//지역(시)검색 조건 변경 이벤트
	$('#codeSi').change(function(){
		citySelect();
	});
	
	// 지역 선택시 지역 세부 목록 ajax 처리
	function citySelect(){
		var city = $('#codeSi').val();
		if(city != ""){
			$.ajax({ 
				url: '/company/townAjax',
				dataType: 'json',
				data: {'city': city },
				type: 'get',
				success: function(data) {
			        if(data.status == 'success'){
						$('#codeGu').removeOption(/./);
			        	$.each(data.resultData, function(index, item){
			        		$('#codeGu').addOption(item.code, item.codeNm);
			        	});
		        		$('#codeGu').selectOptions('01', true);
		        		$('#codeGu').change();
			        }else{
			        	alert(data.msg);
			        }
			    }
			})
		}else{
			$('#codeGu').removeOption(/./);
			$('#codeGu').selectOptions('');
			$('#codeGu').change();
		}
	}
	
	// 업체 등록 처리
	function regForm(){
		if(confirm('등록하시겠습니까?')){
			
			$('#frmReg').ajaxSubmit({
				dataType: 'json',
				beforeSubmit: function(arr, $form, options){
				    var form = $form[0];
				    if (isEmptyFocus($('#companyNm'))) {
				        alert('업체명을 입력하세요.');
				        return false;
				    }
				    if (isEmptyFocus($('#companyType'))) {
				        alert('구분을 선택하세요.');
				        return false;
				    }
				    if (isEmptyFocus($('#mngNm'))) {
				        alert('담당자를 입력하세요.');
				        return false;
				    }
				    if (isEmptyFocus($('#phone'))) {
				        alert('연락처를 입력하세요.');
				        return false;
				    }
				    if (isEmptyFocus($('#codeSi'))) {
				        alert('지역(시)을 선택하세요.');
				        return false;
				    }
				    if (isEmptyFocus($('#codeGu'))) {
				        alert('지역상세(구/군)를 선택하세요.');
				        return false;
				    }
				    if (isEmptyFocus($('#companyAddr'))) {
				        alert('주소를 입력하세요.');
				        return false;
				    }
	
				    return true;
				},
				success: function(data) {
			        if(data.status == 'success'){
						location.href = '/company/companyList';
			        }else{
			        	alert(data.msg);
			        }
			    }
			});
		}	
	}
	
	// 업체 등록 취소 처리
	function cancelForm(){
		if(confirm('등록을 취소하시겠습니까?')){
			location.href = '/company/companyList';
		}else{
			return false;
		}
	}
</script>