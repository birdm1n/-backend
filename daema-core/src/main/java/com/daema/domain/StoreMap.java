package com.daema.domain;

import com.daema.domain.pk.StoreMapPK;
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
    @Column(name = "store_id")
    private long storeId;

    @Id
    @Column(name = "parent_store_id")
    private long parentStoreId;

    @NotBlank
    @Column(name = "use_yn", columnDefinition = "char(1)")
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
