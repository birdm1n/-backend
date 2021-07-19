package com.daema.core.scm.domain.taskupdateboard;

import com.daema.core.scm.domain.joininfo.CallingPlan;
import com.daema.core.scm.dto.CallingPlanDto;
import com.daema.core.scm.dto.TaskUpdateBoardDto;
import com.daema.core.scm.dto.TaskUpdateCommentDto;
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
    @Column(name = "task_update_board_Id", columnDefinition = "BIGINT UNSIGNED comment '업무 수정 보드 아이디'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskUpdateBoardId;

    @Column(name = "just_check")
    private String checkYN;

    @OneToMany(mappedBy = "taskUpdateBoard", cascade = CascadeType.ALL)
    private List<TaskUpdateComment> taskUpdateComments = new ArrayList<>();



    public static TaskUpdateBoard build(TaskUpdateBoardDto taskUpdateBoardDto){
        return TaskUpdateBoard.builder()
                .checkYN(taskUpdateBoardDto.getCheckYN())
                .build();
    }

    public static TaskUpdateBoard create(TaskUpdateBoardDto taskUpdateBoardDto) {

        // create할때 board dto를 받고. boarddto안에는 commentdtolist 가 있음.
        // comment dto list를 이용해서 모두 comment로 빌더한후
        // commentlist에 넣어서 board속성의 commentlist에 넣으면 됌.

        TaskUpdateBoard taskUpdateBoard = TaskUpdateBoard.build(taskUpdateBoardDto);

        List<TaskUpdateComment> taskUpdateCommentList = new ArrayList<>();

        for(TaskUpdateCommentDto taskUpdateCommentDto : taskUpdateBoardDto.getTaskUpdateCommentDtoList())
        {
            TaskUpdateComment taskUpdateComment = TaskUpdateComment.create(taskUpdateCommentDto);
            taskUpdateComment.setTaskUpdateBoard(taskUpdateBoard);
            taskUpdateCommentList.add(taskUpdateComment);
        }
       taskUpdateBoard.setTaskUpdateComments(taskUpdateCommentList);
        return taskUpdateBoard;
    }

    public static void update(TaskUpdateBoard taskUpdateBoard, TaskUpdateBoardDto taskUpdateBoardDto) {
        Long taskUpdateBoardId = taskUpdateBoard.getTaskUpdateBoardId();
        List<TaskUpdateComment> taskUpdateCommentList = taskUpdateBoard.getTaskUpdateComments();
        List<TaskUpdateCommentDto> taskUpdateCommentDtoList = taskUpdateBoardDto.getTaskUpdateCommentDtoList();

        for(TaskUpdateCommentDto taskUpdateCommentDto : taskUpdateCommentDtoList) {
            taskUpdateCommentList.add(TaskUpdateComment.builder()
                    .taskUpdateBoard(TaskUpdateBoard.builder()
                            .taskUpdateBoardId(taskUpdateBoardId).build())
                    .consultState(taskUpdateCommentDto.getConsultState())
                    .openingState(taskUpdateCommentDto.getOpeningState())
                    .logisState(taskUpdateCommentDto.getLogisState())
                    .reason(taskUpdateCommentDto.getReason())
                    .build());
        }

        taskUpdateBoard.setTaskUpdateComments(taskUpdateCommentList);
    }

    public static TaskUpdateBoardDto From(TaskUpdateBoard taskUpdateBoard) {


        return TaskUpdateBoardDto.builder()
                .taskUpdateBoardId(taskUpdateBoard.getTaskUpdateBoardId())
                .taskUpdateCommentDtoList(ListFrom(taskUpdateBoard))
                .build();
    }

    public static List<TaskUpdateCommentDto> ListFrom(TaskUpdateBoard taskUpdateBoard){
        List<TaskUpdateCommentDto> list = new ArrayList<>();
        for( TaskUpdateComment taskUpdateComment : taskUpdateBoard.getTaskUpdateComments())
        {
         list.add(TaskUpdateComment.From(taskUpdateComment));
        }
        return list;
    }

    public static CallingPlanDto from(CallingPlan callingPlan) {
        return CallingPlanDto.builder()
                .callingPlanId(callingPlan.getCallingPlanId())
                .name(callingPlan.getName())
                .build();
    }
}

