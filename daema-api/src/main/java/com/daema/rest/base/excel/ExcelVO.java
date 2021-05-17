package com.daema.rest.base.excel;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 엑셀 다운로드에 사용되는 VO
 * ExcelMeta(headerName = "엑셀 상단 컬럼명")
 * 각 변수명은 query field 를 명시
 * ExcelUtil 을 통해 createCell(value) 진행
 */
public class ExcelVO {

	public static class PubNotiRawData {
		@ExcelMeta(headerName = "요금제명")
		private String chargeName;
		@ExcelMeta(headerName = "상품명")
		private String goodsName;
		@ExcelMeta(headerName = "제조사명")
		private String makerName;
		@ExcelMeta(headerName = "모델명")
		private String modelName;
		@ExcelMeta(headerName = "출고가")
		private int releaseAmt;
		@ExcelMeta(headerName = "출고일자")
		private LocalDate releaseDate;
		@ExcelMeta(headerName = "공시지원금")
		private int supportAmt;
		@ExcelMeta(headerName = "통신사명")
		private String telecomName;
		@ExcelMeta(headerName = "서비스명")
		private String networkName;
		@ExcelMeta(headerName = "요금코드")
		private String chargeCode;
		@ExcelMeta(headerName = "출고가변동")
		private Integer diffReleaseAmt;
		@ExcelMeta(headerName = "공시지원금변동")
		private Integer diffSupportAmt;
		@ExcelMeta(headerName = "이전 공시일자")
		private LocalDate prevReleaseDate;
		@ExcelMeta(headerName = "이전 출고가")
		private Integer prevReleaseAmt;
		@ExcelMeta(headerName = "등록일시")
		private LocalDateTime regiDateTime;
	}

	public static class ReturnStockList {
		@ExcelMeta(headerName = "차감비")
		private Integer ddctAmt;
		@ExcelMeta(headerName = "추가 차감비")
		private Integer addDdctAmt;
		@ExcelMeta(headerName = "제조사명")
		private String makerName;
		@ExcelMeta(headerName = "모델명")
		private String modelName;
		@ExcelMeta(headerName = "출고가")
		private int releaseAmt;
		@ExcelMeta(headerName = "출고일자")
		private LocalDate releaseDate;
		@ExcelMeta(headerName = "공시지원금")
		private int supportAmt;
		@ExcelMeta(headerName = "통신사명")
		private String telecomName;
		@ExcelMeta(headerName = "서비스명")
		private String networkName;
		@ExcelMeta(headerName = "요금코드")
		private String chargeCode;
		@ExcelMeta(headerName = "출고가변동")
		private Integer diffReleaseAmt;
		@ExcelMeta(headerName = "공시지원금변동")
		private Integer diffSupportAmt;
		@ExcelMeta(headerName = "이전 공시일자")
		private LocalDate prevReleaseDate;
		@ExcelMeta(headerName = "이전 출고가")
		private Integer prevReleaseAmt;
		@ExcelMeta(headerName = "등록일시")
		private LocalDateTime regiDateTime;
	}
}