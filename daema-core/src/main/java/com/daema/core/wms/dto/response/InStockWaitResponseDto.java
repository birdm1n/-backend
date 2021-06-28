package com.daema.core.wms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InStockWaitResponseDto {

    private List<InStockWaitDto> inStockWaitDtoList;
    private List<InStockWaitGroupDto> inStockWaitGroupDtoList;

}
