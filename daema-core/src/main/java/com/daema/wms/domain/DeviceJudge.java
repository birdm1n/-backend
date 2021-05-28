package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "dvcId")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "device_judge")
public class DeviceJudge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dvc_judge_id" , columnDefinition = "BIGINT unsigned comment '기기 판정 아이디'")
    private Long dvcJudgeId;

    @Column(name = "judge_memo", columnDefinition = "varchar(255) comment '판정 메모'")
    private String judgeMemo;

    @Enumerated(EnumType.STRING)
    @Column(name = "judge_status", columnDefinition = "varchar(255) comment '판정 상태'")
    private WmsEnum.JudgementStatus judgeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id" , columnDefinition = "BIGINT unsigned comment '기기 아이디'")
    private Device device;

    public void updateDelYn(DeviceJudge deviceJudge, String delYn){
        deviceJudge.setDelYn(delYn);
    }
}