package com.daema.core.sms.domain;

import com.daema.core.sms.dto.JoinAdditionDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Setter
@EqualsAndHashCode(of="joinAdditionId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JoinAddition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_addtion_id", columnDefinition = "BIGINT UNSIGNED comment '가입 부가서비스 아이디'")
    private Long joinAdditionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "join_info_id", columnDefinition = "BIGINT UNSIGNED comment '가입 아이디'")
    private JoinInfo joinInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addition_id", columnDefinition = "BIGINT UNSIGNED comment '부가서비스 아이디'")
    private Addition addition;

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_form_id")
    private AppForm appForm;*/



    /*public JoinAddition create() {
        JoinAddition joinAddition = JoinAddition.builder()
                .joinInfo(JoinInfo.builder()
                        .joinInfoId(joinInfoRepository.save(joinInfo).getJoinInfoId())
                        .build())
                .addition(Addition.builder()
                        .additionId(appFormReqDto.getJoinInfoDto().getAdditionId())
                        .build())
                .build();
        return joinAddition;
    }*/

    public static JoinAddition buildEntity(JoinAdditionDto joinAdditionDto){
        return JoinAddition.builder()
                .addition(Addition.builder()
                        .additionId(joinAdditionDto.getAdditionId())
                        .build())
                .joinInfo(JoinInfo.builder()
                        .joinInfoId(joinAdditionDto.getJoinInfoId())
                        .build())
                .build();
    }

    public static List<JoinAddition> toEntityList(List<JoinAdditionDto> joinAdditionDtoList) {
        List<JoinAddition> joinAdditionList = new ArrayList<>();
        for (JoinAdditionDto joinAdditionDto : joinAdditionDtoList) {
            joinAdditionList.add(
                    JoinAddition.builder()
                            .joinAdditionId(joinAdditionDto.getJoinAdditionId())
                            .joinInfo(JoinInfo.builder()
                                    .joinInfoId(joinAdditionDto.getJoinInfoId())
                                    .build())
                            .addition(Addition.builder()
                                    .additionId(joinAdditionDto.getAdditionId())
                                    .build())
                            .build()
            );
        }
        return joinAdditionList;

    }
}

