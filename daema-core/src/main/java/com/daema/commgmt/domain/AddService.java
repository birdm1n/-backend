package com.daema.commgmt.domain;

import com.daema.base.enums.TypeEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Table;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="addSvcId")
@ToString
@NoArgsConstructor
@Entity
@Table(appliesTo = "add_service", comment = "부가 서비스")
public class AddService extends AddServiceBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "add_svc_id", columnDefinition = "BIGINT UNSIGNED comment '부가 서비스 아이디'" )
    private long addSvcId;

    /**
     * 요청 승인(R) 구분
     * 코드 + pk : R123
     */
    @Column(name = "origin_key", columnDefinition = "varchar(255) comment '출처 키'")
    private String originKey;

    @Nullable
    @Column(name = "use_yn", columnDefinition ="char(1) comment '사용 여부'")
    @ColumnDefault("\"N\"")
    private String useYn;

    @Nullable
    @Column(name = "del_yn", columnDefinition ="char(1) comment '삭제 여부'")
    @ColumnDefault("\"N\"")
    private String delYn;

    @Builder
    public AddService(long addSvcId, String addSvcName, int addSvcCharge, Long telecom, TypeEnum.AddSvcType addSvcType
            , String originKey, LocalDateTime regiDateTime, String addSvcMemo
            , String useYn, String delYn, String telecomName){
        this.addSvcId = addSvcId;
        this.addSvcName = addSvcName;
        this.addSvcCharge = addSvcCharge;
        this.telecom = telecom;
        this.addSvcType = addSvcType;
        this.originKey = originKey;
        this.addSvcMemo = addSvcMemo;
        this.useYn = useYn;
        this.delYn = delYn;
        this.regiDateTime = regiDateTime;
        this.telecomName = telecomName;
    }

    public void updateUseYn(AddService addSvc, String useYn){
        addSvc.setUseYn(useYn);
    }

    public void updateDelYn(AddService addSvc, String delYn){
        addSvc.setDelYn(delYn);
    }
}
