package com.daema.rest.base.dto.common.bak;

import io.swagger.annotations.ApiModelProperty;

public class Bak_PagingDto {

	@ApiModelProperty(value = "게시물 총 개수", example = "0")
	private long totalCnt;

	@ApiModelProperty(value = "현재 리스트 개수", hidden = true, example = "0")
	private int numberOfElements;

	@ApiModelProperty(value = "페이지 번호", example = "0")
	private int pageNo;

	@ApiModelProperty(value = "페이지당 노출 갯수", example = "10")
	private int perPageCnt;

	public Bak_PagingDto() {
		this.perPageCnt = 10;
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
