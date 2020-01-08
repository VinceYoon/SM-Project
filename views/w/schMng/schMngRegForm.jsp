<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="strUtil" uri="/WEB-INF/tld/strUtil.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="${STATIC_COMMON_URL}/js/jquery.auto-complete.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${STATIC_COMMON_URL}/js/jquery.auto-complete.js"></script>

<!-- s: wrap -->
<div class="wrap">
	<!-- s: wrap_in01 -->
	<div class="wrap_in01">
		<!-- s: type01 -->
		<section class="type01">
			<h2>
				<span class="title">일정 등록</span>
				<span class="path">
					<strong>일정관리</strong>
					<i>&lt;</i> ${AUTH.userNm}<i>&lt;</i> ${AUTH.positionNm} 
					<img src="${STATIC_COMMON_URL}/images/icon_path.png">
				</span>
			</h2>
			
			<!-- s: group01 -->
			<div class="group01">
				<div class="title"><div class="fr pb10 "><button class="btn_con_search" onClick="location.href='/schMng/schMngList'">목록으로</button></div></div>
			
				<!-- s: list_detail -->
				<div class="list_detail">
					<!-- s: form -->
					<form id="frmReg" name="frmReg" method ="post" action="/schMng/schMngRegFormAjax">
						<ul>
							<li>
								<dt>일정 구분 <font class="font05">*</font></dt>
								<dd>
									<c:forEach var="item" items="${schGubunList }" varStatus="status">
										<div style="float: left; margin-right: 10px;">
											<input type="radio" id="${item.code}"  name="scheduleGubun" value="${item.code}"> <label for="${item.code}"><span></span>${item.codeNm}</label>
										</div>
									</c:forEach>
									<div class="selectbox" style="width:55px; float:left; margin:-2px 0px 0px 18px;"> 
										<label for="color" id="color" style="font-size: 20px; line-height: 14px;">●</label> 
										<select id="colorCd" name="colorCd"> 
											<c:forEach var="item" items="${colorList }" varStatus="status">
												<option value="${item.code }" style="font-size: 20px; color:#${item.code }">●</option> 
											</c:forEach>
										</select>
									</div>
								</dd>
							</li>
							<li class="company">
								<dt>업체 명 <span class="font05">*</span></dt>
								<dd class="pr20">
									<input type="text" id="companyNm" name="companyNm"style="width:220px;" placeholder="업체명을 입력해주세요."/>
									<span style="color: #999;" id="companyAddr"></span>
									<input type="hidden" id="companySeq" name="companySeq"/>
									
									<div class="select_partner_box" id="companyListDiv">
									</div>
								</dd>
							</li>
							<li>
								<dt class="mng">담당자  <span class="font05">*</span></dt>
								<dd class="pr20">
									<input type="text" id="mngNm" name="mngNm" style="width:220px;" placeholder="이름을 입력해주세요."/>
									<input type="hidden"id="mngId" name="mngId"/>
									
									<div class="select_partner_box" id="mngListDiv">
									</div>
								</dd>
							</li>
						
							<li>
								<dt>일정  <span class="font05">*</span></dt>
								<dd>
									<input type="text" id="startDt" name="startDt" class="datepicker" style="width:110px;" readonly="readonly"  /> 
									<input type="text" id="startTime" name="startTime" style="width:80px; text-align: center; vertical-align: middle;" maxlength="5" value="09:00">
									 &nbsp;~&nbsp;
									<input type="text" id="endDt" name="endDt" class="datepicker" style="width:110px;" readonly="readonly"  />
									<input type="text" id="endTime" name="endTime" style="width:80px; text-align: center; vertical-align: middle;" maxlength="5" value="18:00">
								</dd>
							</li>
							<li class="partner">
								<dt>동행자 </dt>
								<dd>
									<a href="#" onclick="openPartnerLayerPop(); return false;"><input type="text" disabled value="동행자 선택" style="width:358px;cursor:pointer"/></a>
									<div class="module_partner_ly" style="display:none;">
										<div class="btn_box_close"><a href="#" onclick="closePartnerLayerPop();"><img src="${STATIC_COMMON_URL}/images/btn_pop_close.png"/></a></div>
										<div class=" fr pb10" style="text-align:center; width:100%;">
											<button type="button" class="btn_table_blue" onclick="addUser(true);"><img src="${STATIC_COMMON_URL}/images/icon_add_blue.png"> 전체 담당자 추가</button>
											<button type="button" class="btn_table_nomal" onclick="addUser(false);"><img src="${STATIC_COMMON_URL}/images/icon_add_blue.png"> 선택된 담당자 추가</button>
										</div>
										<ul id="modulePartnerUl">
										</ul>
									</div>	
								</dd>
							</li>
							<!-- 선택된 동행자가 있을경우 출력 -->
							<li id="selectPartnerDetailLi" style="display:none;">
								<dt>선택된 동행자</dt>
								<dd class="pr20 select_partner_detail" id="selectPartnerDetailDd">
								</dd>
							</li>
							<li>
								<dt>특이사항</dt>
								<dd>
									<textarea rows="5" cols="62" name="scheduleMemo"></textarea>
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
		// 페이지 로딩
		pageInit();
		// 달력 초기화
		initDatepicker();
		// 컬러세팅 초기화
		$('#color').css('color','#${colorList[0].code}');
		// 업체 조회
		var xhr;
		$('#companyNm').autoComplete({
			minChars: 2,
			cache : false,
			delay : 10,
			source: function(term, response){
				try {
					xhr.abort();
					$('#companyListDiv').html('');
					$('#companySeq').val('');
					$("#companyAddr").html('');
				} catch(e){
					console.log('autocomplete xhr abort exception');
					console.log(e);
				}

				xhr = $.ajax({
					type: 'GET',
					url: '/company/companyListAjax',
					dataType: 'json',
					data: {
						companyNm: term
					},
					global: false,
					async: false,
					success: function (data) {
						if(!data.resultData){
							return;
						}

						var list = data.resultData;
						var completes = [];
						for(var i = 0, len = list.length; i < len; i++){
// 							var name = list[i].companyNm + " / " + list[i].codeSiNm + " / " + list[i].codeGuNm;
							var name = list[i].companyNm;
							completes.push([name, list[i].companySeq, list[i].companyAddr]);
						}
						response(completes);
					}
				});
			},
			renderItem: function (item, search){
				return '<div class="autocomplete-suggestion" data-seq="' + item[1] + '" data-nm="' + item[0] + '" data-addr="' + item[2] + '" style="font-size: 0.8em">' + item[0] + ' / ' + item[2] + '</div>';
			},
			onSelect: function(e, term, item){
				e.preventDefault();

				$("#companySeq").val(item.data('seq'));
				$("#companyNm").val(item.data('nm'));
				$("#companyAddr").html(item.data('addr'));
			},
			focus: function(event, ui) {
		         return false;
			}
		});
		
		//담당자 조회
		$('#mngNm').autoComplete({
			minChars: 2,
			cache : false,
			delay : 10,
			source: function(term, response){
				try {
					xhr.abort();
					$('#mngListDiv').html('');
					$('#mngId').val('');
				} catch(e){
					console.log('autocomplete xhr abort exception');
					console.log(e);
				}

				xhr = $.ajax({
					type: 'GET',
					url: '/user/userListAjax',
					dataType: 'json',
					data: {
						userNm: term
					},
					global: false,
					async: false,
					success: function (data) {
						if(!data.resultData){
							return;
						}

						var list = data.resultData;
						var completes = [];
						for(var i = 0, len = list.length; i < len; i++){
							var name = list[i].userNm + "  " + list[i].positionNm;
							completes.push([name, list[i].userId]);
						}
						response(completes);
					}
				});
			},
			renderItem: function (item, search){
			    return '<div class="autocomplete-suggestion" data-code="' + item[1] + '" data-nm="' + item[0] + '" style="font-size: 0.8em">' + item[0] + '</div>';
			},
			onSelect: function(e, term, item){
				e.preventDefault();

				$("#mngId").val(item.data('code'));
				$("#mngNm").val(item.data('nm'));
			},
			focus: function(event, ui) {
		         return false;
			}
		});
	});

	// 일정 구분 변경 이벤트
	$("input[name=scheduleGubun]").change(function() {
		// 휴가 선택시
		if($(this).val() == '03'){
			$('.mng').text('휴가자  ');
			$('.mng').append('<span class="font05">*</span>');
			$('.company').hide();
			$('.partner').hide();
			$('#startTime').hide();
			$('#endTime').hide();
			
		}// 공사 점검 선택시
		else{
			$('.mng').text('담당자  ');
			$('.mng').append('<span class="font05">*</span>');
			$('.company').show();
			$('.partner').show();
			$('#startTime').show();
			$('#endTime').show();
		}
	});
	
	// 색상 변경 이벤트
	$('#colorCd').change(function(){
		var colorCd = '#' + $('#colorCd').find('option:selected').val();
		$('#color').css('color', colorCd);
	});
	
	//시간 숫자만 입력 처리
	$("input[name$='Time']").on("keyup", function() {
	    $(this).val(addColon($(this).val().replace(/[^0-9]/g,"")));
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
	       	buttonImageOnly:true,
	       	onSelect: function(dateText, inst){
	    		dateText = dateText.replaceAll('-', '');
				var startDate = $('#startDt').val();

				if((startDate.replaceAll('-', '')) > dateText){
					alert('일정종료일은 ' + startDate + ' 보다 이전일을 선택할 수 없습니다.');
					$(this).val('');
				}
	    	}
		});

		setDatepickerImgStyle();
	}
	
	// 시간 입력 자동 세팅
	function addColon(time){
		if(time.toString().length < 4){
			return time.toString().replace(/(\d{2})(\d{1})/, '$1:$2');			
		}else{
			return time.toString().replace(/(\d{2})(\d{2})/, '$1:$2');
		}
	}
	
	// 동행자 조회 레이어팝업 open
	function openPartnerLayerPop(){
		setUserList();
	}
	
	// 동행자 조회 레이어팝업 close
	function closePartnerLayerPop(){
		$('.module_partner_ly').hide();
	}
	
	// 사용자 정보 조회
	function setUserList(){
		var $ul = $('#modulePartnerUl');

		$ul.html('');
		var userId = $('#mngId').val();
		if(isEmpty(userId)){
			closePartnerLayerPop();
			alert('담당자를 먼저 입력해주세요.');
			return;
		}
		
		$.ajax({
			url : '/user/userListAjax.do',
			dataType : 'json',
			data : {userId : userId },
			type : 'GET',
			success : function(data) {
				if (data.status == 'success') {
					var resultData = data.resultData;
					var html = [];
					var	staffVO;
					var cusStaffId;
					for (var i = 0, len = resultData.length; i < len; i++) {
						staffVO = resultData[i];
						staffId = staffVO.userId;

						html.push('<li>');
						html.push('	<div class="btn_module_checkbox">');
						html.push('		<input type="checkbox" id="staff_' + staffId + '" value="' + staffId + '">');
						html.push('		<label for="staff_' + staffId + '">');
						html.push('		<span></span>');
						html.push(		staffVO.userNm + "&nbsp;" + staffVO.positionNm);
						html.push('		</label>');
						html.push('	</div>');
						html.push('</li>');
					}

					$ul.html(html.join(''));
					
					$('.module_partner_ly').show();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("처리 중 오류 발생");
			}
		});
	}
	
	//동행자 추가
	function addUser(isAll) {
		if(isAll){
			$('[id^="staff_"]').prop('checked', true);
		}

		var $selectStaff = $('[id^="staff_"]:checked');
		if($selectStaff.length == 0){
			alert('추가할 동행자를 선택해주세요.');
			return;
		}

		var $staff;
		var staffId;
		var html = [];
		for (var i = 0, len = $selectStaff.length; i < len; i++) {
			$staff = $selectStaff.eq(i);
			staffId = $staff.val();

			if($('#staffButton_' + staffId).length > 0){
				continue;
			}

			html.push('<button type="button" style="margin-right:5px;" class="btn_table_blue" id="staffButton_' + staffId + '" >');
			html.push('<input type="hidden" name="staffId" value="' + staffId + '">');
			html.push($staff.parent('div').text());
			html.push(' <img src="${STATIC_COMMON_URL}/images/icon_del_blue.png" onclick="delUser(\'' + staffId + '\')">');
			html.push('</button>');
		}

		$('#selectPartnerDetailDd').append(html.join(''));

		$('#selectPartnerDetailLi').show();

		closePartnerLayerPop();
	}
	
	//동행자 삭제
	function delUser(staffId) {
		$('#staffButton_' + staffId).remove();

		if($('[id^="staffButton_"]').length == 0){
			$('#selectPartnerDetailLi').hide();
		}
	}
	
	// 일정 등록 처리
	function regForm() {
		$('#frmReg').ajaxSubmit({
			dataType : 'json',
			beforeSubmit : function(arr, $form, options) {
				var form = $form[0];
				
				var $schGubun = $('[name="scheduleGubun"]:checked');
				if($schGubun.length == 0){
					alert("일정 구분을 선택해주세요.");
					return false;
				}
				
				if($schGubun.val() != '03'){
					
					if (isEmptyFocus($('#companySeq'))) {
	 					alert("업체를 입력해주세요.");
	 					return false;
	 				}
					
					if (isEmptyFocus($('#mngId'))) {
	 					alert("동행자를 입력해주세요.");
	 					return false;
	 				}
					if (isEmptyFocus($('#startDt'))) {
	 					alert("일정 시작일을 선택해주세요.");
	 					return false;
	 				}
					if (isEmptyFocus($('#endDt'))) {
	 					alert("일정 종료일을 선택해주세요.");
	 					return false;
	 				}

					if (isEmptyFocus($('#endTime'))) {
	 					alert("일정 죵료시간을 입력해주세요.");
	 					return false;
	 				}	
				}
				
				return true;
			},
			success : function(data) {
				if (data.status == 'success') {
					location.href = '/schMng/schMngList';
				} else {
					alert(data.msg);
				}
			}
		});
	}

	// 등록 취소 처리
	function cancelForm() {
		if (confirm('등록을 취소하시겠습니까?')) {
			location.href = '/schMng/schMngList';
		} else {
			return false;
		}
	}
	
</script>