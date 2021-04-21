package com.daema.commgmt.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrgnztListDto {

    private int depth;

    private long orgId;

    private long parentOrgId;

    private String orgName;

    private String hierarchy;

}
