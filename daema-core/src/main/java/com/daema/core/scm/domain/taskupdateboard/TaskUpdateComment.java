package com.daema.core.scm.domain.taskupdateboard;

import com.daema.core.scm.domain.enums.ScmEnum;
import lombok.*;

import javax.persistence.*;

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
    @Column(name = "task_state", columnDefinition = "varchar(255) comment '업무 상태'")
    ScmEnum.TaskState taskState;

    @Column(name = "reason", columnDefinition = "varchar(255) comment '사유'")
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_update_board_id")
    private TaskUpdateBoard taskUpdateBoard;



}
