package com.daema.rest.base.service;

import com.daema.core.base.domain.CodeDetail;
import com.daema.core.base.domain.Members;
import com.daema.core.base.enums.StatusEnum;
import com.daema.core.base.enums.TypeEnum;
import com.daema.core.base.enums.UserRole;
import com.daema.core.base.repository.CodeDetailRepository;
import com.daema.core.base.repository.MemberRepository;
import com.daema.core.commgmt.domain.FuncMgmt;
import com.daema.core.commgmt.domain.Organization;
import com.daema.core.commgmt.domain.Store;
import com.daema.core.commgmt.dto.SaleStoreMgmtDto;
import com.daema.core.commgmt.repository.FuncMgmtRepository;
import com.daema.core.commgmt.repository.OrganizationRepository;
import com.daema.core.commgmt.repository.PubNotiRawDataRepository;
import com.daema.core.commgmt.repository.StoreRepository;
import com.daema.core.base.dto.CodeDetailDto;
import com.daema.core.base.dto.RetrieveInitDataResponseDto;
import com.daema.rest.common.consts.Constants;
import com.daema.rest.common.consts.PropertiesValue;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.JwtUtil;
import com.daema.rest.common.util.RedisUtil;
import com.daema.rest.wms.service.StockMgmtService;
import com.daema.core.wms.domain.Provider;
import com.daema.core.wms.dto.ProviderMgmtDto;
import com.daema.core.wms.dto.response.DeviceHistoryResponseDto;
import com.daema.core.wms.repository.DeviceRepository;
import com.daema.core.wms.repository.ProviderRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataHandleService {

    private final FuncMgmtRepository funcMgmtRepository;
    private final PubNotiRawDataRepository pubNotiRawDataRepository;
    private final StoreRepository storeRepository;
    private final StockMgmtService stockMgmtService;
    private final CodeDetailRepository codeDetailRepository;
    private final MemberRepository memberRepository;
    private final ProviderRepository providerRepository;
    private final OrganizationRepository organizationRepository;
    private final DeviceRepository deviceRepository;

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final RequestMappingHandlerMapping handlerMapping;
    private final AuthenticationUtil authenticationUtil;

    //10???
    private final static long JOIN_URL_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 24 * 365 * 10;

    public DataHandleService(FuncMgmtRepository funcMgmtRepository, PubNotiRawDataRepository pubNotiRawDataRepository
            , StoreRepository storeRepository, StockMgmtService stockMgmtService, CodeDetailRepository codeDetailRepository
            , MemberRepository memberRepository
            , ProviderRepository providerRepository
            , OrganizationRepository organizationRepository
            , DeviceRepository deviceRepository
            , JwtUtil jwtUtil, RedisUtil redisUtil
            , RequestMappingHandlerMapping handlerMapping, AuthenticationUtil authenticationUtil) {
        this.funcMgmtRepository = funcMgmtRepository;
        this.pubNotiRawDataRepository = pubNotiRawDataRepository;
        this.storeRepository = storeRepository;
        this.stockMgmtService = stockMgmtService;
        this.codeDetailRepository = codeDetailRepository;
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;

        this.handlerMapping = handlerMapping;
        this.authenticationUtil = authenticationUtil;
        this.providerRepository = providerRepository;
        this.organizationRepository = organizationRepository;
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public void extractApiFunc() {

        if (authenticationUtil.isAdmin()) {

            Map<RequestMappingInfo, HandlerMethod> mappings = handlerMapping.getHandlerMethods();
            Set<RequestMappingInfo> keys = mappings.keySet();
            Iterator<RequestMappingInfo> iterator = keys.iterator();

            List<FuncMgmt> javaList = new ArrayList<>();
            String[] nickname;
            FuncMgmt funcMgmt;
            HashMap<String, FuncMgmt> map = new HashMap<>();

            String profile = PropertiesValue.profilesActive;

            while (iterator.hasNext()) {
                RequestMappingInfo key = iterator.next();
                HandlerMethod method = mappings.get(key);

                if (method.hasMethodAnnotation(ApiOperation.class)) {
                    ApiOperation annotation = method.getMethodAnnotation(ApiOperation.class);

                    if (StringUtils.hasText(annotation.nickname())) {
                        nickname = annotation.nickname().split("\\|\\|");

                        if (profile != null &&
                                (!"prod".equals(profile) &&
                                        !"stag".equals(profile))) {
                            funcMgmt = FuncMgmt.builder()
                                    .funcId(nickname[0].concat("_".concat(method.getMethod().getName())))
                                    .groupId(Integer.parseInt(nickname[0]))
                                    .groupName(nickname[1])
                                    .title(annotation.value())
                                    .role(nickname[2])
                                    .urlPath(String.valueOf(key.getPatternsCondition().getPatterns().toArray()[0]))
                                    .orderNum(Integer.parseInt(nickname[3]))
                                    .build();
                        }else{
                            funcMgmt = FuncMgmt.builder()
                                    .funcId(nickname[0].concat("_".concat(method.getMethod().getName())))
                                    .groupId(Integer.parseInt(nickname[0]))
                                    .groupName(nickname[1])
                                    .title(annotation.value())
                                    .role(nickname[2])
                                    .urlPath(String.valueOf(key.getPatternsCondition().getPatterns().toArray()[1]))
                                    .orderNum(Integer.parseInt(nickname[3]))
                                    .build();
                        }

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
        } else {
            throw new AuthorizationServiceException("UnAuthorization User");
        }
    }

    @Transactional
    public void migrationSmartChoiceData() {
        if (authenticationUtil.isAdmin()
                && pubNotiRawDataRepository.existsByDeadLineYn(StatusEnum.FLAG_N.getStatusMsg())) {

            long memberSeq = authenticationUtil.getMemberSeq();

            pubNotiRawDataRepository.migrationSmartChoiceData(memberSeq);
        } else {
            throw new AuthorizationServiceException("UnAuthorization User");
        }
    }

    public RetrieveInitDataResponseDto retrieveInitData(ModelMap reqModel) {

        RetrieveInitDataResponseDto responseDto = new RetrieveInitDataResponseDto();

        if (!authenticationUtil.hasRole(UserRole.ROLE_ANONYMOUS.name())) {

            if (reqModel.containsKey("initData")
                    && reqModel.get("initData") != null) {

                List<String> initData = (List<String>) reqModel.get("initData");

                if (initData.contains("storeList")) {
                    responseDto.setStoreList(retrieveStoreList());
                }
                if (initData.contains("provList")) {
                    responseDto.setProvList(retrieveProvList(false));
                }
                if (initData.contains("provListAll")) {
                    responseDto.setProvList(retrieveProvList(true));
                }
                if (initData.contains("stockList")) {
                    responseDto.setStockList(stockMgmtService.selectStockList(0L));
                }
            }

            if (reqModel.containsKey("code")
                    && reqModel.get("code") != null) {

                responseDto.setCodeList(retrieveCodeList(reqModel));
            }
        } else {
            throw new AuthorizationServiceException("UnAuthorization User");
        }

        return responseDto;
    }

    private List<SaleStoreMgmtDto> retrieveStoreList() {

        List<Store> storeList = storeRepository.findByUseYnOrderByStoreName(StatusEnum.FLAG_N.getStatusMsg());
        return storeList.stream().map(SaleStoreMgmtDto::ofInitData).collect(Collectors.toList());
    }

    private List<ProviderMgmtDto> retrieveProvList(boolean fullFlag) {
        long storeId = authenticationUtil.getStoreId();
        List<Provider> provList = null;
        if (fullFlag) {
            provList = providerRepository.findByStoreIdAndDelYnOrderByProvName(storeId, StatusEnum.FLAG_N.getStatusMsg());
        } else {
            provList = providerRepository.findByStoreIdAndUseYnAndDelYnOrderByProvName(storeId, StatusEnum.FLAG_Y.getStatusMsg(), StatusEnum.FLAG_N.getStatusMsg());
        }
        return provList.stream().map(ProviderMgmtDto::ofInitData).collect(Collectors.toList());
    }

    private Map<String, List<CodeDetailDto>> retrieveCodeList(ModelMap reqModel) {
        List<CodeDetail> codeDetailList = codeDetailRepository.findAllByCodeIdInAndUseYnOrderByCodeIdAscOrderNumAsc(
                (List<String>) reqModel.get("code"), StatusEnum.FLAG_Y.getStatusMsg()
        );

        List<CodeDetailDto> codeDetailDtos = codeDetailList.stream().map(CodeDetailDto::ofInitData).collect(Collectors.toList());

        return codeDetailDtos.stream()
                .collect(Collectors.groupingBy(CodeDetailDto::getCodeId));
    }

    public Object existsData(ModelMap reqModel, HttpServletRequest request) {

        //???????????????(????????????) ????????? ?????? ?????? ???. ?????? ?????? ??????
        //if(!authenticationUtil.hasRole(UserRole.ROLE_ANONYMOUS.name())) {

        Object retVal = "";

        if (reqModel.get("storeName") != null) {
            retVal = Optional.ofNullable(storeRepository.findByStoreName((String) reqModel.get("storeName")))
                    .orElseGet(Store::new).getStoreName();
        } else if (reqModel.get("userName") != null) {
            retVal = Optional.ofNullable(memberRepository.findByUsername((String) reqModel.get("userName")))
                    .orElseGet(Members::new).getUsername();
        } else if (reqModel.get("bizNo") != null) {
            retVal = Optional.ofNullable(storeRepository.findByBizNoAndUseYn((String) reqModel.get("bizNo"), StatusEnum.FLAG_Y.getStatusMsg()))
                    .orElseGet(Store::new).getBizNo();
        } else if (reqModel.get("storeMapBizNo") != null) {
            retVal = storeRepository.findByBizNoAndUseYn((String) reqModel.get("storeMapBizNo"), StatusEnum.FLAG_Y.getStatusMsg());
        } else if (reqModel.get("userInsToken") != null) {
            HashMap<String, Object> retMap = new HashMap<>();

            //?????? ????????? ?????? ????????? ?????? ????????????
            String accessToken = jwtUtil.getAccessTokenFromHeader(request, JwtUtil.AUTHORIZATION);

            if (StringUtils.hasText(accessToken)) {

                Long storeId = (Long) jwtUtil.getClaim(accessToken, "sId", Long.class);
                Long orgId = (Long) jwtUtil.getClaim(accessToken, "gId", Long.class);

                Store store = storeRepository.findById(storeId).orElseGet(Store::new);
                Organization organization = organizationRepository.findById(orgId).orElseGet(Organization::new);

                retMap.put("storeName", store.getStoreName());
                retMap.put("orgName", organization.getOrgName());
                retMap.put("storeId", storeId);
                retMap.put("orgId", orgId);
            }

            retVal = retMap;
        }

        return retVal;
    }

    public String generatorJoinPath(ModelMap reqModel) {
        String returnUrl = "";

        if (!authenticationUtil.hasRole(UserRole.ROLE_ANONYMOUS.name())) {
            String joinType = String.valueOf(reqModel.get("type"));

            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.put("sId", authenticationUtil.getTargetStoreId(Long.parseLong(String.valueOf(reqModel.get("storeId")))));

            if (TypeEnum.JOIN_STORE.getStatusMsg().equals(joinType.toUpperCase())) {

                returnUrl = Constants.BIZ_JOIN_URL.concat("?at=").concat(jwtUtil.doGenerateTokenFromMap(hashMap, JOIN_URL_TOKEN_VALIDATION_SECOND));

            } else if (TypeEnum.JOIN_USER.getStatusMsg().equals(joinType.toUpperCase())) {

                hashMap.put("gId", Long.parseLong(String.valueOf(reqModel.get("orgId"))));

                returnUrl = Constants.USER_JOIN_URL.concat("?at=").concat(jwtUtil.doGenerateTokenFromMap(hashMap, JOIN_URL_TOKEN_VALIDATION_SECOND));
            }
        } else {
            throw new AuthorizationServiceException("UnAuthorization User");
        }

        return returnUrl;
    }

    public List<DeviceHistoryResponseDto> retrieveDeviceHistory(Long dvcId) {
        return deviceRepository.getDeviceHistory(dvcId, authenticationUtil.getStoreId());
    }
}






















