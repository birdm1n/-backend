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
@EqualsAndHashCode(of="orgnzt_id")
@ToString
@Entity
@Table(name = "comtn_orgnzt_info")
public class ComtnOrgnztInfo {

    @Id
    @Column(name = "orgnzt_id")
    private String orgnztId;

    @Column(name = "orgnzt_nm")
    private String orgnztNm;

    @Column(name = "orgnzt_dc")
    private String orgnztDc;


}
