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
@EqualsAndHashCode(of="table_name")
@ToString
@Entity
@Table(name = "comte_cop_seq")
public class ComteCopSeq {

    @Id
    @Column(name = "table_name")
    private String tableName;

    @Column(name = "next_id")
    private double nextId;


}
