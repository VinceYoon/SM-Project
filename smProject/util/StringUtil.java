package com.smProject.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	private static final DecimalFormat df = new DecimalFormat("#,###.####");

	/**
	 * null 체크
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	/**
	 * null 체크
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object str) {

		if (str == null) {
			return true;
		}

		return isEmpty(str.toString());
	}

	/**
	 * 공백 포함 null 체크
	 *
	 * @param foo
	 * @return
	 */
	public static final boolean isEmptyTrimmed(String foo) {
		return ((foo == null) || (foo.trim().length() == 0));
	}

	/**
	 * null이 아닌 값 체크
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return (!(isEmpty(str)));
	}

	/**
	 * replace 처리
	 *
	 * @param org
	 * @param var
	 * @param tgt
	 * @return
	 */
	public static String replaceStr(String org, String var, String tgt) {

		StringBuffer str = new StringBuffer("");
		int end = 0;
		int begin = 0;
		if (org == null || org.equals("")) {
			return org;
		}
		while (true) {
			end = org.indexOf(var, begin);
			if (end == -1) {
				end = org.length();
				str.append(org.substring(begin, end));
				break;
			}
			str.append(org.substring(begin, end) + tgt);
			begin = end + var.length();
		}

		return str.toString();
	}

	/**
	 * 문구 반대로
	 *
	 * @param str
	 * @return
	 */
	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		return new StringBuffer(str).reverse().toString();
	}

	/**
	 * 숫자형 문구 int형으로 변환
	 *
	 * @param str
	 * @param dValue
	 * @return
	 */
	public static int convInt(String str, int dValue) {
		int rValue = 0;
		try {
			rValue = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			rValue = dValue;
		}

		return rValue;
	}

	/**
	 * 문자 말줄임 처리
	 *
	 * @param str
	 * @param length
	 * @return
	 */
	public static String cutStr(String str, int length) {
		try {
			String rValue = str.substring(0, length - 2);
			return rValue + "..";
		} catch (Exception e) {
		}
		return str;
	}

	/**
	 * 숫자에 세자리 수 콤마
	 *
	 * @param str
	 * @return
	 */
	public static String cvtNumber(String str) {
		String retStr;
		try {
			BigDecimal dcmData = new BigDecimal(str);
			retStr = df.format(dcmData);
		} catch (NumberFormatException nfe) {
			retStr = str;
		}

		return retStr;
	}

	/**
	 * 숫자 세자리수 콤마
	 *
	 * @param no
	 * @return
	 */
	public static String cvtNumber(int no) {
		return cvtNumber(no + "");
	}

	/**
	 * 문자 숫자형인지 체크
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		if (sz == 0) {
			return false;
		}
		for (int i = 0; i < sz; ++i) {
			if (!(Character.isDigit(str.charAt(i)))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 문자열 유무 검사
	 *
	 * @param str
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static boolean isPatternMatching(String str, String pattern) throws Exception {
		if (pattern.indexOf(42) >= 0) {
			pattern = pattern.replaceAll("\\*", ".*");
		}

		pattern = "^" + pattern + "$";

		return Pattern.matches(pattern, str);
	}

	/**
	 * 숫자 10보다 작은 건 01,02 형태로 표시
	 *
	 * @param num
	 * @return
	 */
	public static String n2c(int num) {
		if (num < 10) {
			return "0" + num;
		}
		return num + "";
	}

	/**
	 * 문자 10보다 작은건 01,02 형태로 표시
	 *
	 * @param snum
	 * @return
	 */
	public static String n2c(String snum) {
		int num = 0;
		try {
			num = Integer.parseInt(snum);
		} catch (Exception e) {
			return snum;
		}
		return n2c(num);
	}

	/**
	 * null 입력시 "" 로 치환
	 *
	 * @param orgValue
	 * @return
	 * @throws Exception
	 */
	public static String nvl(String orgValue) throws Exception {
		return nvl(orgValue, "");
	}

	/**
	 * null 입력시 defValue 로 치환
	 *
	 * @param orgValue
	 * @param defValue
	 * @return
	 * @throws Exception
	 */
	public static String nvl(String orgValue, String defValue) throws Exception {
		String rtnStr = "";
		if (isEmpty(orgValue)) {
			rtnStr = defValue;
		} else {
			rtnStr = orgValue;
		}
		return rtnStr;
	}

	/**
	 * @param text
	 * @param parser
	 * @return
	 */
	public static String[] split(String text, String parser) {
		int count = 0, index = 0, i = 0, endIdx = 0;
		do {
			count++;
			index++;
			index = text.indexOf(parser, index);
		} while (index != -1);
		String[] data = new String[count];
		index = 0;
		while (i < count) {
			endIdx = text.indexOf(parser, index);
			if (endIdx == -1) {
				data[i] = text.substring(index).trim();
			} else {
				data[i] = text.substring(index, endIdx).trim();
			}
			index = endIdx + 1;
			i++;
		}
		return data;
	}

	/**
	 * 날짜 포맷 변경
	 *
	 * @param dFormat
	 * @param dt
	 * @return
	 * @throws Exception
	 */
	public static String getDateFormat(String dt, String dFormat) throws Exception {
		if (dt == null || (dt.length() != 8 && dt.length() != 14 && dt.length() != 12))
			return (dt);

		String y = dt.substring(0, 4);
		String m = dt.substring(4, 6);
		String d = dt.substring(6, 8);
		String h = "";
		String mm = "";
		String s = "";

		if (dt.length() == 14) {
			h = dt.substring(8, 10);
			mm = dt.substring(10, 12);
			s = dt.substring(12);
		}

		if (dt.length() == 12) {
			h = dt.substring(8, 10);
			mm = dt.substring(10, 12);
		}

		String rValue = "";
		for (int i = 0; i < dFormat.length(); i++) {
			switch (dFormat.charAt(i)) {
			case 'Y':
				rValue += y;
				break;
			case 'M':
				rValue += m;
				break;
			case 'D':
				rValue += d;
				break;
			case 'h':
				rValue += h;
				break;
			case 'm':
				rValue += mm;
				break;
			case 's':
				rValue += s;
				break;
			default:
				rValue += dFormat.charAt(i);
			}
		}
		return (rValue);
	}

	/**
	 * 문자열 target을 주어진 길이(cutLength)만큼 잘라서, 말 줄임표 문자열(tail)을 붙인 뒤 반환
	 *
	 * @param target
	 * @param cutLength
	 * @param tail
	 * @return
	 */
	public static String cutStrLenNicely(String target, int cutLength, String tail) {
		String resultStr = target;
		if (resultStr.length() > cutLength) {
			resultStr = resultStr.substring(0, cutLength) + tail;
		}
		return resultStr;
	}

	/**
	 *
	 * 문자열을 짜르고 기준이 되는 값을 가져옴
	 *
	 * @param str
	 * @param gubun
	 * @param token
	 * @return
	 */
	public static String splitStr(String str, String gubun, int token) {

		StringTokenizer stk = new StringTokenizer(str, gubun);

		if (stk.countTokens() == 0) {
			return "";
		}

		String[] array = new String[stk.countTokens()];
		int i = 0;
		while (stk.hasMoreElements()) {
			array[i++] = stk.nextToken();
		}

		if (array.length > token) {
			return array[token];

		}

		return "";
	}

	/**
	 * replace
	 *
	 * @param str
	 * @param rep
	 * @param tok
	 * @return
	 */
	public static String replace(String str, String rep, String tok) {
		String retStr = "";
		if (isEmpty(str)) {
			return "";
		}

		int i = 0;
		for (int j = 0; (j = str.indexOf(rep, i)) > -1; i = j + rep.length()) {
			retStr = retStr + str.substring(i, j) + tok;
		}

		return retStr + str.substring(str.lastIndexOf(rep) + rep.length(), str.length());
	}

	/**
	 * double 형 string으로 변환
	 *
	 * @param d
	 * @return
	 */
	public static String fmtDouble(double d) {

		if (d == (long) d) {
			return String.format("%d", (long) d);
		} else {
			return String.format("%s", d);
		}
	}

	/**
	 * 문자의 앞자리 제거
	 *
	 * @param str
	 * @param beginIndex
	 * @return
	 */
	public static String preSubStr(String str, int beginIndex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.substring(beginIndex);
	}

	/**
	 *
	 * 엑셀 파일타입인지 확장자 체크
	 *
	 * @param file_name
	 * @return
	 */
	public static boolean excelFileChk(String file_name) {

		if (file_name.toUpperCase().endsWith(".XLS")) {
			return true;

		} else if (file_name.toUpperCase().endsWith(".XLSX")) {
			return true;
		}

		return false;
	}

	/**
	 *
	 * HTML Tag 제거 후 반환
	 *
	 * @param target
	 * @return
	 */
	public static String removeHtml(String target) {
		if (isEmpty(target)) {
			return "";
		}

		String result = target;
		return result.replaceAll("\\<[^>]*>", "");
	}

	/**
	 * 개행 문자 br 치환
	 *
	 * @param str
	 * @return
	 */
	public static String replaceBr(String str) {
		if (isEmpty(str)) {
			return "";
		}

		return str.replaceAll("\\n", "<br />");
	}

	/**
	 * 배열의 값에 param이 존재하는지 확인
	 * @param values
	 * @param param
	 * @return
	 */
	public static boolean isArrayStringEmpty(String[] values, String param) {

		if (values == null || values.length == 0) {
			return false;
		}

		for (String value : values) {
			if (value.equals(param)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * 파라미터 유효성 체크
	 * @param map
	 * @param params
	 * @return
	 */
	public static boolean isValidationCheck(Map<String, Object> map, String... params) {

		if (params == null || params.length == 0) {
			return true;
		}

		if (map == null) {
			return false;
		}

		for (String checkParam : params) {
			if (StringUtil.isEmpty(map.get(checkParam))) {
				logger.info("VALID CHECK {} PARAM EMPTY", checkParam);
				return false;
			}
		}

		return true;
	}

}
