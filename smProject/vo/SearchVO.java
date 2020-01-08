package com.smProject.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smProject.util.StringUtil;

/**
 * 검색 VO
 */
@JsonIgnoreProperties({ "queryString", "searchType", "searchField", "searchValue", "searchSort", "startDt", "endDt",
		"startRow", "endRow", "objects", "currentPage", "totalCount", "pageNo", "blockSize", "rowSize", "maxPage",
		"beginUnitPage", "endUnitPage", "search", "condition", "size", "total", "endListPage", "nextPage", "list",
		"emptyPage", "firstIndex", "previousPage", "recordCountPerPage", "currentPageStr", "pageOfNextpageno",
		"startOfNextpageno", "pageOfPreviouspageno", "startOfPreviouspageno" })
public class SearchVO extends PageVO {

	/** 검색 파라미터 */
	private String queryString = "";

	/** 검색 타입 */
	private String searchType = "";

	/** 검색 필드 */
	private String searchField = "";

	/** 검색 값 */
	private String searchValue = "";

	/** 정렬 기준 */
	private String searchSort = "";

	/** 시작일 */
	private String startDt = "";

	/** 종료일 */
	private String endDt = "";

	/** 페이징 시작 row */
	private int startRow;

	/** 페이징 종료 row */
	private int endRow;

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSearchSort() {
		return searchSort;
	}

	public void setSearchSort(String searchSort) {
		this.searchSort = searchSort;
	}

	public String getStartDt() {
		return startDt;
	}

	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public int getStartRow() {
		this.startRow = ((this.getPageNo() - 1) * this.getRowSize() + 1);
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		this.endRow = (this.getStartRow() + this.getRowSize() - 1);
		return endRow;
	}

	public int getBoardNo(int totalCnt) {
		return totalCnt - (getPageNo() - 1) * getBlockSize();
	}

	public void setSearchDt(String div) {
		if (StringUtil.isNotEmpty(startDt)) {
			startDt = startDt.replace(div, "");
		}
		if (StringUtil.isNotEmpty(endDt)) {
			endDt = endDt.replace(div, "");
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SearchVO [queryString=");
		builder.append(queryString);
		builder.append(", searchField=");
		builder.append(searchField);
		builder.append(", searchValue=");
		builder.append(searchValue);
		builder.append(", searchSort=");
		builder.append(searchSort);
		builder.append(", startDt=");
		builder.append(startDt);
		builder.append(", endDt=");
		builder.append(endDt);
		builder.append(", startRow=");
		builder.append(startRow);
		builder.append(", endRow=");
		builder.append(endRow);
		builder.append("]");
		return builder.toString();
	}

}
