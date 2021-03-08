package com.daema.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(of="userNo")
@ToString
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    @NotBlank
    @Column(length = 50, nullable = false, name = "user_id")
    private String userId;

    @NotBlank
    @Column(length = 200, nullable = false, name = "user_pw")
    private String userPw;

    @NotBlank
    @Column(length = 100, nullable = false, name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;
}
