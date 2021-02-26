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
@EqualsAndHashCode(of="progrm_file_nm")
@ToString
@Entity
@Table(name = "comtn_progrm_list")
public class ComtnProgrmList {

    @Id
    @Column(name = "progrm_file_nm")
    private String progrmFileNm;

    @Column(name = "progrm_stre_path")
    private String progrmStrePath;

    @Column(name = "progrm_korean_nm")
    private String progrmKoreanNm;

    @Column(name = "progrm_dc")
    private String progrmDc;

    @Column(name = "url")
    private String url;


}
