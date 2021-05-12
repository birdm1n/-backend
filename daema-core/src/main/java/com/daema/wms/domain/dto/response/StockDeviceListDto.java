package com.daema.wms.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockDeviceListDto {

   private Long dvc_id;
   private Long stock_id;
   private String stock_name;
   private String hierarchy;
   private String full_barcode;
   private Integer in_stock_amt;
   private String goods_name;
   private String model_name;
   private String capacity;
   private String color_name;
   private Long maker;
   private Long telecom;
   private String telecom_name;
   private String maker_name;
}
