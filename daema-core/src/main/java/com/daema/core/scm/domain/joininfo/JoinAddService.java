package com.daema.core.scm.domain.joininfo;

import com.daema.core.scm.dto.AppFormAddServiceDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "joinAddServiceId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "join_add_service", comment = " 가입 부가서비스")
public class JoinAddService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_add_service_id", columnDefinition = "BIGINT UNSIGNED comment '가입 부가서비스 아이디'")
    private Long joinAddServiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "join_info_id", columnDefinition = "BIGINT UNSIGNED comment '가입 아이디'")
    private JoinInfo joinInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_form_add_service_id", columnDefinition = "BIGINT UNSIGNED comment '부가서비스 아이디'")
    private AppFormAddService appFormAddService;

    public static JoinAddService create(JoinInfo joinInfo, AppFormAddServiceDto appFormAddServiceDto) {
        return JoinAddService.builder()
                .joinInfo(joinInfo)
                .appFormAddService(AppFormAddService.builder()
                        .appFormAddServiceId((appFormAddServiceDto.getAppFormAddServiceId())
                        )
                        .build())
                .build();
    }

    public static List<JoinAddService> createList(JoinInfo joinInfo, List<AppFormAddServiceDto> appFormAddServiceDtoList) {
        List<JoinAddService> joinAddServiceList = new ArrayList<>();

        for (AppFormAddServiceDto appFormAddServiceDto : appFormAddServiceDtoList) {
            joinAddServiceList.add(create(joinInfo, appFormAddServiceDto));
        }
        return joinAddServiceList;

    }
}

