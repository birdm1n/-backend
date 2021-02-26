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
@Table(name = "comtn_author_info")
public class ComtnAuthorInfo {

    @Id
    @Column(name = "author_code")
    private String authorCode;

    @Column(name = "author_nm")
    private String authorNm;

    @Column(name = "author_dc")
    private String authorDc;

    @Column(name = "author_creat_de")
    private String authorCreatDe;


}
