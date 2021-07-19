package com.daema.core.scm.domain.taskupdateboard;

import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.TaskUpdateBoardDto;
import com.daema.core.scm.dto.TaskUpdateCommentDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "taskUpdateCommentId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "task_update_comment", comment = "업무수정 코멘트")
public class TaskUpdateComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_update_comment_id", columnDefinition = "BIGINT UNSIGNED comment '업무 수정 아이디'")
    private Long taskUpdateCommentId;


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
    @JoinColumn(name = "task_update_board_id")
    private TaskUpdateBoard taskUpdateBoard;

    public static TaskUpdateComment create(TaskUpdateCommentDto taskUpdateCommentDto) {

        return TaskUpdateComment.builder()
                .consultState(taskUpdateCommentDto.getConsultState())
                .logisState(taskUpdateCommentDto.getLogisState())
                .openingState(taskUpdateCommentDto.getOpeningState())
                .reason(taskUpdateCommentDto.getReason())
              /*  .taskUpdateBoard(TaskUpdateBoard.builder()
                        .taskUpdateBoardId(taskUpdateCommentDto.getTaskUpdateBoardId()).build())*/
                .build();

    }

    public static TaskUpdateCommentDto From(TaskUpdateComment taskUpdateComment){
        return TaskUpdateCommentDto.builder()
                .consultState(taskUpdateComment.getConsultState())
                .logisState(taskUpdateComment.getLogisState())
                .openingState(taskUpdateComment.getOpeningState())
                .reason(taskUpdateComment.getReason())
                .taskUpdateCommentId(taskUpdateComment.getTaskUpdateCommentId())
                .build();
    }


}