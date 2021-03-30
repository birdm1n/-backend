package com.daema.service;

import com.daema.common.enums.StatusEnum;
import com.daema.common.util.AuthenticationUtil;
import com.daema.domain.Goods;
import com.daema.domain.GoodsOption;
import com.daema.domain.GoodsRegReq;
import com.daema.domain.GoodsRegReqReject;
import com.daema.domain.attr.NetworkAttribute;
import com.daema.dto.*;
import com.daema.dto.common.ResponseDto;
import com.daema.repository.GoodsOptionRepository;
import com.daema.repository.GoodsRegReqRejectRepository;
import com.daema.repository.GoodsRegReqRepository;
import com.daema.repository.GoodsRepository;
import com.daema.response.enums.ServiceReturnMsgEnum;
import com.daema.response.exception.DataNotFoundException;
import com.daema.response.exception.ProcessErrorException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsMgmtService {

    private final GoodsRepository goodsRepository;
    private final GoodsOptionRepository goodsOptionRepository;
    private final GoodsRegReqRepository goodsRegReqRepository;
    private final GoodsRegReqRejectRepository goodsRegReqRejectRepository;

    private final AuthenticationUtil authenticationUtil;

    public GoodsMgmtService(GoodsRepository goodsRepository, GoodsOptionRepository goodsOptionRepository, GoodsRegReqRepository goodsRegReqRepository
                            ,GoodsRegReqRejectRepository goodsRegReqRejectRepository, AuthenticationUtil authenticationUtil) {
        this.goodsRepository = goodsRepository;
        this.goodsOptionRepository = goodsOptionRepository;
        this.goodsRegReqRepository = goodsRegReqRepository;
        this.goodsRegReqRejectRepository = goodsRegReqRejectRepository;
        this.authenticationUtil = authenticationUtil;
    }

    public ResponseDto<GoodsMgmtDto> getList(GoodsMgmtRequestDto requestDto) {

        PageRequest pageable = PageRequest.of(requestDto.getPageNo(), requestDto.getPerPageCnt());

        //관리자 외 사용자는 useYn = Y 정보만 출력
        //TODO 무조건 관리자로 처리중
        //Page<Goods> goodsList = goodsRepository.getSearchPage(pageable, authenticationUtil.isAdmin());

        Page<Goods> goodsList = goodsRepository.getSearchPage(pageable, !authenticationUtil.isAdmin());

        List<Number> ids = goodsList.getContent()
                .stream()
                .map(Goods::getGoodsId)
                .collect(Collectors.toList());

        //goodsList 의 ids 로 옵션 추출
        List<GoodsOption> optionList = goodsOptionRepository.findByGoodsIdIn(ids);

        Map<Long, List<GoodsOption>> optionMap = optionList.stream()
                .collect(Collectors.groupingBy(GoodsOption::getGoodsId));

        //goodsList add goodsOption
        goodsList.forEach(
                goods -> {
                    goods.setOptionList(optionMap.getOrDefault(goods.getGoodsId(), null));
                }
        );

        return new ResponseDto(GoodsMgmtDto.class, goodsList);
    }

    /**
     * 시스템 관리자 : goods 테이블
     * 일반 사용자 : goods_reg_req 테이블
     */
    public void insertData(GoodsMgmtDto goodsMgmtDto) {
        //TODO 하드코딩. 관리자 권한 확인 필요. 지금은 무조건 true 처리 해둠
        //TODO ifnull return 함수 추가
        if (authenticationUtil.isAdmin()) {
            goodsRepository.save(
                    Goods.builder()
                            .goodsName(goodsMgmtDto.getGoodsName())
                            .modelName(goodsMgmtDto.getModelName())
                            .maker(goodsMgmtDto.getMaker())
                            .telecom(goodsMgmtDto.getTelecom())
                            .network(goodsMgmtDto.getNetwork())
                            .capacity(goodsMgmtDto.getCapacity())
                            .originKey(goodsMgmtDto.getOriginKey())
                            .useYn(goodsMgmtDto.getUseYn())
                            .matchingYn(goodsMgmtDto.getMatchingYn())
                            .delYn(goodsMgmtDto.getDelYn())
                            .regiDateTime(LocalDateTime.now())
                            .build()
            );
        } else {
            goodsRegReqRepository.save(
                GoodsRegReq.builder()
                        .goodsName(goodsMgmtDto.getGoodsName())
                        .modelName(goodsMgmtDto.getModelName())
                        .maker(goodsMgmtDto.getMaker())
                        .telecom(goodsMgmtDto.getTelecom())
                        .network(goodsMgmtDto.getNetwork())
                        .capacity(goodsMgmtDto.getCapacity())
                        //TODO security 설정에 따라 storeId 가져오는 방식 변경 필요
                        //.reqStoreId(authenticationUtil.getStoreId())
                        .reqStoreId(1)
                        .reqStatus(StatusEnum.GOODS_REG_REQ.getStatusCode())
                        .regiDateTime(LocalDateTime.now())
                    .build()
            );
        }
    }

    @Transactional
    public void updateData(GoodsMgmtDto goodsMgmtDto) {

        Goods goods = goodsRepository.findById(goodsMgmtDto.getGoodsId()).orElse(null);

        if (goods != null) {

            //TODO ifnull return 함수 추가
            goods.setGoodsId(goodsMgmtDto.getGoodsId());
            goods.setGoodsName(goodsMgmtDto.getGoodsName());
            goods.setModelName(goodsMgmtDto.getModelName());
            goods.setMaker(goodsMgmtDto.getMaker());
            goods.setNetworkAttribute(new NetworkAttribute(goodsMgmtDto.getTelecom(), goodsMgmtDto.getNetwork()));
            goods.setCapacity(goodsMgmtDto.getCapacity());

            if (StringUtils.hasText(goodsMgmtDto.getOriginKey())) {
                goods.setOriginKey(goodsMgmtDto.getOriginKey());
            }
            if (StringUtils.hasText(goodsMgmtDto.getUseYn())) {
                goods.setUseYn(goodsMgmtDto.getUseYn());
            }
            if (StringUtils.hasText(goodsMgmtDto.getMatchingYn())) {
                goods.setMatchingYn(goodsMgmtDto.getMatchingYn());
            }
            if (StringUtils.hasText(goodsMgmtDto.getDelYn())) {
                goods.setDelYn(goodsMgmtDto.getDelYn());
            }
        } else {
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    @Transactional
    public void deleteData(ModelMap reqModelMap) {

        List<Number> goodsIds = (ArrayList<Number>) reqModelMap.get("goodsId");

        if (isNotEmptyList(goodsIds)) {

            List<Goods> goodsList = goodsRepository.findAllById(
                    goodsIds.stream()
                            .map(Number::longValue).collect(Collectors.toList())
            );

            if(isNotEmptyList(goodsList)) {
                Optional.ofNullable(goodsList).orElseGet(Collections::emptyList).forEach(goods -> {
                    goods.updateDelYn(goods, StatusEnum.FLAG_Y.getStatusMsg());
                });
            }else{
                throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }
        } else {
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }

    @Transactional
    public void updateUseYn(ModelMap reqModelMap) {

        long goodsId = Long.parseLong(String.valueOf(reqModelMap.getAttribute("goodsId")));
        String useYn = String.valueOf(reqModelMap.getAttribute("useYn"));

        Goods goods = goodsRepository.findById(goodsId).orElse(null);

        if (goods != null) {
            goods.updateUseYn(goods, useYn);
        } else {
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    @Transactional
    public void saveOptionInfo(List<GoodsOptionDto> goodsOptionDtos) {
        //일괄 삭제, 재인서트
        if (goodsOptionDtos != null
                && goodsOptionDtos.size() > 0) {

            Number goodsId = goodsOptionDtos.get(0).getGoodsId();
            goodsOptionRepository.deleteByGoodsId(goodsId);

            List<GoodsOption> insertOptionList = goodsOptionDtos.stream()
                    .map(GoodsOptionDto::toEntity)
                    .collect(Collectors.toList());

            goodsOptionRepository.saveAll(insertOptionList);

        } else {
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    public ResponseDto<GoodsRegReqDto> getRegReqList(GoodsRegReqRequestDto requestDto) {

        PageRequest pageable = PageRequest.of(requestDto.getPageNo(), requestDto.getPerPageCnt());

        //관리자 외 사용자는 해당 req_store_id 정보만 출력
        //TODO 무조건 관리자로 처리중
        //Page<GoodsRegReq> goodsList = goodsRegReqRepository.getSearchPage(pageable, authenticationUtil.isAdmin());

        Page<GoodsRegReq> goodsList = goodsRegReqRepository.getSearchPage(pageable, !authenticationUtil.isAdmin());

        return new ResponseDto(GoodsRegReqDto.class, goodsList);
    }

    @Transactional
    public void updateReqStatus(GoodsRegReqDto goodsRegReqDto) {

        GoodsRegReq goodsRegReq = goodsRegReqRepository.findById(goodsRegReqDto.getGoodsRegReqId()).orElse(null);

        if(goodsRegReq != null) {
            goodsRegReq.updateReqStatus(goodsRegReq, goodsRegReqDto.getReqStatus());

            if(goodsRegReqDto.getReqStatus() == StatusEnum.GOODS_REG_REQ_APPROVAL.getStatusCode()) {
                //insertData 사용 안함. 요청 승인 정책이 시스템관리자에서 확장 또는 변경될 수 있음
                goodsRepository.save(
                        Goods.builder()
                                .goodsName(goodsRegReq.getGoodsName())
                                .modelName(goodsRegReq.getModelName())
                                .maker(goodsRegReq.getMaker())
                                .telecom(goodsRegReq.getNetworkAttribute().getTelecom())
                                .network(goodsRegReq.getNetworkAttribute().getNetwork())
                                .capacity(goodsRegReq.getCapacity())
                                .originKey("R".concat(String.valueOf(goodsRegReq.getGoodsRegReqId())))
                                .regiDateTime(LocalDateTime.now())
                                .useYn(StatusEnum.FLAG_N.getStatusMsg())
                                .matchingYn(StatusEnum.FLAG_N.getStatusMsg())
                                .delYn(StatusEnum.FLAG_N.getStatusMsg())
                                .build()
                );
            }else if(goodsRegReqDto.getReqStatus() == StatusEnum.GOODS_REG_REQ_REJECT.getStatusCode()){
                GoodsRegReqReject goodsRegReqReject = new GoodsRegReqReject();
                goodsRegReqReject.setGoodsRegReqId(goodsRegReq.getGoodsRegReqId());
                goodsRegReqReject.setRejectComment(goodsRegReqDto.getRegReqRejectDto().getRejectComment());
                goodsRegReqReject.setRejectDateTime(LocalDateTime.now());
                //TODO security 설정에 따라 userId 가져오는 방식 변경 필요
                goodsRegReqReject.setRejectUserId(1L);

                goodsRegReqRejectRepository.save(goodsRegReqReject);
            }else{
                throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
            }
        }else{
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    public ResponseDto<GoodsMgmtDto> getMatchList() {

        List<Goods> goodsList = goodsRepository.getMatchList();

        return new ResponseDto(GoodsMgmtDto.class, goodsList);
    }

    @Transactional
    public void applyMatchStatus(ModelMap reqModelMap) {

        List<Number>  groupGoodsIds = (ArrayList<Number>) reqModelMap.get("groupGoodsId");
        Integer useGoodsId = (Integer) reqModelMap.get("useGoodsId");

        if (isNotEmptyList(groupGoodsIds)) {

            List<Goods> goodsList = goodsRepository.findAllById(
                    groupGoodsIds.stream()
                            .map(Number::longValue).collect(Collectors.toList())
            );

            if(isNotEmptyList(goodsList)) {
                Optional.ofNullable(goodsList).orElseGet(Collections::emptyList).forEach(goods -> {

                    goods.updateMatchYn(goods, StatusEnum.FLAG_Y.getStatusMsg());

                    if(goods.getGoodsId() == useGoodsId){
                        goods.updateUseYn(goods, StatusEnum.FLAG_Y.getStatusMsg());
                    }else{
                        goods.updateUseYn(goods, StatusEnum.FLAG_N.getStatusMsg());
                    }
                });
            }else{
                throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }
        } else {
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }

    private <E> boolean isNotEmptyList(Collection<E> itemList){
        return itemList != null && itemList.size() > 0;
    }
}


































