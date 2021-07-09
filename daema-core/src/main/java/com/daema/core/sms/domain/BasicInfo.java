package com.daema.core.sms.domain;

import com.daema.core.base.domain.Members;
import lombok.*;

import javax.persistence.*;


@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "basicInfoId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "basic_info", comment = "기본 정보")
public class BasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basic_info_id", columnDefinition = "BIGINT UNSIGNED comment '기본 정보'")
    private Long basicInfoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Members members;




    public static BasicInfo toEntity(Long membersId){
       return BasicInfo.builder()
                .members(Members.builder()
                        .seq(membersId)
                        .build())
                .build();

    }
}
