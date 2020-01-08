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
				<span class="title">사용자 관리</span>
			</h2>
			<!-- s: group01 -->
			<div class="group01">
				<div class="title"></div>
				<!-- s: search_box -->
				<div class="search_box">
					<!-- s: form -->
					<form id="frmSearch" name="frmSearch" method ="get" action="/user/userList">
						<ul>
							<li>
								<dt>이름</dt>
								<dd>
									<input type="text" name="userNm" style="width: 200px;" maxlength="20" value="${param.userNm}"/>
								</dd>
							</li>
						</ul>
					</form>
					<!-- e: form -->
					<div class="fr pt10 pb10">
						<button class="btn_con_search" onclick="searchForm(); return false;">검색</button>
					</div>
				</div>
				<!-- e: search_box -->
				<!-- s: main_tbl -->
				<div class="main_tbl">
					<!-- s: table -->
					<table class="tbl01">
						<colgroup>
							<col width="5%">
							<col width="10%">
							<col width="20%">
							<col width="10%">
							<col width="20%">
							<col width="20%">
							<col width="10%">
						</colgroup>
						<thead>
							<tr>
								<th>선택</th>
								<th>이름</th>
								<th>아이디</th>
								<th>직급</th>
								<th>연락처</th>
								<th>프로필</th>
								<th>사용자구분</th>
						</thead>
						<tbody>
							<c:forEach var="item" items="${userList}" varStatus="status">
								<tr>
									<td><input type="checkbox" id="${item.userId }" name="userIds" value="${item.userId }" /><label for="${item.userId }"><span></span></label></td>
									<td><a href="#" onclick="editForm('${item.userId }'); return false">${item.userNm }</a></td>
									<td>${item.userId }</td>
									<td>
										<c:if test="${item.position eq 01 }">사장</c:if>
							        	<c:if test="${item.position eq 02 }">전무</c:if>
							        	<c:if test="${item.position eq 03 }">상무</c:if>
							        	<c:if test="${item.position eq 04 }">이사</c:if>
							        	<c:if test="${item.position eq 05 }">부장</c:if>
							        	<c:if test="${item.position eq 06 }">차장</c:if>
							        	<c:if test="${item.position eq 07 }">과장</c:if>
							        	<c:if test="${item.position eq 08 }">대리</c:if>
							        	<c:if test="${item.position eq 09 }">사원</c:if>
							        </td>
									<td>${item.phone }</td>
									<td>
										<c:if test="${not empty item.prFile}">
											<img src="/resources/images/icon_id3.png">
							        	</c:if>
							        	<c:if test="${empty item.prFile}">
											<a href=""><img src=""></a>
							        	</c:if>
									</td>
									<td>
										<c:if test="${item.auth eq 01 }">일반사용자</c:if>
							        	<c:if test="${item.auth eq 02 }">관리자</c:if>
							        	<c:if test="${item.auth eq 03 }">점검자</c:if>
							        	<c:if test="${item.auth eq 04 }">방화관리자</c:if>
							        </td>
								</tr>
							</c:forEach>
							<c:if test="${fn:length(userList) == 0}">
								<tr>
									<td colspan="7" style="text-align: center" >목록이 없습니다.</td>
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
					<div class="btn_box_con">
						<button class="btn_admin_red" onClick="location.href='/user/userRegForm'">계정등록</button>
						<button class="btn_admin_gray" onClick="delForm();">계정삭제</button>
					</div>
					<!-- e: btn_box_con -->
					
					<!-- s: download_excel -->
					<ul class="download_excel">
						<li>
							<button class="btn_admin_excel" style="width:200px" onclick="excelDown();"><img src="${STATIC_COMMON_URL}/images/icon_excel.png"> 엑셀 다운로드</button>&nbsp;&nbsp;&nbsp;
							<span class="font18">사용자 목록 다운로드</span>
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
	$("input").keydown(function(key) {
		if (key.keyCode == 13) {
			searchForm();
		}
	});
	
	// 검색	
	function searchForm(){
		$('#frmSearch').submit();
	}
	
	// 파라미터 처리 및 조회
	function getParam(pageNo){
		var param = {
			userNm : '${param.userNm}',
			pageNo : pageNo || '${param.pageNo}'
		};

		return $.param(param);
	}
	
	// 페이징
	function paging(pageNo){
		location.href = '/user/userList?' + getParam(pageNo);
	}
	
	// 엑셀다운로드
	function excelDown(){
		$('#frmSearch').attr('action', '/user/userListExcelDownload').submit();
	}
	
	// 사용자 이름 클릭 - 계정 수정 처리
	function editForm(userId){
		
		var param = {
			userId: userId,
			queryString: getParam()
		};

		location.href = '/user/userEditForm?' + $.param(param);
	}
	
	// 계정 삭제 처리
	function delForm(){
		if(confirm('삭제하시겠습니까?')){
			var $userIds = $('input:checkbox:checked');
			
			if($userIds.length == 0){
				alert('삭제할 계정을 선택해주세요.');
				return;
			}
			
			$.ajax({
				url: '/user/userDelFormAjax',
				dataType: 'json',	
				data: $userIds.serialize(),
				type: 'POST',
				async : false,
				success: function(data) {
					if(data.status == 'success'){
						alert('삭제되었습니다.');
						location.href = '/user/userList';
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