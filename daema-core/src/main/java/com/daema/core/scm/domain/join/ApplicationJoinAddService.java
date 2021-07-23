package com.daema.core.scm.domain.join;

import com.daema.core.scm.dto.ApplicationAddServiceDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "applJoinAddSvcId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "application_join_add_service", comment = " 가입 부가서비스")
public class ApplicationJoinAddService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appl_join_add_service_id", columnDefinition = "BIGINT UNSIGNED comment '가입 부가서비스 아이디'")
    private Long applJoinAddSvcId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appl_id", columnDefinition = "BIGINT UNSIGNED comment '가입 아이디'")
    private ApplicationJoin applicationJoin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appl_add_svc_id", columnDefinition = "BIGINT UNSIGNED comment '부가서비스 아이디'")
    private ApplicationAddService applicationAddService;

    public static ApplicationJoinAddService create(ApplicationJoin applicationJoin, ApplicationAddServiceDto applicationAddServiceDto) {
        return ApplicationJoinAddService.builder()
                .applicationJoin(applicationJoin)
                .applicationAddService(ApplicationAddService.builder()
                        .applAddSvcId((applicationAddServiceDto.getApplAddSvcId())
                        )
                        .build())
                .build();
    }

    public static List<ApplicationJoinAddService> createList(ApplicationJoin applicationJoin, List<ApplicationAddServiceDto> applicationAddServiceDtoList) {
        List<ApplicationJoinAddService> applicationJoinAddServiceList = new ArrayList<>();

        for (ApplicationAddServiceDto applicationAddServiceDto : applicationAddServiceDtoList) {
            applicationJoinAddServiceList.add(create(applicationJoin, applicationAddServiceDto));
        }
        return applicationJoinAddServiceList;

    }
}

