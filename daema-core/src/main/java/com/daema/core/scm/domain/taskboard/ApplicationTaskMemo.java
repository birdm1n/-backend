package com.daema.core.scm.domain.taskboard;

import com.daema.core.base.domain.common.BaseUserInfoEntity;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.ApplicationTaskMemoDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "applTaskMemoId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "application_task_memo", comment = "업무 메모")
public class ApplicationTaskMemo extends BaseUserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appl_task_memo_id", columnDefinition = "BIGINT UNSIGNED comment '업무 메모 아이디'")
    private Long applTaskMemoId;


    @Enumerated(EnumType.STRING)
    @Column(name = "consult_state", columnDefinition = "varchar(255) comment '상담 상태'")
    private ScmEnum.TaskState.ConsultState consultState;

    @Enumerated(EnumType.STRING)
    @Column(name = "logis_state", columnDefinition = "varchar(255) comment '물류 상태'")
    private ScmEnum.TaskState.LogisState logisState;

    @Enumerated(EnumType.STRING)
    @Column(name = "opening_state", columnDefinition = "varchar(255) comment '개통 상태'")
    private ScmEnum.TaskState.OpeningState openingState;

    @Column(name = "reason", columnDefinition = "varchar(255) comment '사유'")
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appl_id")
    private ApplicationTaskBoard taskBoard;

    public static ApplicationTaskMemo create(ApplicationTaskMemoDto ApplicationTaskMemoDto) {

        return ApplicationTaskMemo.builder()
                .consultState(ApplicationTaskMemoDto.getConsultState())
                .logisState(ApplicationTaskMemoDto.getLogisState())
                .openingState(ApplicationTaskMemoDto.getOpeningState())
                .reason(ApplicationTaskMemoDto.getReason())
                .build();

    }

    public static ApplicationTaskMemoDto From(ApplicationTaskMemo applicationTaskMemo){
        return ApplicationTaskMemoDto.builder()
                .consultState(applicationTaskMemo.getConsultState())
                .logisState(applicationTaskMemo.getLogisState())
                .openingState(applicationTaskMemo.getOpeningState())
                .reason(applicationTaskMemo.getReason())
                .applTaskMemoId(applicationTaskMemo.getApplTaskMemoId())
                .build();
    }


}