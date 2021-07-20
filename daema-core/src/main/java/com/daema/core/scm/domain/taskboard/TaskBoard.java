package com.daema.core.scm.domain.taskboard;

import com.daema.core.scm.dto.TaskBoardDto;
import com.daema.core.scm.dto.TaskMemoDto;
import com.daema.core.scm.dto.request.MemoReqDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "taskBoardId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "task_board", comment = "업무 보드")
public class TaskBoard {

    @Id
    @Column(name = "task_board_id", columnDefinition = "BIGINT UNSIGNED comment '업무 보드 아이디'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskBoardId;

    @Column(name = "just_check")
    private String checkYN;

    @OneToMany(mappedBy = "taskBoard", cascade = CascadeType.ALL)
    private List<TaskMemo> taskMemos = new ArrayList<>();


    public static TaskBoard build(TaskBoardDto taskBoardDto) {
        return TaskBoard.builder()
                .taskBoardId(taskBoardDto.getTaskBoardId())
                .build();
    }

    public static TaskBoard create(TaskBoardDto taskBoardDto) {

        TaskBoard taskBoard = TaskBoard.build(taskBoardDto);

        List<TaskMemo> taskMemoList = new ArrayList<>();

        for (TaskMemoDto taskMemoDto : taskBoardDto.getTaskMemoDtoList()) {
            TaskMemo taskMemo = TaskMemo.create(taskMemoDto);
            taskMemo.setTaskBoard(taskBoard);
            taskMemoList.add(taskMemo);
        }
        taskBoard.setTaskMemos(taskMemoList);
        return taskBoard;
    }
    public static void update(TaskBoard taskBoard, MemoReqDto memoReqDto){
        /*Long taskBoardId = taskBoard.getTaskBoardId();*/
        taskBoard.getTaskMemos().add(TaskMemo.builder()
                .taskBoard(TaskBoard.builder()
                        .taskBoardId(taskBoard.getTaskBoardId()).build())
                .consultState(memoReqDto.getConsultState())
                .openingState(memoReqDto.getOpeningState())
                .logisState(memoReqDto.getLogisState())
                .reason(memoReqDto.getReason())
                .build());

    }

    public static void update(TaskBoard taskBoard, TaskBoardDto taskBoardDto) {
        Long taskUpdateBoardId = taskBoard.getTaskBoardId();
        List<TaskMemo> taskMemoList = taskBoard.getTaskMemos();
        List<TaskMemoDto> taskMemoDtoList = taskBoardDto.getTaskMemoDtoList();

        for (TaskMemoDto taskMemoDto : taskMemoDtoList) {
            taskMemoList.add(TaskMemo.builder()
                    .taskBoard(TaskBoard.builder()
                            .taskBoardId(taskUpdateBoardId).build())
                    .consultState(taskMemoDto.getConsultState())
                    .openingState(taskMemoDto.getOpeningState())
                    .logisState(taskMemoDto.getLogisState())
                    .reason(taskMemoDto.getReason())
                    .build());
        }

        taskBoard.setTaskMemos(taskMemoList);
    }

    public static TaskBoardDto From(TaskBoard taskBoard) {


        return TaskBoardDto.builder()
                .taskBoardId(taskBoard.getTaskBoardId())
                .taskMemoDtoList(ListFrom(taskBoard))
                .build();
    }

    public static List<TaskMemoDto> ListFrom(TaskBoard taskBoard) {
        List<TaskMemoDto> list = new ArrayList<>();
        for (TaskMemo taskMemo : taskBoard.getTaskMemos()) {
            list.add(TaskMemo.From(taskMemo));
        }
        return list;
    }

}

