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
@EqualsAndHashCode(of="emplyr_id")
@ToString
@Entity
@Table(name = "comth_emplyr_info_change_dtls")
public class ComthEmplyrInfoChangeDtls {

    @Id
    @Column(name = "emplyr_id")
    private String emplyrId;

    @Column(name = "change_de")
    private String changeDe;

    @Column(name = "orgnzt_id")
    private String orgnztId;

    @Column(name = "empl_no")
    private String emplNo;

    @Column(name = "sexdstn_code")
    private String sexdstnCode;

    @Column(name = "brthdy")
    private String brthdy;

    @Column(name = "fxnum")
    private String fxnum;

    @Column(name = "house_adres")
    private String houseAdres;

    @Column(name = "house_end_telno")
    private String houseEndTelno;

    @Column(name = "area_no")
    private String areaNo;

    @Column(name = "detail_adres")
    private String detailAdres;

    @Column(name = "zip")
    private String zip;

    @Column(name = "offm_telno")
    private String offmTelno;

    @Column(name = "mbtlnum")
    private String mbtlnum;

    @Column(name = "email_adres")
    private String emailAdres;

    @Column(name = "house_middle_telno")
    private String houseMiddleTelno;

    @Column(name = "pstinst_code")
    private String pstinstCode;

    @Column(name = "emplyr_sttus_code")
    private String emplyrSttusCode;

    @Column(name = "esntl_id")
    private String esntlId;


}
