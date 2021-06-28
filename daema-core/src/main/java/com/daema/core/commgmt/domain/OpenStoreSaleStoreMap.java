package com.daema.core.commgmt.domain;

import com.daema.core.commgmt.domain.pk.OpenStoreSaleStoreMapPK;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of={"openingStoreId", "saleStoreId"})
@IdClass(OpenStoreSaleStoreMapPK.class)
@ToString
@Entity
@org.hibernate.annotations.Table(appliesTo = "open_store_sale_store_map", comment = "개통 관리점 영업 관리점 맵핑")
@NoArgsConstructor
public class OpenStoreSaleStoreMap {

    @Id
    @Column(name = "opening_store_id", columnDefinition = "BIGINT unsigned comment '개통 관리점 아이디'")
    private long openingStoreId;

    @Id
    @Column(name = "sale_store_id", columnDefinition = "BIGINT unsigned comment '영업 관리점 아이디'")
    private long saleStoreId;

    @Column(nullable = false, name = "use_yn", columnDefinition ="char(1) comment '사용 여부'")
    private String useYn;

    @Builder
    public OpenStoreSaleStoreMap(long openingStoreId, long saleStoreId){
        this.openingStoreId = openingStoreId;
        this.saleStoreId = saleStoreId;
    }

    @Builder
    public OpenStoreSaleStoreMap(long openingStoreId, long saleStoreId, String useYn){
        this.openingStoreId = openingStoreId;
        this.saleStoreId = saleStoreId;
        this.useYn = useYn;
    }

    public void updateUseYn(OpenStoreSaleStoreMap openStoreSaleStoreMap, String useYn){
        openStoreSaleStoreMap.setUseYn(useYn);
    }
}