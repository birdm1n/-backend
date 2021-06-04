package com.daema.base.domain.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;


/*
1. 이력이 쌓일 때 이력의 값을 REV라는 컬럼으로 저장하는데 이 값이 default: int 타입이다.
2. 이 경우 약 20억 이상의 이력이 쌓이면 곧바로 오류가 발생하게 된다.
3. REVINFO 테이블의 REV 컬럼과, 각 이력 테이블의 REV 컬럼을 모두 Long type 즉 BIGINT로 변경해야만 한다.
4. @RevisionEntity를 커스텀으로 새로 만들어야 한다.
*/

@Getter
@Setter
@NoArgsConstructor
@Entity
@RevisionEntity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@org.hibernate.annotations.Table(appliesTo = "rev_info", comment = "리비전 정보")
public class RevInfo implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @RevisionNumber
  @EqualsAndHashCode.Include
  @Column(name = "rev_id")
  private Long revId;

  @RevisionTimestamp
  @EqualsAndHashCode.Include
  @Column(name = "rev_timestamp")
  private Long timestamp;


  @Transient
  public Date getRevisionDate() {
    return new Date(timestamp);
  }

  @Override
  public String toString() {
    return String.format("LongRevisionEntity(id = %d, revisionDate = %s)",
            revId, DateFormat.getDateTimeInstance().format(getRevisionDate()));
  }
}