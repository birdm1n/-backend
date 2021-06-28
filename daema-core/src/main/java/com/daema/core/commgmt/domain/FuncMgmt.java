package com.daema.core.commgmt.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@EqualsAndHashCode(of="funcId")
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "func_mgmt", comment = "기능 관리")
public class FuncMgmt {

    /**
     * groupId + "_" + method name
     */
    @Id
    @Column(name = "func_name", columnDefinition = "varchar(255) comment '기능 이름'")
    private String funcId;

    @Column(name = "group_id", columnDefinition = "BIGINT unsigned comment '그룹 아이디'")
    private long groupId;

    @Column(name = "group_name", columnDefinition = "varchar(255) comment '그룹 이름'")
    private String groupName;

    @Column(name = "title", columnDefinition = "varchar(255) comment '제목'")
    private String title;

    @Column(name = "role", columnDefinition = "varchar(255) comment '역할'")
    private String role;

    @Column(name = "url_path", columnDefinition = "varchar(255) comment 'url 경로'")
    private String urlPath;

    @Column(name = "order_seq", columnDefinition = "INT comment '정렬 순번'")
    private int orderNum;

    @Builder
    public FuncMgmt(String funcId, int groupId, String groupName, String title, String role, String urlPath, int orderNum){
        this.funcId = funcId;
        this.groupId = groupId;
        this.groupName = groupName;
        this.title = title;
        this.role = role;
        this.urlPath = urlPath;
        this.orderNum = orderNum;
    }
}
