package com.daema.base.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@org.hibernate.annotations.Table(appliesTo = "salt", comment = "암호키")
public class Salt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salt_id", columnDefinition = "BIGINT UNSIGNED comment '암호키 아이디'")
    private int id;

    @NotNull()
    @Column(name = "salt_data", columnDefinition = "VARCHAR(255) comment '암호키 데이터'")
    private String salt;

    public Salt() {
    }

    public Salt(String salt) {
        this.salt = salt;
    }
}
