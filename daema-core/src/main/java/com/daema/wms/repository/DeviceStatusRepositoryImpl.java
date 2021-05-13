package com.daema.wms.repository;

import com.daema.wms.domain.DeviceStatus;
import com.daema.wms.domain.dto.response.DeviceStatusListDto;
import com.daema.wms.repository.custom.CustomDeviceStatusRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.daema.wms.domain.QDeviceStatus.deviceStatus;

public class DeviceStatusRepositoryImpl extends QuerydslRepositorySupport implements CustomDeviceStatusRepository {

    public DeviceStatusRepositoryImpl() {
        super(DeviceStatus.class);
    }

    @Override
    public List<DeviceStatusListDto> getLastDeviceStatusInfo(List<Long> dvcIds) {

        JPQLQuery<Long> query = getQuerydsl().createQuery();

        //기기별 최대값 가져옴
        query.select(deviceStatus.dvcStatusId.max().as("dvcStatusId"))
                .from(deviceStatus)
                .where(deviceStatus.device.dvcId.in(dvcIds))
                .groupBy(deviceStatus.device.dvcId);

        List<Long> dvcStatusIds = query.fetch();

        if (dvcStatusIds != null
                && dvcStatusIds.size() > 0) {
            //최대값 id 로 재조회
            return getDeviceStatusInfo(dvcStatusIds);
        } else {
            return null;
        }
    }

    public List<DeviceStatusListDto> getDeviceStatusInfo(List<Long> dvcStatusIds) {

        JPQLQuery<DeviceStatusListDto> query = getQuerydsl().createQuery();

        query.select(Projections.fields(
                DeviceStatusListDto.class
                ,deviceStatus.dvcStatusId.as("dvcStatusId")
                ,deviceStatus.device.dvcId.as("dvcId")
                ,deviceStatus.productFaultyYn.as("productFaultyYn")
                ,deviceStatus.extrrStatus.as("extrrStatus")
                ,deviceStatus.productMissYn.as("productMissYn")
                ,deviceStatus.missProduct.as("missProduct")
                ,deviceStatus.ddctAmt.as("ddctAmt")
                ,deviceStatus.addDdctAmt.as("addDdctAmt")
                ,deviceStatus.ddctReleaseAmtYn.as("ddctReleaseAmtYn")
                ,deviceStatus.inStockStatus.as("inStockStatus")
        ))
                .from(deviceStatus)
                .where(
                        deviceStatus.dvcStatusId.in(dvcStatusIds)
                );

        return query.fetch();
    }
}
























