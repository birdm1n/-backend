package com.daema.core.sms.domain;

import com.daema.core.sms.dto.CallingPlanDto;
import com.daema.core.sms.dto.TaskUpdateBoardDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "taskUpdateBoardId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "task_update_board", comment = "업무수정 보드")
public class TaskUpdateBoard {

    @Id
    @Column(name ="task_update_board_Id", columnDefinition = "BIGINT UNSIGNED comment '업무 수정 보드 아이디'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskUpdateBoardId;

    @OneToMany(mappedBy = "taskUpdateBoard", cascade =  CascadeType.ALL)
    private List<TaskUpdateComment> taskUpdateComments = new ArrayList<>();

    public static TaskUpdateBoard toEntity(){

        return new TaskUpdateBoard();
    }

    public static TaskUpdateBoardDto From(TaskUpdateBoard taskUpdateBoard){
        return TaskUpdateBoardDto.builder()
                .taskUpdateBoardId(taskUpdateBoard.getTaskUpdateBoardId())/*
                .taskUpdateCommentIds(taskUpdateBoard.getTaskUpdateComments()*/
                .build();
    }

    public static CallingPlanDto from(CallingPlan callingPlan) {
        return CallingPlanDto.builder()
                .callingPlanId(callingPlan.getCallingPlanId())
                .name(callingPlan.getName())
                .build();
    }
}

