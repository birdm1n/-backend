package com.daema.dto;

import com.daema.domain.dto.PubNotiMappingDto;
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
