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
				<span class="title">사용자 수정</span>
				<span class="path">
					<strong>사용자관리</strong>
					<i>&lt;</i> ${AUTH.userNm}<i>&lt;</i> ${AUTH.positionNm} 
					<img src="${STATIC_COMMON_URL}/images/icon_path.png">
				</span>
			</h2>
			
			<!-- s: group01 -->
			<div class="group01">
				<div class="title"><div class="fr pb10 "><button class="btn_con_search" onClick="location.href='/user/userList?${param.queryString}'">목록으로</button></div></div>
			
				<span class="font06 "> * 아이디와 비밀번호는 대소문자가 구분되오니 Caps Lock키가 눌렸는지 확인해주세요.</span>
				<!-- s: list_detail -->
				<div class="list_detail">
					<!-- s: form -->
					<form id="frmEdit" name="frmEdit" method ="post" action="/user/userEditFormAjax">
						<ul>
							<li>
								<dt>아이디 </dt>
								<dd>
									<input type="hidden" id="userId" name="userId" value="${userId }" />
									${userId }
								</dd>
							</li>
							<li>
								<dt>비밀번호 <span class="font05">*</span></dt>
								<dd>
									<input type="password" id="pwd" name="pwd" style="width: 200px;" maxlength="15" />
									<span class="font06"> * 8~15자 영문 숫자 혼용 기입 </span>
								</dd>
							</li>
							<li>
								<dt>비밀번호 확인 <span class="font05">*</span></dt>
								<dd>
									<input type="password" id="pwdChk" style="width: 200px;" maxlength="20" />
								</dd>
							</li>
						</ul>
						<ul class="list_detail_more">
							<li>
								<dt>성함 <span class="font05">*</span></dt>
								<dd>
									<input type="text" id="userNm" name="userNm" style="width: 200px;" maxlength="30" value="${userNm }" />
								</dd>
							</li>
							<li>
								<dt>직급 <span class="font05">*</span></dt>
								<dd>
									<div class="selectbox" style="width: 200px;">
										<label for="position">선택</label>
										<select id="position" name="position">
											<option value="">선택</option>
											<c:forEach var="item" items="${positionList }" varStatus="status">
												<option value="${item.code }">${item.codeNm }</option> 
											</c:forEach>
										</select>
									</div>
								</dd>
							</li>
							<li>
								<dt>연락처 <span class="font05">*</span></dt>
								<dd>
									<input type="text" id="phone" name="phone" style="width: 200px;" value="${phone }" />
									<font class="font06">* (예시) 010-0000-0000</font>
								</dd>
							</li>
							<li>
								<dt>사용자 구분 <font class="font05">*</font></dt>
								<dd>
									<div class="selectbox" style="width: 200px;">
										<label for="auth">선택</label>
										<select id="auth" name="auth">
											<option value="">선택</option>
											<c:forEach var="item" items="${authList }" varStatus="status">
												<option value="${item.code }">${item.codeNm }</option> 
											</c:forEach>
										</select>
									</div>
								</dd>
							</li>
							<li>
								<dt>프로필 파일 설정</dt>
								<dd>
									<input type="text" id="prFile" name="prFile" style="width: 400px;" value="${prFile }" readonly="readonly" onclick="showProFilePop();"/>
								</dd>
								<div class="module_partner_ly" style="overflow-y: scroll;height:400px;width:400px;display: block;left:500px;top:10px;margin-bottom: 500px;display:none">
									<div class=" fr pb10" style="text-align:rigth; width:100%;">
										<button class="btn_table_blue" onclick="hideProFilePop(); return false" >닫기</button> 
									</div>									
									<ul>
										<c:forEach var="item" items="${fileList }" >
										<li>
											<div class="btn_module_checkbox">
												<label for="z1">${item.name}</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<img alt="" src="${path}/${item.name}" width="50" height="50">&nbsp;&nbsp;&nbsp;
												<input type="button" value="적용" onclick="setFile('${item.name}');">
											</div>
										</li>
										</c:forEach>
									</ul>
								</div>
							</li>
							<li>
								<dt></dt>
								<dd id="ddImg">
									<c:if test="${prFile != ''}">
										<img src="${path}/${prFile}" width="100" height="100">
									</c:if>
								</dd>
							</li>
						</ul>
					</form>	
					<!-- e: form -->
				</div>
				<!-- e: list_detail -->
			
				<div class="btn_box_con">
					<button type="button" class="btn_admin_red" onClick="editForm(); return false;">수정</button> 
					<button type="button" class="btn_admin_gray" onClick="cancelForm(); return false;">취소</button>
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
	
	$(document).ready(function(){
		// 직급
		$('#position').val('${position }').change();
		
		// 사용자 구분
		$('#auth').val('${auth }').change();
	});

	// 계정 수정 처리
	function editForm() {
		$('#frmEdit').ajaxSubmit({
			dataType : 'json',
			beforeSubmit : function(arr, $form, options) {
				var form = $form[0];

				
				if(!isEmpty($('#pwd').val())){
					
					if(isEmptyFocus($('#pwdChk'))){
						alert("비밀번호를 확인을 입력해주세요.");
						return false;
					}
					
					
					if(!/^[a-zA-Z0-9]{8,15}$/.test($('#pwd').val())){ 
						alert('비밀번호는 숫자와 영문자 조합으로 8~15자리를 사용해야 합니다.'); 
				        return false;
				    }
					

					var chk_num = $('#pwd').val().search(/[0-9]/g); 
				    var chk_eng = $('#pwd').val().search(/[a-z]/ig);

				    if(chk_num < 0 || chk_eng < 0){ 
				        alert('비밀번호는 숫자와 영문자를 혼용하여야 합니다.'); 
				        return false;
				    }
					
					
					if($('#pwd').val() != $('#pwdChk').val()){
						alert("비밀번호와 비밀번호 확인이 다릅니다.");
						return false;
					}
					
					if($('#pwd').val().length < 6) {
						alert("비밀번호는 6자리 이상 입력해 주시기 바랍니다.");
						return false;
					}
				}
				
				if (isEmptyFocus($('#userNm'))) {
					alert("성함을 입력해주세요.");
					return;
				}

				if ($('#userNm').val().length < 2) {
					alert("성함은 2자 이상 입력해 주시기 바랍니다.");
					form.userNm.focus();
					return false;
				}
				
				if (isEmptyFocus($('#position'))) {
					alert("직급을 선택해주세요.");
					return false;
				}
				
				if (isEmptyFocus($('#phone'))) {
					alert("연락처를 입력해주세요.");
					return false;
				}
				
				if (isEmptyFocus($('#auth'))) {
					alert("사용자 구분을 선택해주세요.");
					return false;
				}

				return true;
			},
			success : function(data) {
				if (data.status == 'success') {
					alert("수정되었습니다.")
					location.reload();
				} else {
					alert(data.msg);
				}
			}
		});
	}

	// 계정 수정 취소 처리
	function cancelForm() {
		if (confirm('수정을 취소하시겠습니까?')) {
			location.href = '/user/userList?${param.queryString}';
		} else {
			return false;
		}
	}
	
	// 파일 세팅
	function setFile(fileName){
		$('#prFile').val(fileName);
		var img = "${path}/" +  fileName;
		$('#ddImg').html('<img src="' + img + '" width="100" height="100">');
		hideProFilePop();
	}
	
	// 프로필 파일 오픈
	function showProFilePop(){
		$('.module_partner_ly').show();
	}
	
	// 프로필 파일 닫기
	function hideProFilePop(){
		$('.module_partner_ly').hide();
	}
</script>