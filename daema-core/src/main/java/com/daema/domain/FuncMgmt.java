package com.daema.domain;

import lombok.*;

import javax.persistence.*;

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
    @Column(name = "func_id")
    private String funcId;

    @Column(name = "group_id")
    private int groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "title")
    private String title;

    @Column(name = "role")
    private String role;

    @Column(name = "url_path")
    private String urlPath;

    @Column(name = "order_num")
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
