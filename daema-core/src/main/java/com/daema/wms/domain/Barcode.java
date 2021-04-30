package com.daema.wms.domain;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="barcode")
public class Barcode  {

    @Id
    @Column(name = "full_barcode")
    private String fullBarcode;

    @Column(name = "common_barcode")
    private String commonBarcode;

}