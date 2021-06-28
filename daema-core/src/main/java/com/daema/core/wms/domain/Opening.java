package com.daema.core.wms.domain;

import com.daema.core.base.domain.common.BaseEntity;
import com.daema.core.base.enums.StatusEnum;
import com.daema.core.commgmt.domain.Store;
import com.daema.core.wms.domain.enums.WmsEnum;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@AuditOverride(forClass = BaseEntity.class)
@Audited
@Builder
@Getter
@Setter
@EqualsAndHashCode(of="opening_id")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "opening", comment = "개통")
public class Opening extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opening_id", columnDefinition = "BIGINT unsigned comment '개통 아이디'")
    private Long openingId;

    /* OPENING("개통") - 철회 가능, CANCEL("철회") - 철회 완료, COMPL("완료") - 철회불가 */
    @Enumerated(EnumType.STRING)
    @Column(name = "opening_status", columnDefinition = "varchar(255) comment '개통 상태'")
    private WmsEnum.OpeningStatus openingStatus;

    @Column(name = "opening_date", columnDefinition = "DATE comment '개통 일자'")
    private LocalDate openingDate;

    @Column(name = "opening_memo", columnDefinition = "varchar(255) comment '개통 메모'")
    private String openingMemo;

    @Column(name = "cancel_date", columnDefinition = "DATE comment '철회 일자'")
    private LocalDate cancelDate;

    @Column(name = "cancel_memo", columnDefinition = "varchar(255) comment '철회 메모'")
    private String cancelMemo;

    @Column(name = "batch_compl_date", columnDefinition = "DATE comment '배치 완료 일자'")
    private LocalDate batchComplDate;

    @Audited(targetAuditMode = NOT_AUDITED) // 해당 테이블까지 이력을 추적하지 않겠다는 설정 필수
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", columnDefinition = "BIGINT unsigned comment '관리점 아이디'")
    private Store store;

    @Audited(targetAuditMode = NOT_AUDITED) // 해당 테이블까지 이력을 추적하지 않겠다는 설정 필수
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id", columnDefinition = "BIGINT unsigned comment '기기 아이디'")
    private Device device;


    public void insertOpening(Store store, Device device, LocalDate openingDate, String openingMemo ){
        this.store = store;
        this.device = device;
        this.openingDate = openingDate;
        this.openingMemo = openingMemo;
        this.openingStatus = WmsEnum.OpeningStatus.OPENING;
        super.setDelYn(StatusEnum.FLAG_N.getStatusMsg());
    }

    public void updateCancel(LocalDate cancelDate, String cancelMemo ){
        this.cancelDate = cancelDate;
        this.cancelMemo = cancelMemo;
        this.openingStatus = WmsEnum.OpeningStatus.CANCEL;
        super.setDelYn(StatusEnum.FLAG_Y.getStatusMsg());
    }

}
