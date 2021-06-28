package com.daema.core.commgmt.dto.response;

import com.daema.core.commgmt.dto.ChargeMgmtDto;
import com.daema.core.commgmt.dto.GoodsMgmtDto;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class PubNotiMgmtResponseDto {

    private List<GoodsMgmtDto> goodsList;
    private List<ChargeMgmtDto> chargeList;
    private HashMap<String, PubNotiMappingDto> mappingData;

}
