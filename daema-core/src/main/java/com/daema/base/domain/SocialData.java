package com.daema.base.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class SocialData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String socialId;
    private String email;
    private String type;

    @Column( columnDefinition = "BIGINT UNSIGNED comment '이름2'")
    private Long unsignedTest;

    @OneToOne(mappedBy = "social")
    private Member member;

    public SocialData(String socialId, String email, String type) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
        this.type = type;
    }
}
