package com.daema.core.scm.domain.join;

import com.daema.core.scm.domain.enums.ScmEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "applAddSvcId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "application_add_service", comment = "부가서비스")
public class ApplicationAddService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appl_add_svc_id", columnDefinition = "BIGINT UNSIGNED comment '부가서비스 아이디'")
    private Long applAddSvcId;

    @Column(name = "appl_add_svc_category", columnDefinition = "varchar(255) comment '신청서 부가서비스 카테고리'")
    private ScmEnum.ApplicationAddSvcCategory applicationAddSvcCategory;

    @Column(name = "product_name", columnDefinition = "varchar(255) comment '상품명'")
    private String productName;

    @Column(name = "charge", columnDefinition = "INT comment '요금'")
    private int charge;

    @OneToMany(mappedBy = "applicationAddService")
    private List<ApplicationJoinAddService> applicationJoinAddServiceList = new ArrayList<>();



}
