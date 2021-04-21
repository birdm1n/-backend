package com.daema.rest.commgmt.dto.response;

import com.daema.rest.commgmt.dto.ChargeMgmtDto;
import com.daema.rest.commgmt.dto.GoodsMgmtDto;
import com.daema.commgmt.domain.dto.response.PubNotiMappingDto;
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
