package com.daema.core.scm.domain.joininfo;

import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.AdditionDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "addtionId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "addition", comment = "부가서비스")
public class Addition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addition_id", columnDefinition = "BIGINT UNSIGNED comment '부가서비스 아이디'")
    private Long additionId;

    @Column(name = "addition_category", columnDefinition = "varchar(255) comment '부가서비스 카테고리'")
    private ScmEnum.AdditionCategory additionCategory;

    @Column(name = "product_name", columnDefinition = "varchar(255) comment '상품명'")
    private String productName;

    @Column(name = "charge", columnDefinition = "INT comment '요금'")
    private int charge;

    @OneToMany(mappedBy = "addition")
    private List<JoinAddition> joinAdditionList = new ArrayList<>();

    public static AdditionDto from(Addition addition) {
        return AdditionDto.builder()
                .additionId(addition.getAdditionId())
                .additionCategory(addition.getAdditionCategory())
                .productName(addition.getProductName())
                .charge(addition.getCharge())
                /*.joinInfoId(addition.getJoinInfo().getJoinInfoId())*/
                .build();
    }

    public static Addition toEntity(AdditionDto additionDto) {
        return Addition.builder()
                .additionId(additionDto.getAdditionId())
                .additionCategory(additionDto.getAdditionCategory())
                .productName(additionDto.getProductName())
                .charge(additionDto.getCharge())
                /* .joinInfo(JoinInfo.builder()
                         .joinInfoId(additionDto.getJoinInfoId())
                         .build())*/
                .build();
    }
    public static List<Addition> toEntity(List<AdditionDto> additionDtos) {
        List<Addition> additionList = new ArrayList<>();
        for(AdditionDto additionDto : additionDtos){
            additionList.add(
                    Addition.builder()
                            .additionId(additionDto.getAdditionId())
                            .additionCategory(additionDto.getAdditionCategory())
                            .productName(additionDto.getProductName())
                            .charge(additionDto.getCharge())
                            /* .joinInfo(JoinInfo.builder()
                                     .joinInfoId(additionDto.getJoinInfoId())
                                     .build())*/
                            .build()
            );
        }
        return additionList;

    }
}
