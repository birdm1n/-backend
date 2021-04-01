package com.daema.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="chargeId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="add_service")
public class AddService extends AddServiceBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "add_svc_id")
    private long addSvcId;

    /**
     * 요청 승인(R) 구분
     * 코드 + pk : R123
     */
    @Column(name = "origin_key", length = 8)
    private String originKey;

    @Nullable
    @Column(name = "use_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String useYn;

    @Nullable
    @Column(name = "del_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String delYn;

    @Builder
    public AddService(long addSvcId, String addSvcName, int addSvcCharge, int telecom
            , String originKey, LocalDateTime regiDateTime, String addSvcMemo, String useYn, String delYn){
        this.addSvcId = addSvcId;
        this.addSvcName = addSvcName;
        this.addSvcCharge = addSvcCharge;
        this.telecom = telecom;
        this.originKey = originKey;
        this.addSvcMemo = addSvcMemo;
        this.useYn = useYn;
        this.delYn = delYn;
        this.regiDateTime = regiDateTime;
    }

    public void updateUseYn(AddService addSvc, String useYn){
        addSvc.setUseYn(useYn);
    }

    public void updateDelYn(AddService addSvc, String delYn){
        addSvc.setDelYn(delYn);
    }
}
