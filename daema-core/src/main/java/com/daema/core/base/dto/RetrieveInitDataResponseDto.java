package com.daema.core.base.dto;

import com.daema.core.commgmt.dto.SaleStoreMgmtDto;
import com.daema.core.wms.dto.ProviderMgmtDto;
import com.daema.core.wms.dto.response.SelectStockDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveInitDataResponseDto {

    private List<ProviderMgmtDto> provList;
    private List<SaleStoreMgmtDto> storeList;
    private Map<String, List<CodeDetailDto>> codeList;
    private List<SelectStockDto> stockList;

}
