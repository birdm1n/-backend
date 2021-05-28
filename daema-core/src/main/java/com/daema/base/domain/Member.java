package com.daema.base.domain;

import com.daema.commgmt.domain.dto.response.OrgnztMemberListDto;
import com.daema.base.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@SqlResultSetMapping(
        name="OrgnztMemberList",
        classes = @ConstructorResult(
                targetClass = OrgnztMemberListDto.class,
                columns = {
                        @ColumnResult(name="seq", type = Long.class),
                        @ColumnResult(name="username", type = String.class),
                        @ColumnResult(name="name", type = String.class),
                        @ColumnResult(name="email", type = String.class),
                        @ColumnResult(name="phone", type = String.class),
                        @ColumnResult(name="phone1", type = String.class),
                        @ColumnResult(name="phone2", type = String.class),
                        @ColumnResult(name="phone3", type = String.class),
                        @ColumnResult(name="user_status", type = String.class),
                        @ColumnResult(name="org_id", type = Long.class),
                        @ColumnResult(name="org_name", type = String.class),
                        @ColumnResult(name="member_hierarchy", type = String.class)
                })
)

@Entity
@Table(name = "Members")
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    //계정명. 로그인ID
    @Column(unique = true)
    @NotNull
    private String username;
    @NotNull
    private String password;
    //사용자명
    @NotBlank
    private String name;

    private String email;

    private String address;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "phone1", length = 4)
    private String phone1;

    @Column(name = "phone2", length = 4)
    private String phone2;

    @Column(name = "phone3", length = 4)
    private String phone3;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="social_id")
    private SocialData social;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_NOT_PERMITTED;

    private LocalDateTime regiDatetime;

    private LocalDateTime updDatetime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salt_id")
    private Salt salt;

    @NotNull
    private long storeId;
    @NotNull
    private long orgId;
    /**
     * 1-신규(미승인), 6-정상(승인), 9-삭제
     */
    @NotBlank
    @Column(nullable = false, name = "user_status", columnDefinition ="char(1)")
    @ColumnDefault("1")
    private String userStatus;

    public Member() {
    }

    public Member(@NotBlank String username, @NotBlank String password, @NotBlank String name, @NotBlank String email, @NotBlank String address, @NotNull String phone, @NotNull String phone1, @NotNull String phone2, @NotNull String phone3, @NotNull long storeId, @NotNull long orgId, @NotBlank String userStatus) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
        this.storeId = storeId;
        this.orgId = orgId;
        this.userStatus = userStatus;
    }

    @Builder
    public Member(long seq, String username, String password, String name, String email, String address
            , String phone, String phone1, String phone2, String phone3
            , LocalDateTime regiDatetime, LocalDateTime updDatetime, long storeId, long orgId, String userStatus, UserRole role){

        this.seq = seq;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
        this.regiDatetime = regiDatetime;
        this.updDatetime = updDatetime;
        this.storeId = storeId;
        this.orgId = orgId;
        this.userStatus = userStatus;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "seq=" + seq +
                ", id='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                ", regiDatetime=" + regiDatetime +
                ", updDatetime=" + updDatetime +
                ", storeId=" + storeId +
                ", orgId=" + orgId +
                ", userStatus=" + userStatus +
                '}';
    }

    public void updateUserStatus(Member member, String userStatus){
        member.setUserStatus(userStatus);
        member.setUpdDatetime(LocalDateTime.now());
    }

    public void updateOrgnztId(Member member, long orgnztId){
        member.setOrgId(orgnztId);
        member.setUpdDatetime(LocalDateTime.now());
    }
}


