package com.daema.core.scm.domain.taskboard;

import com.daema.core.base.domain.common.BaseUserInfoEntity;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.TaskMemoDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "taskMemoId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "task_memo", comment = "업무 메모")
public class TaskMemo extends BaseUserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_memo_id", columnDefinition = "BIGINT UNSIGNED comment '업무 메모 아이디'")
    private Long taskMemoId;


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
    @JoinColumn(name = "task_board_id")
    private TaskBoard taskBoard;

    public static TaskMemo create(TaskMemoDto taskMemoDto) {

        return TaskMemo.builder()
                .consultState(taskMemoDto.getConsultState())
                .logisState(taskMemoDto.getLogisState())
                .openingState(taskMemoDto.getOpeningState())
                .reason(taskMemoDto.getReason())
                .build();

    }

    public static TaskMemoDto From(TaskMemo taskMemo){
        return TaskMemoDto.builder()
                .consultState(taskMemo.getConsultState())
                .logisState(taskMemo.getLogisState())
                .openingState(taskMemo.getOpeningState())
                .reason(taskMemo.getReason())
                .taskMemoId(taskMemo.getTaskMemoId())
                .build();
    }


}