package com.daema.core.commgmt.domain;

import com.daema.core.commgmt.domain.pk.StoreMapPK;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(of={"storeId", "parentStoreId"})
@IdClass(StoreMapPK.class)
@Entity
@ToString
@org.hibernate.annotations.Table(appliesTo = "store_map", comment = "관리점 맵핑")
@NoArgsConstructor
public class StoreMap {

    @Id
    @Column(name = "store_id", columnDefinition = "BIGINT unsigned comment '관리점 아이디'")
    private long storeId;

    @Id
    @Column(name = "parent_store_id", columnDefinition = "BIGINT unsigned comment '상위 관리점 아이디'")
    private long parentStoreId;

    @NotBlank
    @Column(name = "use_yn", columnDefinition = "char(1) comment '사용 여부'")
    private String useYn;

    @Builder
    public StoreMap(long storeId, long parentStoreId, String useYn){
        this.storeId = storeId;
        this.parentStoreId = parentStoreId;
        this.useYn = useYn;
    }

    public void updateUseYn(StoreMap storeMap, String useYn){
        storeMap.setUseYn(useYn);
    }
}

