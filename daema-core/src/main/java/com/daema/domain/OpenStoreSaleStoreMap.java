package com.daema.domain;

import com.daema.domain.pk.OpenStoreSaleStoreMapPK;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@EqualsAndHashCode(of={"openStoreId", "saleStoreId"})
@IdClass(OpenStoreSaleStoreMapPK.class)
@ToString
@Entity
@Table(name="open_store_sale_store_map")
@NoArgsConstructor
public class OpenStoreSaleStoreMap {

    @Id
    @Column(name = "open_store_id")
    private long openStoreId;

    @Id
    @Column(name = "sale_store_id")
    private long saleStoreId;

    @Column(nullable = false, name = "use_yn", columnDefinition ="char(1)")
    private String useYn;

    @Builder
    public OpenStoreSaleStoreMap(long openStoreId, long saleStoreId){
        this.openStoreId = openStoreId;
        this.saleStoreId = saleStoreId;
    }

    @Builder
    public OpenStoreSaleStoreMap(long openStoreId, long saleStoreId, String useYn){
        this.openStoreId = openStoreId;
        this.saleStoreId = saleStoreId;
        this.useYn = useYn;
    }

    public void updateUseYn(OpenStoreSaleStoreMap openStoreSaleStoreMap, String useYn){
        openStoreSaleStoreMap.setUseYn(useYn);
    }
}