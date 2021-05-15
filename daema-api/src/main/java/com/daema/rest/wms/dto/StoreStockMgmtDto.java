package com.daema.rest.wms.dto;

import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.Device;
import com.daema.wms.domain.Stock;
import com.daema.wms.domain.StoreStock;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreStockMgmtDto {

    private Long storeStockId;
    private Store store;
    private Device device;
    private WmsEnum.StockType stockType;
    private String stockYn;
    private Long stockTypeId;
    private Stock prevStock;
    private Stock nextStock;

    private WmsEnum.HistoryStatus historyStatus;

    public static StoreStockMgmtDto from(StoreStock storeStock) {
        return StoreStockMgmtDto.builder()
                .storeStockId(storeStock.getStoreStockId())
                .store(storeStock.getStore())
                .device(storeStock.getDevice())
                .stockType(storeStock.getStockType())
                .stockTypeId(storeStock.getStockTypeId())
                .stockYn(storeStock.getStockYn())
                .prevStock(storeStock.getPrevStock())
                .nextStock(storeStock.getNextStock())
            .build();
    }

    public static StoreStock toEntity(StoreStockMgmtDto storeStockMgmtDto) {
        return StoreStock.builder()
                .storeStockId(storeStockMgmtDto.getStoreStockId())
                .store(storeStockMgmtDto.getStore())
                .device(storeStockMgmtDto.getDevice())
                .stockType(storeStockMgmtDto.getStockType())
                .stockTypeId(storeStockMgmtDto.getStockTypeId())
                .stockYn(storeStockMgmtDto.getStockYn())
                .prevStock(storeStockMgmtDto.getPrevStock())
                .nextStock(storeStockMgmtDto.getNextStock())
            .build();
    }
}