package com.daema.core.scm.domain.payment;

import com.daema.core.scm.domain.vo.Address;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Giro{


    @Embedded
    private Address paymentAddress;


}
