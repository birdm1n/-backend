package com.daema.wms.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreStockCheckListDto {

    private LocalDateTime regiDateTime;

    private String name;

    private String orgName;
}
