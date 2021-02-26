package com.daema.domain.comt;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode(of="author_code")
@ToString
@Entity
@Table(name = "comtn_author_role_relate")
public class ComtnAuthorRoleRelate {

    @Id
    @Column(name = "author_code")
    private String authorCode;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "creat_dt")
    private java.sql.Timestamp creatDt;


}
