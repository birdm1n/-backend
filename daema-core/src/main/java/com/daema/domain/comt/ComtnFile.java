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
@Table(name = "comtn_file")
public class ComtnFile {

    @Id
    @Column(name = "atch_file_id")
    private String atchFileId;

    @Column(name = "creat_dt")
    private java.sql.Timestamp creatDt;

    @Column(name = "use_at")
    private String useAt;


}
