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
@EqualsAndHashCode(of="atch_file_id")
@ToString
@Entity
@Table(name = "comtn_file_detail")
public class ComtnFileDetail {

    @Id
    @Column(name = "atch_file_id")
    private String atchFileId;

    @Column(name = "file_sn")
    private double fileSn;

    @Column(name = "file_stre_cours")
    private String fileStreCours;

    @Column(name = "stre_file_nm")
    private String streFileNm;

    @Column(name = "orignl_file_nm")
    private String orignlFileNm;

    @Column(name = "file_extsn")
    private String fileExtsn;

    @Column(name = "file_cn")
    private String fileCn;

    @Column(name = "file_size")
    private double fileSize;


}
