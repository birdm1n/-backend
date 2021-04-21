package com.daema.commgmt.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PubNotiMappingDto {

    private long goodsId;

    private long chargeId;

    private int supportAmt;

    private int releaseAmt;

    private LocalDate releaseDate;

}
