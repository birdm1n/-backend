package com.daema.core.wms.dto.response;

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
   private String raw_barcode;
   private Integer in_stock_amt;
   private String goods_name;
   private String model_name;
   private String capacity;
   private String color_name;
   private Long maker_code_id;
   private Long telecom_code_id;
   private String telecom_name;
   private String maker_name;
}
