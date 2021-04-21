package com.daema.rest.base.service;

import com.daema.commgmt.repository.*;
import com.daema.base.repository.CodeDetailRepository;
import com.daema.base.repository.MemberRepository;
import com.daema.rest.common.enums.StatusEnum;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.base.domain.CodeDetail;
import com.daema.commgmt.domain.FuncMgmt;
import com.daema.base.domain.Member;
import com.daema.commgmt.domain.Store;
import com.daema.rest.base.dto.CodeDetailDto;
import com.daema.rest.base.dto.RetrieveInitDataResponseDto;
import com.daema.rest.commgmt.dto.SaleStoreMgmtDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataHandleService {

    private final FuncMgmtRepository funcMgmtRepository;
    private final PubNotiRawDataRepository pubNotiRawDataRepository;
    private final StoreRepository storeRepository;
    private final CodeDetailRepository codeDetailRepository;
    private final MemberRepository memberRepository;

    private final RequestMappingHandlerMapping handlerMapping;
    private final AuthenticationUtil authenticationUtil;

    public DataHandleService(FuncMgmtRepository funcMgmtRepository, PubNotiRawDataRepository pubNotiRawDataRepository
                             ,StoreRepository storeRepository, CodeDetailRepository codeDetailRepository
                             ,MemberRepository memberRepository
            ,RequestMappingHandlerMapping handlerMapping, AuthenticationUtil authenticationUtil) {
        this.funcMgmtRepository = funcMgmtRepository;
        this.pubNotiRawDataRepository = pubNotiRawDataRepository;
        this.storeRepository = storeRepository;
        this.codeDetailRepository = codeDetailRepository;
        this.memberRepository = memberRepository;

        this.handlerMapping = handlerMapping;
        this.authenticationUtil = authenticationUtil;
    }

    @Transactional
    public void extractApiFunc() {

        if(authenticationUtil.isAdmin()) {

            Map<RequestMappingInfo, HandlerMethod> mappings = handlerMapping.getHandlerMethods();
            Set<RequestMappingInfo> keys = mappings.keySet();
            Iterator<RequestMappingInfo> iterator = keys.iterator();

            List<FuncMgmt> javaList = new ArrayList<FuncMgmt>();
            String[] nickname;
            HashMap<String, FuncMgmt> map = new HashMap<>();

            while (iterator.hasNext()) {
                RequestMappingInfo key = iterator.next();
                HandlerMethod method = mappings.get(key);

                if (method.hasMethodAnnotation(ApiOperation.class)) {
                    ApiOperation annotation = method.getMethodAnnotation(ApiOperation.class);

                    if (StringUtils.hasText(annotation.nickname())) {
                        nickname = annotation.nickname().split("\\|\\|");

                        FuncMgmt funcMgmt = FuncMgmt.builder()
                                .funcId(nickname[0].concat("_".concat(method.getMethod().getName())))
                                .groupId(Integer.parseInt(nickname[0]))
                                .groupName(nickname[1])
                                .title(annotation.value())
                                .role(nickname[2])
                                .urlPath(key.getPatternsCondition().getPatterns().iterator().next())
                                .orderNum(Integer.parseInt(nickname[3]))
                                .build();

                        javaList.add(funcMgmt);

                        map.put(nickname[0].concat("_".concat(method.getMethod().getName())), funcMgmt);
                    }
                }
            }

            List<FuncMgmt> dbList = funcMgmtRepository.findAll();

            List<FuncMgmt> deleteList = dbList.stream()
                    .filter(db -> !map.containsKey(db.getFuncId()))
                    .collect(Collectors.toList());

            funcMgmtRepository.saveAll(javaList);
            funcMgmtRepository.deleteAll(deleteList);
        }else{
            throw new AuthorizationServiceException("UnAuthorization User");
        }
    }

    @Transactional
    public void migrationSmartChoiceData(){
        if(authenticationUtil.isAdmin()
            && pubNotiRawDataRepository.existsByDeadLineYn(StatusEnum.FLAG_N.getStatusMsg())) {

            long memberSeq = authenticationUtil.getMemberSeq();

            pubNotiRawDataRepository.migrationSmartChoiceData(memberSeq);
        }else{
            throw new AuthorizationServiceException("UnAuthorization User");
        }
    }

    public RetrieveInitDataResponseDto retrieveInitData(ModelMap reqModel){

        RetrieveInitDataResponseDto responseDto = new RetrieveInitDataResponseDto();

        if(reqModel.containsKey("initData")
                && reqModel.get("initData") != null){

            List<String> initData = (List<String>) reqModel.get("initData");

            if(initData.contains("storeList")){
                List<Store> storeList = storeRepository.findByUseYnOrderByStoreName(StatusEnum.FLAG_Y.getStatusMsg());
                responseDto.setStoreList(storeList.stream().map(SaleStoreMgmtDto::ofInitData).collect(Collectors.toList()));
            }
        }

        if(reqModel.containsKey("code")
                && reqModel.get("code") != null){

                List<CodeDetail> codeDetailList = codeDetailRepository.findAllByCodeIdInAndUseYnOrderByCodeIdAscOrderNumAsc(
                        (List<String>) reqModel.get("code"), StatusEnum.FLAG_Y.getStatusMsg()
                );


                List<CodeDetailDto> codeDetailDtos = codeDetailList.stream().map(CodeDetailDto::ofInitData).collect(Collectors.toList());

                Map<String, List<CodeDetailDto>> detailMap = codeDetailDtos.stream()
                        .collect(Collectors.groupingBy(CodeDetailDto::getCodeId));

                responseDto.setCodeList(detailMap);
        }

        return responseDto;
    }

    public Object existsData(ModelMap reqModel){

        Object retVal = "";

        if(reqModel.get("storeName") != null){
            retVal = Optional.ofNullable(storeRepository.findByStoreName((String) reqModel.get("storeName")))
                    .orElseGet(Store::new).getStoreName();
        }else if(reqModel.get("userName") != null){
            retVal = Optional.ofNullable(memberRepository.findByUsername((String) reqModel.get("userName")))
                    .orElseGet(Member::new).getUsername();
        }else if(reqModel.get("bizNo") != null){
            retVal = Optional.ofNullable(storeRepository.findByBizNoAndUseYn((String) reqModel.get("bizNo"), StatusEnum.FLAG_Y.getStatusMsg()))
                    .orElseGet(Store::new).getBizNo();
        }else if(reqModel.get("storeMapBizNo") != null) {
            retVal = storeRepository.findByBizNoAndUseYn((String) reqModel.get("storeMapBizNo"), StatusEnum.FLAG_Y.getStatusMsg());
        }

        return retVal;
    }
}






















