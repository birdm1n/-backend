package com.daema.domain.dto.common;

public class PagingDto {

	private long totalCnt;

	private int numberOfElements;

	private int pageNo;

	private int perPageCnt;

	public PagingDto () {
		this.perPageCnt = 20;
	}

	// getter, setter
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPerPageCnt() {
		return perPageCnt;
	}

	public void setPerPageCnt(int perPageCnt) {
		this.perPageCnt = perPageCnt;
	}

	public long getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(long totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
}
