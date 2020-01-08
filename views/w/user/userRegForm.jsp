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
				<span class="title">사용자 등록</span>
				<span class="path">
					<strong>사용자관리</strong>
					<i>&lt;</i> ${AUTH.userNm}<i>&lt;</i> ${AUTH.positionNm} 
					<img src="${STATIC_COMMON_URL}/images/icon_path.png">
				</span>
			</h2>
			
			<!-- s: group01 -->
			<div class="group01">
				<div class="title"><div class="fr pb10 "><button class="btn_con_search" onClick="location.href='/user/userList'">목록으로</button></div></div>
			
				<span class="font06 "> * 아이디와 비밀번호는 대소문자가 구분되오니 Caps Lock키가 눌렸는지 확인해주세요.</span>
				<!-- s: list_detail -->
				<div class="list_detail">
					<!-- s: form -->
					<form id="frmReg" name="frmReg" method ="post" action="/user/userRegFormAjax">
						<ul>
							<li>
								<dt>아이디 <font class="font05">*</font></dt>
								<dd>
									<input type="hidden" id="userId" name="userId" />
									<input type="text" id="userIdChk" style="width: 200px;" maxlength="15" />
									<button class="btn_table_blue" onClick="userIdCheck(); return false;">아이디 중복 확인</button>
									<span class="font06"> * 특수문자 제외 4~15자 </span>
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
									<input type="text" id="userNm" name="userNm" style="width: 200px;" maxlength="30" />
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
									<input type="text" id="phone" name="phone" style="width: 200px;" />
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
									<input type="text" id="prFile" name="prFile" style="width:350px;" readonly="readonly" onclick="showProFilePop();"/>
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

	// 아이디 중복 확인
	function userIdCheck() {
		if (isEmptyFocus($('#userIdChk'))) {
			alert("아이디를 입력하세요.");
			return;
		}

		if ($('#userIdChk').val().length < 4) {
			alert("아이디는 4자리 이상 입력해 주시기 바랍니다.");
			return;
		}

		if (!chkAlpNum($('#userIdChk').val())) {
			alert("아이디는 숫자, 영문만 사용할 수 있습니다.");
			return;
		}

		var userId = $('#userIdChk').val();
		$.ajax({
					url : '/user/userIdChkProcAjax.do',
					dataType : 'json',
					data : {'userId' : userId },
					type : 'post',
					success : function(data) {
						if (data.status == 'success') {
							if (data.resultData.count > 0) {
								$('#userIdChk').css('color', '#e75c7b');
								alert('이미 사용 중인 아이디입니다.\n다른 아이디를 입력해 주세요.');
							} else {
								$('#userIdChk').css('color', 'black');
								$('#userId').val(userId);
								alert('사용할 수 있는 아이디입니다.\n해당 아이디를 사용하시겠습니까?');
							}
						} else {
							alert(data.msg);
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("처리 중 오류 발생");
					}
				});
	}

	// 아이디 세팅
	function setUseId(userId) {
		$('#userRegForm #userId').val(userId);
		closeLayPop('#idCheckPop');
	}

	// 계정 등록 처리
	function regForm() {
		$('#frmReg').ajaxSubmit({
			dataType : 'json',
			beforeSubmit : function(arr, $form, options) {
				var form = $form[0];
				if (isEmptyFocus($('#userId'))) {
					alert("아이디 중복 확인을 해주세요.");
					return;
				}

				if ($('#userId').val() != $('#userIdChk').val()) {
					alert("아이디 중복 확인을 해주세요.");
					form.userId.focus();
					return false;
				}

				if (isEmptyFocus($('#pwd'))) {
					alert("비밀번호를 입력해주세요.");
					return;
				}

				if (!/^[a-zA-Z0-9]{8,15}$/.test($('#pwd').val())) {
					alert('비밀번호는 숫자와 영문자 조합으로 8~15자리를 사용해야 합니다.');
					form.pwd.focus();
					return false;
				}

				var chk_num = $('#pwd').val().search(/[0-9]/g);
				var chk_eng = $('#pwd').val().search(/[a-z]/ig);
				if (chk_num < 0 || chk_eng < 0) {
					alert('비밀번호는 숫자와 영문자를 혼용하여야 합니다.');
					form.pwd.focus();
					return false;
				}
				
				if (isEmptyFocus($('#pwdChk'))) {
					alert("비밀번호 확인을 입력해주세요.");
					return;
				}

				if ($('#pwd').val() != $('#pwdChk').val()) {
					alert("비밀번호와 비밀번호 확인이 다릅니다.");
					form.pwdChk.focus();
					return false;
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
					return;
				}
				
				if (isEmptyFocus($('#phone'))) {
					alert("연락처를 입력해주세요.");
					return;
				}
				
				if (isEmptyFocus($('#auth'))) {
					alert("사용자 구분을 선택해주세요.");
					return;
				}

				return true;
			},
			success : function(data) {
				if (data.status == 'success') {
					location.href = '/user/userList';
				} else {
					alert(data.msg);
				}
			}
		});
	}

	// 등록 취소 처리
	function cancelForm() {
		if (confirm('등록을 취소하시겠습니까?')) {
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