package com.daema.core.scm.domain.taskboard;

import com.daema.core.base.domain.common.BaseUserInfoEntity;
import com.daema.core.scm.domain.application.Application;
import com.daema.core.scm.dto.InsertMemoReqDto;
import com.daema.core.scm.dto.ApplicationTaskBoardDto;
import com.daema.core.scm.dto.ApplicationTaskMemoDto;
import com.daema.core.scm.dto.request.MemoReqDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "applId")
@ToString(exclude = {"application"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "application_task_board", comment = "업무 보드")
public class ApplicationTaskBoard extends BaseUserInfoEntity {

    @Id
    private Long applId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appl_id", columnDefinition = "BIGINT UNSIGNED comment '신청서 아이디'")
    @MapsId
    private Application application;

    @Column(name = "just_check")
    private String checkYN;

    @OneToMany(mappedBy = "taskBoard", cascade = CascadeType.ALL)
    private List<ApplicationTaskMemo> taskMemoList = new ArrayList<>();

    @OneToMany(mappedBy = "taskBoard", cascade = CascadeType.ALL)
    private List<ApplicationMemo> memoList = new ArrayList<>();

    public static ApplicationTaskBoard create(Application application, ApplicationTaskBoardDto applicationTaskBoardDto) {

        ApplicationTaskBoard taskBoard = ApplicationTaskBoard.builder()
                .application(application)
                .build();

        List<ApplicationTaskMemo> applicationTaskMemoList = new ArrayList<>();

        for (ApplicationTaskMemoDto ApplicationTaskMemoDto : applicationTaskBoardDto.getApplicationTaskMemoDtoList()) {
            ApplicationTaskMemo applicationTaskMemo = ApplicationTaskMemo.create(ApplicationTaskMemoDto);
            applicationTaskMemo.setTaskBoard(taskBoard);
            applicationTaskMemoList.add(applicationTaskMemo);
        }
        taskBoard.setTaskMemoList(applicationTaskMemoList);
        return taskBoard;
    }
    public static void updateTaskMemos(ApplicationTaskBoard applicationTaskBoard, MemoReqDto memoReqDto){
        /*Long taskBoardId = taskBoard.getTaskBoardId();*/
        applicationTaskBoard.getTaskMemoList().add(ApplicationTaskMemo.builder()
                .taskBoard(ApplicationTaskBoard.builder()
                        .applId(applicationTaskBoard.getApplId()).build())
                .consultState(memoReqDto.getConsultState())
                .openingState(memoReqDto.getOpeningState())
                .logisState(memoReqDto.getLogisState())
                .reason(memoReqDto.getReason())
                .build());

    }



    public static void updateTaskMemos(ApplicationTaskBoard applicationTaskBoard, ApplicationTaskBoardDto applicationTaskBoardDto) {
        Long taskUpdateBoardId = applicationTaskBoard.getApplId();
        List<ApplicationTaskMemo> applicationTaskMemoList = applicationTaskBoard.getTaskMemoList();
        List<ApplicationTaskMemoDto> applicationTaskMemoDtoList = applicationTaskBoardDto.getApplicationTaskMemoDtoList();

        for (ApplicationTaskMemoDto ApplicationTaskMemoDto : applicationTaskMemoDtoList) {
            applicationTaskMemoList.add(ApplicationTaskMemo.builder()
                    .taskBoard(ApplicationTaskBoard.builder()
                            .applId(taskUpdateBoardId).build())
                    .consultState(ApplicationTaskMemoDto.getConsultState())
                    .openingState(ApplicationTaskMemoDto.getOpeningState())
                    .logisState(ApplicationTaskMemoDto.getLogisState())
                    .reason(ApplicationTaskMemoDto.getReason())
                    .build());
        }

        applicationTaskBoard.setTaskMemoList(applicationTaskMemoList);
    }

    public static ApplicationTaskBoardDto From(ApplicationTaskBoard applicationTaskBoard) {


        return ApplicationTaskBoardDto.builder()
                .applId(applicationTaskBoard.getApplId())
                .applicationTaskMemoDtoList(ListFrom(applicationTaskBoard))
                .build();
    }

    public static List<ApplicationTaskMemoDto> ListFrom(ApplicationTaskBoard applicationTaskBoard) {
        List<ApplicationTaskMemoDto> list = new ArrayList<>();
        for (ApplicationTaskMemo applicationTaskMemo : applicationTaskBoard.getTaskMemoList()) {
            list.add(ApplicationTaskMemo.From(applicationTaskMemo));
        }
        return list;
    }


    public static void updateMemo(ApplicationTaskBoard applicationTaskBoard, InsertMemoReqDto memo){
        /*Long taskBoardId = taskBoard.getTaskBoardId();*/
        applicationTaskBoard.getMemoList().add(ApplicationMemo.builder()
                .memoContents(memo.getMemoContents())
                .category(memo.getCategory())
                .build());

    }
}

