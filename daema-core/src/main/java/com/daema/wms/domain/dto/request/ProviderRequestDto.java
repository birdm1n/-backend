package com.daema.wms.domain.dto.request;

import com.daema.base.dto.SearchParamDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderRequestDto extends SearchParamDto {

    private Long provId;
    private String provName;

}
