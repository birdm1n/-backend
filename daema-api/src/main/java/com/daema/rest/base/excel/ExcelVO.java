package com.daema.rest.base.excel;

import com.daema.core.wms.dto.response.DeviceStatusListDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 엑셀 다운로드에 사용되는 VO
 * ExcelMeta(columnName = "엑셀 상단 컬럼명")
 * 각 변수명은 query field 를 명시
 * ExcelUtil 을 통해 createCell(value) 진행
 */

//입고현황 InStockList


//판매이동 SellMoveList
//재고이동 StockMoveList
//재고이관 TransStoreList
//불량이관 FaultyTransList
//판매이관 SellTransList
//이동현황 MoveMgmtList
//기기현황 DeviceCurrentList


//반품목록 ReturnStockList
//재고현황 StoreStockList
//장기재고 LongTimeStoreStockList
//불량기기현황 FaultyStoreStockList
//이동재고반품_엑셀업로드_실패목록 BarcodeList
public class ExcelVO {

	//sample 공시지원금목록
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
		private Integer releaseAmt;
		@ExcelTemplate(columnName = "출고일자")
		private LocalDate releaseDate;
		@ExcelTemplate(columnName = "공시지원금")
		private Integer supportAmt;
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

	//입고현황
	public static class InStockList {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime regiDateTime;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffRegiDate;
		@ExcelTemplate(columnName = "공급처")
		private String provName;
		@ExcelTemplate(columnName = "보유처")
		private String stockName;
		@ExcelTemplate(columnName = "재고구분")
		private String statusStrMsg;
		@ExcelTemplate(columnName = "제조사")
		private String makerName;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private Integer modelName;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "기기일련번호")
		private String rawBarcode;
		@ExcelTemplate(columnName = "입고단가")
		private String inStockAmt;
		@ExcelTemplate(columnName = "입고상태")
		private String inStockStatusMsg;
		@ExcelTemplate(columnName = "제품상태")
		private String productFaultyYn;
		@ExcelTemplate(columnName = "외장상태")
		private String extrrStatusMsg;
	}

	// 판매이동
	public static class SellMoveList {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime inStockRegiDateTime;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffInStockRegiDate;
		@ExcelTemplate(columnName = "출고일")
		private LocalDateTime regiDateTime;
		@ExcelTemplate(columnName = "출고일-경과일")
		private String diffRegiDate;
		@ExcelTemplate(columnName = "재고구분")
		private String moveStockTypeMsg;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "기기 일련번호")
		private String rawBarcode;
		@ExcelTemplate(columnName = "입고단가")
		private String inStockAmt;
		@ExcelTemplate(columnName = "입고상태")
		private String inStockStatusMsg;
		@ExcelTemplate(columnName = "제품상태")
		private String productFaultyYn;
		@ExcelTemplate(columnName = "외장상태")
		private String extrrStatusMsg;
		@ExcelTemplate(columnName = "고객명")
		private String cusName;
		@ExcelTemplate(columnName = "전화번호")
		private String cusPhone;
	}

	// 재고이동
	public static class StockMoveList {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime inStockRegiDateTime;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffInStockRegiDate;
		@ExcelTemplate(columnName = "출고일")
		private LocalDateTime regiDateTime;
		@ExcelTemplate(columnName = "출고일-경과일")
		private String diffRegiDate;
		@ExcelTemplate(columnName = "재고구분")
		private String moveStockTypeMsg;
		@ExcelTemplate(columnName = "이전보유처")
		private String prevStockName;
		@ExcelTemplate(columnName = "현재보유처")
		private String nextStockName;
		@ExcelTemplate(columnName = "제조사")
		private String makerName;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "기기 일련번호")
		private String rawBarcode;
		@ExcelTemplate(columnName = "입고단가")
		private String inStockAmt;
		@ExcelTemplate(columnName = "입고상태")
		private String inStockStatusMsg;
		@ExcelTemplate(columnName = "제품상태")
		private String productFaultyYn;
		@ExcelTemplate(columnName = "외장상태")
		private String extrrStatusMsg;

		/*

		수량 response 가 없습니다.

		*/
		/*@ExcelTemplate(columnName = "수량")
		private String ; */
	}

	// 재고이관
	public static class StoreTransList {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime inStockRegiDateTime;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffInStockRegiDate;
		@ExcelTemplate(columnName = "출고일")
		private LocalDateTime regiDateTime;
		@ExcelTemplate(columnName = "출고일-경과일")
		private String diffRegiDate;
		@ExcelTemplate(columnName = "재고구분")
		private String outStockTypeMsg;
		@ExcelTemplate(columnName = "이전보유처")
		private String prevStockName;
//		@ExcelTemplate(columnName = "현재보유처")
//		private String nextStockName;
		@ExcelTemplate(columnName = "제조사")
		private String makerName;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "기기 일련번호")
		private String rawBarcode;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "입고상태")
		private String inStockStatusMsg;
		@ExcelTemplate(columnName = "제품상태")
		private String productFaultyYn;
		@ExcelTemplate(columnName = "외장상태")
		private String extrrStatusMsg;
		@ExcelTemplate(columnName = "입고단가")
		private String inStockAmt;
	}

	// 불량이관
	public static class FaultyTransList  {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime inStockRegiDateTime;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffInStockRegiDate;
		@ExcelTemplate(columnName = "출고일")
		private LocalDateTime regiDateTime;
		@ExcelTemplate(columnName = "출고일-경과일")
		private String diffRegiDate;
		@ExcelTemplate(columnName = "재고구분")
		private String outStockTypeMsg;
		@ExcelTemplate(columnName = "보유처")
		private String prevStockName;
		@ExcelTemplate(columnName = "공급처")
		private String nextProvName;
		@ExcelTemplate(columnName = "제조사")
		private String makerName;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "기기일련번호")
		private String rawBarcode;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "입고상태")
		private String inStockStatusMsg;
		@ExcelTemplate(columnName = "제품상태")
		private String productFaultyYn;
		@ExcelTemplate(columnName = "외장상태")
		private String extrrStatusMsg;
		@ExcelTemplate(columnName = "입고단가")
		private String inStockAmt;
	}

	// 판매이관
	public static class SellTransList  {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime inStockRegiDateTime;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffInStockRegiDate;
		@ExcelTemplate(columnName = "출고일")
		private LocalDateTime regiDateTime;
		@ExcelTemplate(columnName = "출고일-경과일")
		private String diffRegiDate;
		@ExcelTemplate(columnName = "재고구분")
		private String outStockTypeMsg;
		@ExcelTemplate(columnName = "이전보유처")
		private String prevStockName;
//		@ExcelTemplate(columnName = "현재보유처")
//		private String nextStoreName;
		@ExcelTemplate(columnName = "제조사")
		private String makerName;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "기기일련번호")
		private String rawBarcode;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "입고상태")
		private String inStockStatusMsg;
		@ExcelTemplate(columnName = "제품상태")
		private String productFaultyYn;
		@ExcelTemplate(columnName = "외장상태")
		private String extrrStatusMsg;
		@ExcelTemplate(columnName = "입고단가")
		private String inStockAmt;
	}
	// 불량이관
	public static class ReturnTransList  {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime inStockRegiDateTime;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffInStockRegiDate;
		@ExcelTemplate(columnName = "출고일")
		private LocalDateTime regiDateTime;
		@ExcelTemplate(columnName = "출고일-경과일")
		private String diffRegiDate;
		@ExcelTemplate(columnName = "재고구분")
		private String outStockTypeMsg;
		@ExcelTemplate(columnName = "보유처")
		private String prevStockName;
		@ExcelTemplate(columnName = "공급처")
		private String nextProvName;
		@ExcelTemplate(columnName = "제조사")
		private String makerName;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "기기일련번호")
		private String rawBarcode;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "입고상태")
		private String inStockStatusMsg;
		@ExcelTemplate(columnName = "제품상태")
		private String productFaultyYn;
		@ExcelTemplate(columnName = "외장상태")
		private String extrrStatusMsg;
		@ExcelTemplate(columnName = "입고단가")
		private String inStockAmt;
	}

	// 이동현황
	public static class MoveMgmtList {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime inStockRegiDateTime;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffInStockRegiDate;
		@ExcelTemplate(columnName = "출고일")
		private LocalDateTime moveRegiDateTime;
		@ExcelTemplate(columnName = "출고일-경과일")
		private String diffMoveRegiDateTime;
		@ExcelTemplate(columnName = "이전보유처")
		private String prevStockName;
		@ExcelTemplate(columnName = "현재보유처")
		private String nextStockName;
		@ExcelTemplate(columnName = "재고구분")
		private String stockTypeMsg;
		@ExcelTemplate(columnName = "제조사")
		private String makerName;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "기기 일련번호")
		private String rawBarcode;
		@ExcelTemplate(columnName = "입고단가")
		private String inStockAmt;
		@ExcelTemplate(columnName = "입고상태")
		private String inStockStatusMsg;
		@ExcelTemplate(columnName = "제품상태")
		private String productFaultyYn;
		@ExcelTemplate(columnName = "외장상태")
		private String extrrStatusMsg;
		@ExcelTemplate(columnName = "배송방법")
		private String deliveryTypeMsg;
		@ExcelTemplate(columnName = "배송상태")
		private String deliveryStatusMsg;

	}

	//개통이력기기현황
	public static class OpeningCurrentList {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime inStockRegiDate;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffInStockRegiDate;
		@ExcelTemplate(columnName = "개통일")
		private LocalDate openingDate;
		@ExcelTemplate(columnName = "개통일-경과일")
		private Long diffOpeningDate;
		@ExcelTemplate(columnName = "보유처 Before")
		private String prevStockName;
		@ExcelTemplate(columnName = "보유처 After")
		private String nextStockName;
		@ExcelTemplate(columnName = "재고구분")
		private String stockTypeMsg;
		@ExcelTemplate(columnName = "제조사")
		private String makerName;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "기기일련번호")
		private String rawBarcode;
		@ExcelTemplate(columnName = "입고단가")
		private String inStockAmt;
		@ExcelTemplate(columnName = "고객명")
		private String cusName;
		@ExcelTemplate(columnName = "고객 전화번호")
		private String cusPhone;
		@ExcelTemplate(columnName = "철회")
		private String cancelStatusMsg;
	}

	//기기현황
	public static class DeviceCurrentList {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime inStockRegiDate;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffInStockRegiDate;
		@ExcelTemplate(columnName = "출고일")
		private LocalDateTime moveRegiDate;
		@ExcelTemplate(columnName = "출고일-경과일")
		private String diffMoveRegiDate;
		@ExcelTemplate(columnName = "보유처 Before")
		private String prevStockName;
		@ExcelTemplate(columnName = "보유처 After")
		private String nextStockName;
		@ExcelTemplate(columnName = "재고구분")
		private String stockTypeMsg;
		@ExcelTemplate(columnName = "제조사")
		private String makerName;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "기기일련번호")
		private String rawBarcode;
		@ExcelTemplate(columnName = "입고단가")
		private String inStockAmt;
		@ExcelTemplate(columnName = "입고상태")
		private String inStockStatusMsg;
		@ExcelTemplate(columnName = "제품상태")
		private String productFaultyYn;
		@ExcelTemplate(columnName = "외장상태")
		private String extrrStatusMsg;
		@ExcelTemplate(columnName = "배송방법")
		private String deliveryTypeMsg;
		@ExcelTemplate(columnName = "배송상태")
		private String deliveryStatusMsg;
		@ExcelTemplate(columnName = "개통상태")
		private String openingText;

	}

	//반품목록
	public static class ReturnStockList {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime inStockRegiDateTime;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffInStockRegiDate;
		@ExcelTemplate(columnName = "출고일")
		private LocalDateTime regiDateTime;
		@ExcelTemplate(columnName = "출고일-경과일")
		private String diffReturnStockRegiDate;
		@ExcelTemplate(columnName = "보유처 Before")
		private String prevStockName;
		@ExcelTemplate(columnName = "보유처 After")
		private String nextStockName;
		@ExcelTemplate(columnName = "재고구분")
		private String statusStr;
		@ExcelTemplate(columnName = "제조사")
		private String makerName;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "기기일련번호")
		private String rawBarcode;
		/*
		@ExcelTemplate(columnName = "고객명")
		private String regiUserName;
		@ExcelTemplate(columnName = "전화번호")
		private String
		*/
		@ExcelTemplate(columnName = "반품비")
		private String returnStockAmt;
		@ExcelTemplate(columnName = "추가차감비")
		private String addDdctAmt;
	}

	// 재고현황
	public static class StoreStockList {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime inStockRegiDateTime;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffInStockRegiDate;
		@ExcelTemplate(columnName = "재고확인일")
		private LocalDateTime stockCheckDateTime1;
		@ExcelTemplate(columnName = "재고확인일-경과일")
		private String diffStockCheckDateTime1;
		@ExcelTemplate(columnName = "재고확인일")
		private LocalDateTime stockCheckDateTime2;
		@ExcelTemplate(columnName = "재고확인일-경과일")
		private String diffStockCheckDateTime2;
		@ExcelTemplate(columnName = "현재보유처")
		private String prevStockName;
		@ExcelTemplate(columnName = "다음보유처")
		private String nextStockName;
		@ExcelTemplate(columnName = "재고구분")
		private String statusStr;
		@ExcelTemplate(columnName = "제조사")
		private String makerName;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "기기 일련번호")
		private String rawBarcode;
		@ExcelTemplate(columnName = "입고단가")
		private String inStockAmt;
		@ExcelTemplate(columnName = "입고상태")
		private DeviceStatusListDto inStockStatusMsg;
		@ExcelTemplate(columnName = "제품상태")
		private DeviceStatusListDto productFaultyYn;
		@ExcelTemplate(columnName = "외장상태")
		private DeviceStatusListDto extrrStatusMsg;
	}

	//장기재고
	public static class LongTimeStoreStockList {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime inStockRegiDateTime;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffInStockRegiDate;
		@ExcelTemplate(columnName = "출고일")
		private LocalDateTime moveDateTime;
		@ExcelTemplate(columnName = "출고일-경과일")
		private String diffMoveDateTime;
		@ExcelTemplate(columnName = "보유처 Before")
		private String prevStockName;
		@ExcelTemplate(columnName = "보유처 After")
		private String nextStockName;
		@ExcelTemplate(columnName = "재고구분")
		private String statusStr;
		@ExcelTemplate(columnName = "제조사")
		private String makerName;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "기기일련번호")
		private String rawBarcode;
		@ExcelTemplate(columnName = "입고단가")
		private String inStockAmt;
		@ExcelTemplate(columnName = "입고상태")
		private DeviceStatusListDto inStockStatusMsg;
		@ExcelTemplate(columnName = "제품상태")
		private DeviceStatusListDto productFaultyYn;
		@ExcelTemplate(columnName = "외장상태")
		private DeviceStatusListDto extrrStatusMsg;
	}

	//불량기기현황
	public static class FaultyStoreStockList {
		@ExcelTemplate(columnName = "통신사")
		private String telecomName;
		@ExcelTemplate(columnName = "입고일")
		private LocalDateTime inStockRegiDateTime;
		@ExcelTemplate(columnName = "입고일-경과일")
		private String diffInStockRegiDate;
		@ExcelTemplate(columnName = "이관일")
		private LocalDateTime moveDateTime;
		@ExcelTemplate(columnName = "이관일-경과일")
		private String diffMoveDateTime;
		@ExcelTemplate(columnName = "보유처")
		private String stockName;
		@ExcelTemplate(columnName = "공급처")
		private String provName;
		@ExcelTemplate(columnName = "재고구분")
		private String statusStr;
		@ExcelTemplate(columnName = "제조사")
		private String makerName;
		@ExcelTemplate(columnName = "기기명")
		private String goodsName;
		@ExcelTemplate(columnName = "모델명")
		private String modelName;
		@ExcelTemplate(columnName = "용량")
		private String capacity;
		@ExcelTemplate(columnName = "색상")
		private String colorName;
		@ExcelTemplate(columnName = "기기 일련번호")
		private String rawBarcode;
		@ExcelTemplate(columnName = "배송방법")
		private String deliveryTypeMsg;
		@ExcelTemplate(columnName = "배송상태")
		private String deliveryStatusMsg;
		@ExcelTemplate(columnName = "판정상태")
		private String judgeStatusMsg;
	}

	//이동재고반품/입고대기_엑셀업로드_실패목록
	public static class BarcodeList {
		@ExcelTemplate(columnName = "기기일련번호")
		private String rawBarcode;
	}
}