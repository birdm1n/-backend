package com.daema.rest.wms.service;

import com.daema.base.repository.CodeDetailRepository;
import com.daema.commgmt.repository.GoodsOptionRepository;
import com.daema.commgmt.repository.GoodsRepository;
import com.daema.commgmt.repository.PubNotiRepository;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.wms.domain.*;
import com.daema.wms.domain.dto.response.*;
import com.daema.wms.domain.enums.WmsEnum;
import com.daema.wms.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoveStockMgmtService {

    private final InStockRepository inStockRepository;
    private final DeviceRepository deviceRepository;
    private final PubNotiRepository pubNotiRepository;
    private final StockRepository stockRepository;
    private final GoodsRepository goodsRepository;
    private final GoodsOptionRepository goodsOptionRepository;
    private final InStockWaitRepository inStockWaitRepository;
    private final MoveStockRepository moveStockRepository;
    private final DeviceStatusRepository deviceStatusRepository;
    private final CodeDetailRepository codeDetailRepository;
    private final StoreStockRepository storeStockRepository;
    private final AuthenticationUtil authenticationUtil;

    public MoveStockMgmtService(InStockRepository inStockRepository, DeviceRepository deviceRepository, PubNotiRepository pubNotiRepository, StockRepository stockRepository, GoodsRepository goodsRepository, GoodsOptionRepository goodsOptionRepository, InStockWaitRepository inStockWaitRepository, MoveStockRepository moveStockRepository, DeviceStatusRepository deviceStatusRepository, CodeDetailRepository codeDetailRepository, StoreStockRepository storeStockRepository, AuthenticationUtil authenticationUtil) {
        this.inStockRepository = inStockRepository;
        this.deviceRepository = deviceRepository;
        this.pubNotiRepository = pubNotiRepository;
        this.stockRepository = stockRepository;
        this.goodsRepository = goodsRepository;
        this.goodsOptionRepository = goodsOptionRepository;
        this.inStockWaitRepository = inStockWaitRepository;
        this.moveStockRepository = moveStockRepository;
        this.deviceStatusRepository = deviceStatusRepository;
        this.codeDetailRepository = codeDetailRepository;
        this.storeStockRepository = storeStockRepository;
        this.authenticationUtil = authenticationUtil;
    }

    @Transactional(readOnly = true)
    public ResponseDto<MoveStockResponseDto> getMoveAndTrnsList(WmsEnum.MovePathType movePathType) {
        long sotreId = authenticationUtil.getStoreId();
        // 이동 프로세스
        if(WmsEnum.MovePathType.SELL_MOVE == movePathType || WmsEnum.MovePathType.STOCK_MOVE == movePathType) {
            Page<MoveStockResponseDto> resultPageDto = moveStockRepository.getMoveTypeList(movePathType);
        }
        
        // 이관 프로세스
        if(WmsEnum.MovePathType.SELL_TRNS == movePathType ||
                WmsEnum.MovePathType.STOCK_TRNS == movePathType||
                WmsEnum.MovePathType.FAULTY_TRNS == movePathType){
            Page<MoveStockResponseDto> resultPageDto = moveStockRepository.getTransTypeList(movePathType);

        }

        return null;
    }
}