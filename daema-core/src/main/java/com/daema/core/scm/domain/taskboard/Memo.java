package com.daema.core.scm.domain.taskboard;


import com.daema.core.base.domain.common.BaseUserInfoEntity;
import com.daema.core.commgmt.domain.Organization;
import com.daema.core.commgmt.domain.Store;
import com.daema.core.scm.domain.appform.AppForm;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.InsertMemoReqDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of="memoId")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "memo", comment = "신청서 메모")
public class Memo extends BaseUserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id", columnDefinition = "BIGINT UNSIGNED comment '메모 아이디'")
    private Long memoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition = "varchar(255) comment '카테고리'")
    private ScmEnum.MemoCategory category;

    @Column(name = "memo_contents", columnDefinition = "varchar(255) comment '메모 내용'")
    private String memoContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_board_id", columnDefinition = "BIGINT unsigned comment '신청서 아이디'")
    private TaskBoard taskBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", columnDefinition = "BIGINT unsigned comment '관리점 아이디'")
    private Store store;
/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", columnDefinition = "BIGINT unsigned comment '조직 아이디'")
    private Organization organization;*/

    public void create(InsertMemoReqDto reqDto) {
        this.category = reqDto.getCategory();
        this.memoContents = reqDto.getMemoContents();
        this.taskBoard = reqDto.getTaskBoard();
        this.store = reqDto.getStore();/*
        this.organization = reqDto.getOrganization();*/
    }
}

