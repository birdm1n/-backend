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
@EqualsAndHashCode(of="role_code")
@ToString
@Entity
@Table(name = "comtn_role_info")
public class ComtnRoleInfo {

    @Id
    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "role_nm")
    private String roleNm;

    @Column(name = "role_pttrn")
    private String rolePttrn;

    @Column(name = "role_dc")
    private String roleDc;

    @Column(name = "role_ty")
    private String roleTy;

    @Column(name = "role_sort")
    private String roleSort;

    @Column(name = "role_creat_de")
    private String roleCreatDe;


}
