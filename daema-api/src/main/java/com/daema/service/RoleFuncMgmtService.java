package com.daema.service;

import com.daema.common.enums.StatusEnum;
import com.daema.common.util.AuthenticationUtil;
import com.daema.common.util.CommonUtil;
import com.daema.domain.FuncMgmt;
import com.daema.domain.FuncRoleMap;
import com.daema.domain.RoleMgmt;
import com.daema.dto.FuncRoleMgmtDto;
import com.daema.dto.FuncRoleResponseDto;
import com.daema.repository.FuncMgmtRepository;
import com.daema.repository.FuncRoleMapRepository;
import com.daema.repository.RoleMgmtRepository;
import com.daema.response.enums.ServiceReturnMsgEnum;
import com.daema.response.exception.DataNotFoundException;
import com.daema.response.exception.ProcessErrorException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleFuncMgmtService {

    private final FuncMgmtRepository funcMgmtRepository;
    private final RoleMgmtRepository roleMgmtRepository;
    private final FuncRoleMapRepository funcRoleMapRepository;

    private final AuthenticationUtil authenticationUtil;

    public RoleFuncMgmtService(FuncMgmtRepository funcMgmtRepository, RoleMgmtRepository roleMgmtRepository, FuncRoleMapRepository funcRoleMapRepository, AuthenticationUtil authenticationUtil) {
        this.funcMgmtRepository = funcMgmtRepository;
        this.roleMgmtRepository = roleMgmtRepository;
        this.funcRoleMapRepository = funcRoleMapRepository;
        this.authenticationUtil = authenticationUtil;
    }

    public void insertRole(FuncRoleMgmtDto.RoleMgmtDto roleMgmtDto) {
        //TODO ifnull return 함수 추가
        roleMgmtRepository.save(
                RoleMgmt.builder()
                        .roleName(roleMgmtDto.getRoleName())
                        .necessaryYn(roleMgmtDto.getNecessaryYn())
                        .delYn(StatusEnum.FLAG_N.getStatusMsg())
                        .storeId(roleMgmtDto.getStoreId())
                        .regiDateTime(LocalDateTime.now())
                    .build()
        );
    }

    @Transactional
    public void updateRole(FuncRoleMgmtDto.RoleMgmtDto roleMgmtDto) {

        RoleMgmt roleMgmt = roleMgmtRepository.findById(roleMgmtDto.getRoleId()).orElse(null);

        if (roleMgmt != null) {

            roleMgmt.setRoleName(roleMgmtDto.getRoleName());

            if (StringUtils.hasText(roleMgmtDto.getNecessaryYn())) {
                roleMgmt.setNecessaryYn(roleMgmtDto.getNecessaryYn());
            }

            if (StringUtils.hasText(roleMgmtDto.getDelYn())) {
                roleMgmt.setDelYn(roleMgmtDto.getDelYn());
            }
        } else {
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    @Transactional
    public void deleteRole(ModelMap reqModelMap) {

        if (reqModelMap.get("delRoleId") != null) {

            Integer roleId = Integer.parseInt(reqModelMap.get("delRoleId") + "");
            RoleMgmt roleMgmt = roleMgmtRepository.findById(roleId).orElse(null);

            if(roleMgmt != null) {
                roleMgmt.updateDelYn(roleMgmt, StatusEnum.FLAG_Y.getStatusMsg());
            } else {
                throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }
        }else{
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }

    public FuncRoleResponseDto getFuncRoleMapInfo(long storeId) {

        List<Order> orders = new ArrayList<>();
        orders.add(Order.asc("groupId"));
        orders.add(Order.asc("orderNum"));

        //기능 전체 목록
        List<FuncMgmt> funcList = funcMgmtRepository.findAll(Sort.by(orders));

        //역할 전체 목록
        List<RoleMgmt> roleList = getRoleList(storeId);

        //기능, 역할 맵핑 목록
        List<FuncRoleMap> mapList = funcRoleMapRepository.getMappingList(storeId);

        //맵핑 데이터 없는 func 에 사용
        List<String[]> emptyRoleList = new ArrayList<>();
        for (RoleMgmt roleMgmt : roleList) {
            emptyRoleList.add(new String[]{ String.valueOf(roleMgmt.getRoleId()), StatusEnum.FLAG_N.getStatusMsg() });
        }

        //response 데이터 세팅
        FuncRoleResponseDto responseDto = new FuncRoleResponseDto();

        responseDto.roleList = roleList.stream()
                .map(FuncRoleMgmtDto.RoleMgmtDto::from)
                .collect(Collectors.toList());

        List<String[]> filterRoleInfos;

        for (FuncMgmt funcMgmt : funcList) {

            filterRoleInfos = new ArrayList<>();

            if (mapList.stream().anyMatch(map -> map.getFuncId().equals(funcMgmt.getFuncId()))) {
                for (RoleMgmt roleMgmt : roleList) {
                    filterRoleInfos.add(
                            new String[]{String.valueOf(roleMgmt.getRoleId())
                                    , mapList.contains(new FuncRoleMap(funcMgmt.getFuncId(), roleMgmt.getRoleId())) ? StatusEnum.FLAG_Y.getStatusMsg() : StatusEnum.FLAG_N.getStatusMsg() }
                    );
                }
            } else {
                filterRoleInfos = emptyRoleList;
            }

            responseDto.funcRoleList.add(new FuncRoleResponseDto.FuncRoleMapDto(funcMgmt, filterRoleInfos));
        }

        return responseDto;
    }

    /**
     * 관리점_영업점별 역할 가져오기. 필수 역할은 기본으로 가져옴
     * @param storeId
     * @return
     */
    public List<RoleMgmt> getRoleList(long storeId){
        return roleMgmtRepository.getRoleList(storeId);
    }

    @Transactional
    public void setFuncRoleMapInfo (List<ModelMap> reqModelMap){

        if (CommonUtil.isNotEmptyList(reqModelMap)) {
            List<FuncRoleMap> saveMaps = new ArrayList<>();
            List<FuncRoleMap> deleteMaps = new ArrayList<>();

            for(ModelMap reqMap : reqModelMap){
                try {
                    if (StatusEnum.FLAG_Y.getStatusMsg().equals(String.valueOf(reqMap.get("mapYn")))) {
                        setCDList(saveMaps, reqMap);
                    } else {
                        setCDList(deleteMaps, reqMap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(CommonUtil.isNotEmptyList(saveMaps)){
                funcRoleMapRepository.saveAll(saveMaps);
            }

            if(CommonUtil.isNotEmptyList(deleteMaps)){
                funcRoleMapRepository.deleteAll(deleteMaps);
            }
        } else {
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }

    private void setCDList(List<FuncRoleMap> list, ModelMap reqMap){
        list.add(
                new FuncRoleMap(
                        String.valueOf(reqMap.get("funcId"))
                        ,Integer.parseInt(reqMap.get("roleId") + "")
                )
        );
    }
}






















