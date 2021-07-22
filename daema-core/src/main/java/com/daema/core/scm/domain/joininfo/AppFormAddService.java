package com.daema.core.scm.domain.joininfo;

import com.daema.core.commgmt.domain.AddServiceBase;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.AppFormAddServiceDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "appFormAddServiceId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "app_form_add_service", comment = "부가서비스")
public class AppFormAddService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_form_add_service_id", columnDefinition = "BIGINT UNSIGNED comment '부가서비스 아이디'")
    private Long appFormAddServiceId;

    @Column(name = "app_form_add_service_category", columnDefinition = "varchar(255) comment '부가서비스 카테고리'")
    private ScmEnum.AppFormAddServiceCategory appFormAddServiceCategory;

    @Column(name = "product_name", columnDefinition = "varchar(255) comment '상품명'")
    private String productName;

    @Column(name = "charge", columnDefinition = "INT comment '요금'")
    private int charge;

    @OneToMany(mappedBy = "appFormAddService")
    private List<JoinAddService> joinAddServiceList = new ArrayList<>();



}
