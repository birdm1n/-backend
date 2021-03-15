package com.daema.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="userId")
@ToString
@Entity
@Table(name="user2")
public class User2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @NotBlank
    @Column(length = 30, nullable = false, name = "email")
    private String email;

    @NotBlank
    @Column(length = 20, nullable = false, name = "user_pw")
    private String userPw;

    @NotBlank
    @Column(length = 20, nullable = false, name = "user_name")
    private String userName;

    @NotBlank
    @Column(length = 15, name = "user_phone")
    private String userPhone;

    @NotNull
    @Column(nullable = false, name = "store_id")
    private long storeId;

    @NotNull
    @Column(nullable = false, name = "orgnzt_id")
    private long orgnztId;

    /**
     * 1-신규(승인전), 2-정상(승인완료), 9-삭제
     */
    @NotBlank
    @Column(length = 1, nullable = false, name = "user_status", columnDefinition ="char")
    private String userStatus;

    @Column(name = "last_upd_datetime")
    private LocalDateTime lastUpdDateTime;

    @Builder
    public User2 (long userId, String email, String userPw, String userName, String userPhone,
                  long storeId, long orgnztId, String userStatus, LocalDateTime lastUpdDateTime){
        this.userId = userId;
        this.email = email;
        this.userPw = userPw;
        this.userName = userName;
        this.userPhone = userPhone;
        this.storeId = storeId;
        this.orgnztId = orgnztId;
        this.userStatus = userStatus;
        this.lastUpdDateTime = lastUpdDateTime;
    }
}
