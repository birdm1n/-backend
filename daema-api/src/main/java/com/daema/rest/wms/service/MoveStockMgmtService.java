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


    public ResponseDto<MoveStockResponseDto> getMoveAndTrnsList(WmsEnum.MovePathType movePathType) {

        return null;
    }
}