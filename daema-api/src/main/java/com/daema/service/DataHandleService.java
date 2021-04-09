package com.daema.service;

import com.daema.common.enums.StatusEnum;
import com.daema.common.util.AuthenticationUtil;
import com.daema.domain.FuncMgmt;
import com.daema.repository.FuncMgmtRepository;
import com.daema.repository.PubNotiRawDataRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataHandleService {

    private final PubNotiMgmtService pubNotiMgmtService;

    private final FuncMgmtRepository funcMgmtRepository;

    private final PubNotiRawDataRepository pubNotiRawDataRepository;

    private final RequestMappingHandlerMapping handlerMapping;

    private final AuthenticationUtil authenticationUtil;

    public DataHandleService(PubNotiMgmtService pubNotiMgmtService, FuncMgmtRepository funcMgmtRepository, PubNotiRawDataRepository pubNotiRawDataRepository
            ,RequestMappingHandlerMapping handlerMapping, AuthenticationUtil authenticationUtil) {
        this.pubNotiMgmtService = pubNotiMgmtService;
        this.funcMgmtRepository = funcMgmtRepository;
        this.pubNotiRawDataRepository = pubNotiRawDataRepository;
        this.handlerMapping = handlerMapping;
        this.authenticationUtil = authenticationUtil;
    }

    @Transactional
    public void extractApiFunc() {
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
    }

    @Transactional
    public void migrationSmartChoiceData(){
        /*
        if(authenticationUtil.isAdmin()
            && pubNotiRawDataRepository.existsByDeadLineYn(StatusEnum.FLAG_N.getStatusMsg())) {
        */
        //TODO
        if(pubNotiRawDataRepository.existsByDeadLineYn(StatusEnum.FLAG_N.getStatusMsg())) {

            //TODO
            //long memberSeq = authenticationUtil.getId("memberSeq");
            long memberSeq = 1L;

            pubNotiRawDataRepository.migrationSmartChoiceData(memberSeq);
        }
    }
}






















