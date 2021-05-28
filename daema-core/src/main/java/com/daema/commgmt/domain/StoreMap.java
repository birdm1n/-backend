package com.daema.commgmt.domain;

import com.daema.commgmt.domain.pk.StoreMapPK;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(of={"storeId", "parentStoreId"})
@IdClass(StoreMapPK.class)
@ToString
@Entity
@Table(name="store_map")
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
