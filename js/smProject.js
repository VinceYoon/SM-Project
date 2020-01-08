/**
 * SELECT BOX 활성화
 * @returns
 */
function initSelbox() {
	var selectTarget = $('.selectbox select');

	selectTarget.on('blur', function(){
	  $(this).parent().removeClass('focus');
	});

	selectTarget.change(function(){
	  var select_name = $(this).children('option:selected').text();

	  $(this).siblings('label').text(select_name);
	});

	$('select').each(function(){
		var $this = $(this);
		var select_name = $this.children('option:selected').text();

		$this.siblings('label').text(select_name);
	});
}

//datepicker 달력 스타일 set
function setDatepickerImgStyle(mleft){
	$("img.ui-datepicker-trigger").css({
		marginLeft: mleft || '2px',
		verticalAlign: 'middle',
		cursor: 'Pointer'
	});
}