package com.daema.rest.base.service;

import com.daema.base.domain.CodeDetail;
import com.daema.base.domain.Member;
import com.daema.base.enums.UserRole;
import com.daema.base.repository.CodeDetailRepository;
import com.daema.base.repository.MemberRepository;
import com.daema.commgmt.domain.FuncMgmt;
import com.daema.commgmt.domain.Store;
import com.daema.commgmt.repository.FuncMgmtRepository;
import com.daema.commgmt.repository.PubNotiRawDataRepository;
import com.daema.commgmt.repository.StoreRepository;
import com.daema.rest.base.dto.CodeDetailDto;
import com.daema.rest.base.dto.RetrieveInitDataResponseDto;
import com.daema.rest.commgmt.dto.SaleStoreMgmtDto;
import com.daema.rest.common.Constants;
import com.daema.rest.common.enums.StatusEnum;
import com.daema.rest.common.enums.TypeEnum;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.JwtUtil;
import com.daema.rest.common.util.RedisUtil;
import com.daema.rest.wms.dto.ProviderMgmtDto;
import com.daema.wms.domain.Provider;
import com.daema.wms.repository.ProviderRepository;
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
    private final ProviderRepository providerRepository;

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final RequestMappingHandlerMapping handlerMapping;
    private final AuthenticationUtil authenticationUtil;

    //10년
    private final static long JOIN_URL_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 24 * 365 * 10;

    public DataHandleService(FuncMgmtRepository funcMgmtRepository, PubNotiRawDataRepository pubNotiRawDataRepository
                             ,StoreRepository storeRepository, CodeDetailRepository codeDetailRepository
                             ,MemberRepository memberRepository
                             ,ProviderRepository providerRepository
                             ,JwtUtil jwtUtil, RedisUtil redisUtil
            ,RequestMappingHandlerMapping handlerMapping, AuthenticationUtil authenticationUtil) {
        this.funcMgmtRepository = funcMgmtRepository;
        this.pubNotiRawDataRepository = pubNotiRawDataRepository;
        this.storeRepository = storeRepository;
        this.codeDetailRepository = codeDetailRepository;
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;

        this.handlerMapping = handlerMapping;
        this.authenticationUtil = authenticationUtil;
        this.providerRepository = providerRepository;
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
                        redisUtil.setData(funcMgmt.getUrlPath(), funcMgmt.getRole());
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

        if(!authenticationUtil.hasRole(UserRole.ROLE_ANONYMOUS.name())) {

            if (reqModel.containsKey("initData")
                    && reqModel.get("initData") != null) {

                List<String> initData = (List<String>) reqModel.get("initData");

                if (initData.contains("storeList")) {
                    responseDto.setStoreList(retrieveStoreList());
                }
                if (initData.contains("provList")) {
                    responseDto.setProvList(retrieveProvList());
                }
            }

            if (reqModel.containsKey("code")
                    && reqModel.get("code") != null) {

                responseDto.setCodeList(retrieveCodeList(reqModel));
            }
        }else{
            throw new AuthorizationServiceException("UnAuthorization User");
        }

        return responseDto;
    }

    private List<SaleStoreMgmtDto> retrieveStoreList(){

        List<Store> storeList = storeRepository.findByUseYnOrderByStoreName(StatusEnum.FLAG_Y.getStatusMsg());
        return storeList.stream().map(SaleStoreMgmtDto::ofInitData).collect(Collectors.toList());
    }
    private List<ProviderMgmtDto> retrieveProvList(){
        long storeId = authenticationUtil.getStoreId();
        List<Provider> provList = providerRepository.findByStoreIdAndUseYnOrderByProvName(storeId, StatusEnum.FLAG_Y.getStatusMsg());
        return provList.stream().map(ProviderMgmtDto::ofInitData).collect(Collectors.toList());
    }

    private Map<String, List<CodeDetailDto>> retrieveCodeList(ModelMap reqModel){
        List<CodeDetail> codeDetailList = codeDetailRepository.findAllByCodeIdInAndUseYnOrderByCodeIdAscOrderNumAsc(
                (List<String>) reqModel.get("code"), StatusEnum.FLAG_Y.getStatusMsg()
        );

        List<CodeDetailDto> codeDetailDtos = codeDetailList.stream().map(CodeDetailDto::ofInitData).collect(Collectors.toList());

        return codeDetailDtos.stream()
                .collect(Collectors.groupingBy(CodeDetailDto::getCodeId));
    }

    public Object existsData(ModelMap reqModel){

        //회원가입시(비로그인) 데이터 조회 가능 함. 하단 조건 생략
        //if(!authenticationUtil.hasRole(UserRole.ROLE_ANONYMOUS.name())) {

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

    public String generatorJoinPath(ModelMap reqModel){
        String returnUrl = "";

        if(!authenticationUtil.hasRole(UserRole.ROLE_ANONYMOUS.name())) {
            String joinType = String.valueOf(reqModel.get("type"));

            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.put("sId", authenticationUtil.getTargetStoreId(Long.parseLong(String.valueOf(reqModel.get("storeId")))));

            if (TypeEnum.JOIN_STORE.getStatusMsg().equals(joinType.toUpperCase())) {

                returnUrl = Constants.BIZ_JOIN_URL.concat(jwtUtil.doGenerateTokenFromMap(hashMap, JOIN_URL_TOKEN_VALIDATION_SECOND));

            } else if (TypeEnum.JOIN_USER.getStatusMsg().equals(joinType.toUpperCase())) {

                hashMap.put("gId", Long.parseLong(String.valueOf(reqModel.get("orgId"))));

                returnUrl = Constants.USER_JOIN_URL.concat(jwtUtil.doGenerateTokenFromMap(hashMap, JOIN_URL_TOKEN_VALIDATION_SECOND));
            }
        }else{
            throw new AuthorizationServiceException("UnAuthorization User");
        }

        return returnUrl;
    }
}






















