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
@EqualsAndHashCode(of="menu_nm")
@ToString
@Entity
@Table(name = "comtn_menu_info")
public class ComtnMenuInfo {

    @Id
    @Column(name = "menu_nm")
    private String menuNm;

    @Column(name = "progrm_file_nm")
    private String progrmFileNm;

    @Column(name = "menu_no")
    private double menuNo;

    @Column(name = "upper_menu_no")
    private double upperMenuNo;

    @Column(name = "menu_ordr")
    private double menuOrdr;

    @Column(name = "menu_dc")
    private String menuDc;

    @Column(name = "relate_image_path")
    private String relateImagePath;

    @Column(name = "relate_image_nm")
    private String relateImageNm;


}
