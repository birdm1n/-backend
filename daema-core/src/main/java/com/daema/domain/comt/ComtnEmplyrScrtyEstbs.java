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
@EqualsAndHashCode(of="scrty_dtrmn_trget_id")
@ToString
@Entity
@Table(name = "comtn_emplyr_scrty_estbs")
public class ComtnEmplyrScrtyEstbs {

    @Id
    @Column(name = "scrty_dtrmn_trget_id")
    private String scrtyDtrmnTrgetId;

    @Column(name = "mber_ty_code")
    private String mberTyCode;

    @Column(name = "author_code")
    private String authorCode;


}
