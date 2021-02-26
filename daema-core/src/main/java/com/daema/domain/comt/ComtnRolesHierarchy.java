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
@EqualsAndHashCode(of="parnts_role")
@ToString
@Entity
@Table(name = "comtn_roles_hierarchy")
public class ComtnRolesHierarchy {

    @Id
    @Column(name = "parnts_role")
    private String parntsRole;

    @Column(name = "chldrn_role")
    private String chldrnRole;


}
