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
				<span class="title">일정 관리</span>
			</h2>
			<!-- s: group01 -->
			<div class="group01">
				<div class="title"></div>
				
				<!-- s: calendar -->
				<div id='calendar'></div>
				<!-- e: calendar -->
				
				<form id="frmSearch" name="frmSearch" method ="get" action="">
					<!-- s: download_excel -->
					<ul class="download_excel" style="margin-top: 30px;">
						<li>
							<button class="btn_admin_excel" style="width: 200px;float: left;margin-top: -10px;" onclick="excelDown();"><img src="${STATIC_COMMON_URL}/images/icon_excel.png"> 엑셀 다운로드</button>&nbsp;&nbsp;&nbsp;
							<div class="selectbox" style="width: 100px;margin-left: 30px;">
								<label for="year">2019</label>
								<select id="year" name="year">
									<c:forEach var="item" begin="2019" end="${currYear }">
										<option value="${item }">${item }</option>
									</c:forEach>
								</select>
							</div>
							<div class="selectbox" style="width: 100px;margin-left: 10px;">
								<label for="month">전체</label>
								<select id="month" name="month">
									<option value="">전체</option>
									<option value="01">1월</option>
									<option value="02">2월</option>
									<option value="03">3월</option>
									<option value="04">4월</option>
									<option value="05">5월</option>
									<option value="06">6월</option>
									<option value="07">7월</option>
									<option value="08">8월</option>
									<option value="09">9월</option>
									<option value="10">10월</option>
									<option value="11">11월</option>
									<option value="12">12월</option>
								</select>
							</div>
							<span class="font18">일정 관리 목록 다운로드</span>
						</li>
					</ul>
					<c:if test="${AUTH.auth eq '02'}">
						<!-- 
						<ul class="download_excel">
							<li>
								<button class="btn_admin_excel" style="width: 200px;float: left;margin-top: -10px;" onclick="excelDown2();"><img src="${STATIC_COMMON_URL}/images/icon_excel.png"> 엑셀 다운로드</button>&nbsp;&nbsp;&nbsp;
								<div class="selectbox" style="width: 100px;margin-left: 30px;">
									<label for="vYear">2019</label>
									<select id="vYear" name="vYear">
										<c:forEach var="item" begin="2019" end="${currYear }">
											<option value="${item }">${item }</option>
										</c:forEach>
									</select>
								</div>
								<div class="selectbox" style="width: 100px;margin-left: 10px;">
									<label for="vMonth">전체</label>
									<select id="vMonth" name="vMonth">
										<option value="">전체</option>
										<option value="01">1월</option>
										<option value="02">2월</option>
										<option value="03">3월</option>
										<option value="04">4월</option>
										<option value="05">5월</option>
										<option value="06">6월</option>
										<option value="07">7월</option>
										<option value="08">8월</option>
										<option value="09">9월</option>
										<option value="10">10월</option>
										<option value="11">11월</option>
										<option value="12">12월</option>
									</select>
								</div>
								<span class="font18">휴가 일정 관리 목록 다운로드</span>
							</li>
						</ul>
						 -->
					</c:if>
					<!-- e: download_excel -->
				</form>

			</div>
			<!-- e: group01 -->
		</section>
		<!-- e: type01 -->
	</div>
	<!-- e: wrap_in01 -->
</div>
<!-- e: wrap -->

<script>
	$(document).ready(function() {
		
		initSelbox();

		var leftText = '';
		if('${AUTH.auth}' == '02'){
			leftText = 'prev,next today schMngRegBtn';
		}else{
			leftText = 'prev,next today';
		}
		$('#calendar').fullCalendar({
				height : 'auto',
				customButtons: {
					schMngRegBtn: {
						text: '일정 추가',
						click: function() {
							location.href='/schMng/schMngRegForm';
						}
					}
				},
				header : {
					left : leftText,
					center : 'title',
					right : 'month,agendaWeek,agendaDay'
				},
				defaultDate : new Date(),
				navLinks : true, // can click day/week names to navigate views
				editable : true,
				googleCalendarApiKey : "AIzaSyDcnW6WejpTOCffshGDDb4neIrXVUA1EAE", // Google API KEY
				eventSources : [
				    // 대한민국 공휴일
				    {
						googleCalendarId : "ko.south_korea#holiday@group.v.calendar.google.com",
						className : "koHolidays",
						color : "#FF0000",
						textColor : "#FFFFFF"
				    }
				],
				eventLimit : true, // allow "more" link when too many events
				events : function(start, end, timezone, callback){
					$.ajax({
						url: '/schMng/schMngFormAjax',
						dataType: 'json',	
						data: {},
						type: 'POST',
						async : false,
						success: function(data) {
							if(data.status == 'success'){
								var events = [];
								$.each(data.resultData, function(index, item){
									var scheduleGubunNm = '';
									if(item.scheduleGubun == '01'){
										scheduleGubunNm = '공사';
									}else if(item.scheduleGubun == '02'){
										scheduleGubunNm = '점검';
									}else if(item.scheduleGubun == '03'){
										scheduleGubunNm = '휴가';
									}else if(item.scheduleGubun == '04'){
										scheduleGubunNm = '방화관리';
									}
									var colorCd = '';
									if(item.colorCd == '' || item.colorCd == null){
										colorCd = '#039be5';
									}else{
										colorCd = '#' + item.colorCd;
									}
									var startDt = item.startDt;
									startDt = item.startDt.substring(0,4) + '-' + item.startDt.substring(4,6) + '-' + item.startDt.substring(6,8);
									var endDt = item.endDt;
									endDt = item.endDt.substring(0,4) + '-' + item.endDt.substring(4,6) + '-' + item.endDt.substring(6,8);
									var startTime = '';
									if(item.startTime == '' || item.startTime == null){
										startTime = '';
									}else{
										startTime = 'T' + item.startTime.substring(0,2) + ':' + item.startTime.substring(2,4) + ':00';
									}
									var endTime = '';
									if(item.endTime == '' || item.endTime == null){
										endTime = '';
									}else{
										endTime = 'T' +  item.endTime.substring(0,2) + ':' + item.endTime.substring(2,4) + ':00';
									}
									var schMngUrl = '';
									if('${AUTH.auth}' == '02'){
										schMngUrl = '/schMng/schMngEditForm?scheduleMngSeq=';
									}else{
										schMngUrl = '/schMng/schMngView?scheduleMngSeq=';
									}
									events.push({
										id: item.scheduleMngSeq,
										title: '[' + scheduleGubunNm + '] ' + item.companyNm,
										color: colorCd,
										textColor: '#FFFFFF',
										url: schMngUrl + item.scheduleMngSeq,
										start: startDt + startTime,
										end: endDt + endTime
									});
					        	});
								callback(events);
					        }else{
					        	alert(data.msg);
					        }
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							alert("처리 중 오류 발생");
						}
					});
				}
			});
	});
	
	// 엑셀다운로드
	function excelDown(){
		$('#frmSearch').attr('action', '/schMng/schMngListExcelDownload').submit();
	}
	
	// 엑셀다운로드 (휴가자)
	function excelDown2(){
		$('#frmSearch').attr('action', '/schMng/schMngListExcelDownload2').submit();
	}
</script>
<style>
	#calendar {
		max-width: 100%;
		margin: 0 auto;
	}
  
	.fc-day-top.fc-sat.fc-past { color:#0000FF; } /* 토요일 */
	.fc-day-top.fc-sun.fc-past { color:#FF0000; } /* 일요일 */
</style>

