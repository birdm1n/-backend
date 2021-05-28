package com.daema.commgmt.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode(of="funcId")
@ToString
@NoArgsConstructor
@Entity
@Table(name = "func_mgmt")
public class FuncMgmt {

    /**
     * groupId + "_" + method name
     */
    @Id
    @Column(name = "func_id", columnDefinition = "varchar(255) comment '기능 아이디'")
    private String funcId;

    @Column(name = "group_id", columnDefinition = "INT comment '그룹 아이디'")
    private int groupId;

    @Column(name = "group_name", columnDefinition = "varchar(255) comment '그룹 명'")
    private String groupName;

    @Column(name = "title", columnDefinition = "varchar(255) comment '제목'")
    private String title;

    @Column(name = "role", columnDefinition = "varchar(255) comment '룰'")
    private String role;

    @Column(name = "url_path", columnDefinition = "varchar(255) comment 'url 경로'")
    private String urlPath;

    @Column(name = "order_num", columnDefinition = "INT comment '순서 넘버'")
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
