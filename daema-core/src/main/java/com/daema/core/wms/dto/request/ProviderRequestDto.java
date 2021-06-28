package com.daema.core.wms.dto.request;

import com.daema.core.base.dto.SearchParamDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderRequestDto extends SearchParamDto {

    private Long provId;
    private String provName;

}
