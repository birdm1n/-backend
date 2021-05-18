package com.daema.rest.base.excel;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 엑셀 다운로드에 사용되는 VO
 * ExcelMeta(columnName = "엑셀 상단 컬럼명")
 * 각 변수명은 query field 를 명시
 * ExcelUtil 을 통해 createCell(value) 진행
 */
public class ExcelVO {

	public static class PubNotiRawData {
		@ExcelTemplate(columnName = "요금제명")
		private String chargeName;
		@ExcelTemplate(columnName = "상품명")
		private String goodsName;
		@ExcelTemplate(columnName = "제조사명")
		private String makerName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "출고가")
		private int releaseAmt;
		@ExcelTemplate(columnName = "출고일자")
		private LocalDate releaseDate;
		@ExcelTemplate(columnName = "공시지원금")
		private int supportAmt;
		@ExcelTemplate(columnName = "통신사명")
		private String telecomName;
		@ExcelTemplate(columnName = "서비스명")
		private String networkName;
		@ExcelTemplate(columnName = "요금코드")
		private String chargeCode;
		@ExcelTemplate(columnName = "출고가변동")
		private Integer diffReleaseAmt;
		@ExcelTemplate(columnName = "공시지원금변동")
		private Integer diffSupportAmt;
		@ExcelTemplate(columnName = "이전 공시일자")
		private LocalDate prevReleaseDate;
		@ExcelTemplate(columnName = "이전 출고가")
		private Integer prevReleaseAmt;
		@ExcelTemplate(columnName = "등록일시")
		private LocalDateTime regiDateTime;
	}

	public static class ReturnStockList {
		@ExcelTemplate(columnName = "차감비")
		private Integer ddctAmt;
		@ExcelTemplate(columnName = "추가 차감비")
		private Integer addDdctAmt;
		@ExcelTemplate(columnName = "제조사명")
		private String makerName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "출고가")
		private int releaseAmt;
		@ExcelTemplate(columnName = "출고일자")
		private LocalDate releaseDate;
		@ExcelTemplate(columnName = "공시지원금")
		private int supportAmt;
		@ExcelTemplate(columnName = "통신사명")
		private String telecomName;
		@ExcelTemplate(columnName = "서비스명")
		private String networkName;
		@ExcelTemplate(columnName = "요금코드")
		private String chargeCode;
		@ExcelTemplate(columnName = "출고가변동")
		private Integer diffReleaseAmt;
		@ExcelTemplate(columnName = "공시지원금변동")
		private Integer diffSupportAmt;
		@ExcelTemplate(columnName = "이전 공시일자")
		private LocalDate prevReleaseDate;
		@ExcelTemplate(columnName = "이전 출고가")
		private Integer prevReleaseAmt;
		@ExcelTemplate(columnName = "등록일시")
		private LocalDateTime regiDateTime;
	}

	public static class BarcodeList {
		@ExcelTemplate(columnName = "기기일련번호")
		private String fullBarcode;
	}
}