package com.daema.core.wms.dto.response;

import com.daema.core.base.enums.StatusEnum;
import com.daema.core.wms.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class StockListDto {

    private long stockId;
    private long parentStockId;
    private String stockName;
    private String chargerName;
    private String chargerPhone;
    private String chargerPhone1;
    private String chargerPhone2;
    private String chargerPhone3;
    private long storeId;
    private String stockType;
    private int depth;
    private String hierarchy;
    private int dvcCnt;

    private List<StockListDto> children;

    public StockListDto(Stock stock) {

        this.stockId = stock.getStockId();
        this.parentStockId = stock.getParentStock() != null ? stock.getParentStock().getStockId() : 0;
        this.stockName = stock.getStockName();
        this.chargerName = stock.getChargerName();
        this.chargerPhone = stock.getChargerPhone();
        this.chargerPhone1 = stock.getChargerPhone1();
        this.chargerPhone2 = stock.getChargerPhone2();
        this.chargerPhone3 = stock.getChargerPhone3();
        this.storeId = stock.getStoreId();
        this.stockType = stock.getStockType();

        if(stock.getParentStock() != null){

            this.hierarchy = stock.getParentStock().getStockId() + "/" + stock.getStockId() + "/";

            if(stock.getParentStock().getParentStock() != null){
                this.hierarchy = stock.getParentStock().getParentStock().getStockId() + "/" + this.hierarchy;
            }
        }else{
            this.hierarchy = stock.getStockId() + "/";
        }

        this.depth = StringUtils.countOccurrencesOf(this.hierarchy, "/");

        this.dvcCnt = 0;
        this.children = stock.getChildStockList()
                .stream()
                .filter(child -> child.getDelYn().equals(StatusEnum.FLAG_N.getStatusMsg()))
                .map(StockListDto::new).collect(Collectors.toList());
    }

}
