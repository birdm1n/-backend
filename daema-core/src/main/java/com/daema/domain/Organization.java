package com.daema.domain;

import com.daema.domain.dto.OrgnztListDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@SqlResultSetMapping(
        name="OrgnztList",
        classes = @ConstructorResult(
                targetClass = OrgnztListDto.class,
                columns = {
                        @ColumnResult(name="depth", type = Integer.class),
                        @ColumnResult(name="org_id", type = Long.class),
                        @ColumnResult(name="parent_org_id", type = Long.class),
                        @ColumnResult(name="org_name", type = String.class),
                        @ColumnResult(name="hierarchy", type = String.class)
                })
)

@Getter
@Setter
@EqualsAndHashCode(of="orgId")
@ToString
@NoArgsConstructor
@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "org_id")
    private long orgId;

    @Column(name = "parent_org_id")
    @ColumnDefault(value = "0")
    private long parentOrgId;

    @Column(name = "store_id")
    private long storeId;

    @NotBlank
    @Column(length = 20, nullable = false, name = "org_name")
    private String orgName;

    @Column(nullable = false, name = "del_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String delYn;

    @Builder
    public Organization(long orgId, long parentOrgId, String orgName, long storeId, String delYn) {
        this.orgId = orgId;
        this.parentOrgId = parentOrgId;
        this.orgName = orgName;
        this.storeId = storeId;
        this.delYn = delYn;
    }
}
