package com.daema.rest.commgmt.service;

import com.daema.core.base.enums.StatusEnum;
import com.daema.core.base.enums.UserRole;
import com.daema.core.commgmt.domain.FuncMgmt;
import com.daema.core.commgmt.domain.FuncRoleMap;
import com.daema.core.commgmt.domain.RoleMgmt;
import com.daema.core.commgmt.repository.FuncMgmtRepository;
import com.daema.core.commgmt.repository.FuncRoleMapRepository;
import com.daema.core.commgmt.repository.RoleMgmtRepository;
import com.daema.core.commgmt.dto.FuncRoleMgmtDto;
import com.daema.core.commgmt.dto.response.FuncRoleResponseDto;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
                        .storeId(authenticationUtil.getTargetStoreId(roleMgmtDto.getStoreId()))
                        .regiDateTime(LocalDateTime.now())
                    .build()
        );
    }

    @Transactional
    public void updateRole(FuncRoleMgmtDto.RoleMgmtDto roleMgmtDto) {

        RoleMgmt roleMgmt = roleMgmtRepository.findById(roleMgmtDto.getRoleId()).orElse(null);
        long storeId = authenticationUtil.getTargetStoreId(roleMgmtDto.getStoreId());

        if (roleMgmt != null
            && roleMgmt.getStoreId() == storeId) {

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

            Long roleId = Long.parseLong(reqModelMap.get("delRoleId") + "");
            RoleMgmt roleMgmt = roleMgmtRepository.findById(roleId).orElse(null);

            long storeId = authenticationUtil.getTargetStoreId(Long.parseLong(String.valueOf(reqModelMap.getAttribute("storeId"))));

            if (roleMgmt != null
                    && roleMgmt.getStoreId() == storeId) {
                roleMgmt.updateDelYn(roleMgmt, StatusEnum.FLAG_Y.getStatusMsg());
            } else {
                throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }
        }else{
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }

    public FuncRoleResponseDto getFuncRoleMapInfo(long storeId) {

        long targetStoreId = authenticationUtil.getTargetStoreId(storeId);

        List<Order> orders = new ArrayList<>();
        orders.add(Order.asc("groupId"));
        orders.add(Order.asc("orderNum"));

        String role = UserRole.ROLE_USER.name();

        for(UserRole userRole : UserRole.values()){
            if(authenticationUtil.hasRole(userRole.name())){
                role = userRole.name();
                break;
            }
        }

        //기능 전체 목록
        List<FuncMgmt> funcList = funcMgmtRepository.findAllByRoleContainingOrderByGroupIdAscRoleAscOrderNumAsc(role);

        //역할 전체 목록
        List<RoleMgmt> roleList = getRoleList(targetStoreId);

        //기능, 역할 맵핑 목록
        List<FuncRoleMap> mapList = funcRoleMapRepository.getMappingList(targetStoreId);

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
                                    , mapList.contains(new FuncRoleMap(funcMgmt.getFuncId(), roleMgmt.getRoleId(), targetStoreId)) ? StatusEnum.FLAG_Y.getStatusMsg() : StatusEnum.FLAG_N.getStatusMsg() }
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

    @Transactional(propagation = Propagation.REQUIRED)
    public void setFuncRoleMapInfo (List<ModelMap> reqModelMap, Long storeId){

        if (CommonUtil.isNotEmptyList(reqModelMap)) {
            List<FuncRoleMap> saveMaps = new ArrayList<>();
            List<FuncRoleMap> deleteMaps = new ArrayList<>();

            for(ModelMap reqMap : reqModelMap){
                try {
                    if (StatusEnum.FLAG_Y.getStatusMsg().equals(String.valueOf(reqMap.get("mapYn")))) {
                        setCDList(saveMaps, reqMap, storeId);
                    } else {
                        setCDList(deleteMaps, reqMap, storeId);
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

    private void setCDList(List<FuncRoleMap> list, ModelMap reqMap, Long storeId){
        list.add(
                new FuncRoleMap(
                        String.valueOf(reqMap.get("funcId"))
                        ,Integer.parseInt(reqMap.get("roleId") + "")
                        ,storeId
                )
        );
    }

    public List<String> getMemberEnableUrlPathList(long memberSeq, long storeId){
        return funcMgmtRepository.getMemberEnableUrlPathList(memberSeq, storeId);
    }
}






















