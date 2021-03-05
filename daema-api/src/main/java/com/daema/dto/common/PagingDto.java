package com.daema.dto.common;

import io.swagger.annotations.ApiModelProperty;

public class PagingDto {

	@ApiModelProperty(value = "게시물 총 개수", example = "0")
	private int totalCnt;

	@ApiModelProperty(value = "페이지 번호", example = "0")
	private int pageNo;

	@ApiModelProperty(value = "페이지당 노출 갯수", example = "10")
	private int perPageCnt;

	@ApiModelProperty(value = "노출 페이징수", example = "10")
	private int pageRange;

	@ApiModelProperty(value = "페이징 시작", example = "0")
	private int pageStartNo;

	@ApiModelProperty(value = "페이징 종료", hidden = true, example = "0")
	private int pageEndNo;

	public PagingDto () {
		this.pageNo = 1;
		this.perPageCnt = 10;
		this.pageRange = 10;
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

	public int getPageRange() {
		return pageRange;
	}

	public void setPageRange(int pageRange) {
		this.pageRange = pageRange;
	}

	public int getPageStartNo() {
		this.pageStartNo = (this.pageNo - 1) * this.perPageCnt;

		return this.pageStartNo;
	}

	public void setPageStartNo(int pageStartNo) {
		this.pageStartNo = pageStartNo;
	}

	public int getPageEndNo() {
		this.pageEndNo = this.pageNo * this.perPageCnt;
		return this.pageEndNo;
	}

	public void setPageEndNo(int pageEndNo) {
		this.pageEndNo = pageEndNo;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
}
