package com.daema.domain;

import com.daema.domain.pk.OpenStoreUserMapPK;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@EqualsAndHashCode(of={"openStoreId", "userId"})
@IdClass(OpenStoreUserMapPK.class)
@ToString
@Entity
@Table(name="open_store_user_map")
@NoArgsConstructor
public class OpenStoreUserMap {

    @Id
    @Column(name = "open_store_id")
    private long openStoreId;

    @Id
    @Column(name = "user_id")
    private long userId;

    @Column(nullable = false, name = "use_yn", columnDefinition ="char(1)")
    private String useYn;

    @Builder
    public OpenStoreUserMap(long openStoreId, long userId){
        this.openStoreId = openStoreId;
        this.userId = userId;
    }

    @Builder
    public OpenStoreUserMap(long openStoreId, long userId, String useYn){
        this.openStoreId = openStoreId;
        this.userId = userId;
        this.useYn = useYn;
    }

    public void updateUseYn(OpenStoreUserMap openStoreUserMap, String useYn){
        openStoreUserMap.setUseYn(useYn);
    }
}