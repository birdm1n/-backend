package com.daema.core.base.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "social_data", comment = "소셜 데이터")
public class SocialData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_data_id", columnDefinition = "BIGINT UNSIGNED comment '소셜 데이터 아이디'")
    private Long socialDataId;

    @Column(name = "social_data", columnDefinition = "varchar(255) comment '소셜 데이터'")
    private String socialData;

    @Column(name = "social_email", columnDefinition = "varchar(255) comment '소셜 이메일'")
    private String socialEmail;

    @Column(name = "social_type", columnDefinition = "varchar(255) comment '소셜 타입'")
    private String socialType;

    @OneToOne(mappedBy = "social")
    private Members member;

    public SocialData(String socialData, String socialEmail, String socialType) {
        this.socialData = socialData;
        this.socialEmail = socialEmail;
        this.socialType = socialType;
    }
}
