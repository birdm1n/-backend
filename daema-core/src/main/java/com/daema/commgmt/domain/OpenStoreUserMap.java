package com.daema.commgmt.domain;

import com.daema.commgmt.domain.pk.OpenStoreUserMapPK;
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
    @Column(name = "open_store_id", columnDefinition = "BIGINT unsigned comment '오픈 관리점 아이디'")
    private long openStoreId;

    @Id
    @Column(name = "user_id", columnDefinition = "BIGINT unsigned comment '유저 아이디'")
    private long userId;

    @Column(nullable = false, name = "use_yn", columnDefinition ="char(1) comment '사용 여부' ")
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