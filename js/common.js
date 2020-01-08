/**
 * replaceAll
 * @return : String
 */
String.prototype.replaceAll = function(str, rps) {
	return this.split(str).join(rps);
};

/**
 * 숫자로 구성되어 있는지 학인 arguments[0] : 허용할 문자셋
 *
 * @return : boolean
 */
String.prototype.isNum = function() {
	return (/^[0-9]+$/).test(this) ? true : false;
};

/**
 * 영어만 허용 - arguments[0] : 추가 허용할 문자들
 *
 * @return : boolean
 */
String.prototype.isEng = function() {
	return (/^[a-zA-Z]+$/).test(this.remove(arguments[0])) ? true : false;
};

/**
 * 영어와&nbsp; 만 허용 - arguments[0] : 추가 허용할 문자들
 *
 * @return : boolean
 */
String.prototype.isblankEng = function() {
	return (/^[a-zA-Z\s]+$/).test(this.remove(arguments[0])) ? true : false;
};

/**
 * 숫자와 영어만 허용 - arguments[0] : 추가 허용할 문자들
 *
 * @return : boolean
 */
String.prototype.isEngNum = function() {
	return (/^[0-9a-zA-Z]+$/).test(this.remove(arguments[0])) ? true : false;
};


String.prototype.num = function() {
	return (this.trim().replace(/[^0-9]/g, ""));
};

/**
 * null 체크
 */
var isEmpty = function(value){
	if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
		return true
	}else{
		return false
	}
};


/**
 * not null 체크
 */
var isNotEmpty = function(value){
	return !isEmpty(value)
};


/**
 * not null 체크
 */
var isCheck = function(obj){

	for(var i = 0; i < obj.length; i++){
		if(obj[i].checked == true){
			return true;
		}
	}
	return false;
};

/**
 * null 체크 후 객체 focus
 */
var isEmptyFocus = function(obj){
	if(isEmpty($(obj).val())){
		$(obj).focus();
		return true;
	}else{
		return false;
	}
};

/**
 * 숫자, 영문만 포함하는지 확인
 */
var chkAlpNum = function(str) {
	var ObjReg = /[^a-zA-Z0-9\- ]/;
	return !ObjReg.test(str);
};

/**
 * 두 날짜 차이를 일 수로 계산
 */
var diffDay = function(startDay, endDay){
	var btMs = endDay.getTime() - startDay.getTime(),
		btDay = btMs / (1000 * 60 * 60 *24);

	return btDay;
};

/**
 * 문자열을 날짜형으로 변경
 */
var cvtDate = function(strDate, delimiter){
	if(delimiter){
		strDate = strDate.replaceAll(delimiter, '');
	}

	var year = strDate.substring(0, 4),
		month = strDate.substring(4, 6),
		day = strDate.substring(6, 8);

	return new Date(year, (month * 1 - 1), day);
};

/**
 * 0이하 숫자 앞자리에 0붙게한다.
 */
function n2c(num) {
	//return (parseInt(num) < 10 || num.length < 2) ? "0" + num : "" + num;
	return nc(num, 2);
}

/**
 * 0이하 숫자 앞자리에 0붙게한다.
 */
function nc(num, cipher) {
	var standard = Math.pow(10, (cipher - 1));

	if (!(parseInt(num) < standard || num.length < cipher)) {
		return num;
	}

	var zeroCounter = (cipher - (num + '').length);
	var zero = "";
	for (var i = 0; i < zeroCounter; i++) {
		zero += "0";
	}

	return zero + num;
}

/**
 * 현재 날짜
 */
function getToday(delimiter){
	var dt = getDateInfo();
    var div = delimiter || '';
    return [dt.Y, dt.M, dt.D].join(div);
}

/**
 * ,구분자 숫자 return
 * @param dt Date
 */
function getDateInfo(date){
	var dt = date || new Date();

	var date = {
		Y: dt.getFullYear(),
		M: n2c(dt.getMonth() + 1),
		D: n2c(dt.getDate()),
		h: n2c(dt.getHours()),
		m: n2c(dt.getMinutes()),
		s: n2c(dt.getSeconds())
	};

	return date;
}

/**
 * 입력받은 문자열에서 끝에서부터의  넓이만큼 마스킹 처리 한다.
 * @param str
 * @param chr
 * @param width
 * @returns {String}
 */
function formatMaskByPostionEnd(str, chr, width) {
	str = str.trim();

	var temp = "";
	var mask = "";

	temp = str.substring(0, str.length - width );

	for ( var i = 0; i < width; i++ ) {
		mask += chr;
	}

	return temp + mask;
}

/**
 * null 처리
 */
function nvl(orgVal, defaultVal){
	var replaceVal = defaultVal || '';

	return isEmpty(orgVal) ? replaceVal : orgVal;
}

// 월 약자 리턴
function getMonthStr(month){
	var strMonth = '';

	switch((month * 1)){
	case 1 :
		strMonth = 'Jan';
		break;
	case 2 :
		strMonth = 'Feb';
		break;
	case 3 :
		strMonth = 'Mar';
		break;
	case 4 :
		strMonth = 'Apr';
		break;
	case 5 :
		strMonth = 'May';
		break;
	case 6 :
		strMonth = 'Jun';
		break;
	case 7 :
		strMonth = 'Jul';
		break;
	case 8 :
		strMonth = 'Aug';
		break;
	case 9 :
		strMonth = 'Sept';
		break;
	case 10 :
		strMonth = 'Oct';
		break;
	case 11 :
		strMonth = 'Nov';
		break;
	case 12 :
		strMonth = 'Dec';
		break;
	default : '';
	}

	return strMonth;
}

// 월 계산
function addMonth(ym, addM, delimiter){
	var div = delimiter || '';

	var accountDt = addMonthData(ym, addM);

	return accountDt.Y + delimiter + accountDt.M;
}

// 월 계산
function addMonthData(ym, addM){
	var year = ym.substring(0, 4);
	var month = ym.substring(4, 6);

	return getDateInfo(new Date(year, ((month * 1) + addM - 1)));
}

// 배역 빈값 체크
function isEmptyObject(obj) {
    for (var name in obj) {
        return false;
    }

    return true;
}