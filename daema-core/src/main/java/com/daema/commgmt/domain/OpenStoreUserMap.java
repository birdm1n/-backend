package com.daema.commgmt.domain;

import com.daema.commgmt.domain.pk.OpenStoreUserMapPK;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@EqualsAndHashCode(of={"openingStoreId", "userId"})
@IdClass(OpenStoreUserMapPK.class)
@ToString
@Entity
@org.hibernate.annotations.Table(appliesTo = "open_store_user_map", comment = "개통 관리점 유저 맵핑")
@NoArgsConstructor
public class OpenStoreUserMap {

    @Id
    @Column(name = "opening_store_id", columnDefinition = "BIGINT unsigned comment '개통 관리점 아이디'")
    private long openingStoreId;

    @Id
    @Column(name = "user_id", columnDefinition = "BIGINT unsigned comment '유저 아이디'")
    private long userId;

    @Column(nullable = false, name = "use_yn", columnDefinition ="char(1) comment '사용 여부' ")
    private String useYn;

    @Builder
    public OpenStoreUserMap(long openStoreId, long userId){
        this.openingStoreId = openStoreId;
        this.userId = userId;
    }

    @Builder
    public OpenStoreUserMap(long openStoreId, long userId, String useYn){
        this.openingStoreId = openStoreId;
        this.userId = userId;
        this.useYn = useYn;
    }

    public void updateUseYn(OpenStoreUserMap openStoreUserMap, String useYn){
        openStoreUserMap.setUseYn(useYn);
    }
}