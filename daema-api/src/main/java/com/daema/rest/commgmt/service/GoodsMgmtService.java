package com.daema.rest.commgmt.service;

import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.GoodsRegReq;
import com.daema.commgmt.domain.GoodsRegReqReject;
import com.daema.commgmt.domain.attr.NetworkAttribute;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.daema.commgmt.domain.dto.response.GoodsMatchRespDto;
import com.daema.commgmt.repository.GoodsOptionRepository;
import com.daema.commgmt.repository.GoodsRegReqRejectRepository;
import com.daema.commgmt.repository.GoodsRegReqRepository;
import com.daema.commgmt.repository.GoodsRepository;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.commgmt.dto.GoodsMgmtDto;
import com.daema.rest.commgmt.dto.GoodsOptionDto;
import com.daema.rest.commgmt.dto.GoodsRegReqDto;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.enums.StatusEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import org.springframework.data.domain.Page;
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

    public ResponseDto<GoodsMgmtDto> getList(ComMgmtRequestDto requestDto) {

        //관리자 외 사용자는 useYn = Y 정보만 출력
        Page<Goods> goodsList = goodsRepository.getSearchPage(requestDto, authenticationUtil.isAdmin());

        List<Number> ids = goodsList.getContent()
                .stream()
                .map(Goods::getGoodsId)
                .collect(Collectors.toList());

        //goodsList 의 ids 로 옵션 추출
        List<GoodsOption> optionList = goodsOptionRepository.findByGoodsGoodsIdIn(ids);

        Map<Goods, List<GoodsOption>> optionMap = optionList.stream()
                .collect(Collectors.groupingBy(GoodsOption::getGoods));

        //goodsList add goodsOption
        goodsList.forEach(
                goods -> goods.setOptionList(optionMap.getOrDefault(goods, null))

        );

        return new ResponseDto(GoodsMgmtDto.class, goodsList);
    }

    /**
     * 시스템 관리자 : goods 테이블
     * 일반 사용자 : goods_reg_req 테이블
     */
    public void insertData(GoodsMgmtDto goodsMgmtDto) {
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
                        .reqStoreId(authenticationUtil.getStoreId())
                        .reqStatus(StatusEnum.REG_REQ.getStatusCode())
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

        if (CommonUtil.isNotEmptyList(goodsIds)) {

            List<Goods> goodsList = goodsRepository.findAllById(
                    goodsIds.stream()
                            .map(Number::longValue).collect(Collectors.toList())
            );

            if(CommonUtil.isNotEmptyList(goodsList)) {
                Optional.ofNullable(goodsList).orElseGet(Collections::emptyList).forEach(goods -> {
                    goods.updateDelYn(goods, StatusEnum.FLAG_Y.getStatusMsg());
                    goodsOptionRepository.deleteByGoodsGoodsId(goods.getGoodsId());
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

        if (goodsOptionDtos != null
                && goodsOptionDtos.size() > 0) {

            Number goodsId = goodsOptionDtos.get(0).getGoodsId();

            List<Number> ids = new ArrayList<>();
            ids.add(goodsId);

            //goodsList 의 ids 로 옵션 추출
            List<GoodsOption> optionList = goodsOptionRepository.findByGoodsGoodsIdIn(ids);
            List<GoodsOption> saveOptionList = new ArrayList<>();
            
            Map<Number, GoodsOptionDto> optKeys = goodsOptionDtos.stream()
                    .filter(goodsOptionDto -> goodsOptionDto.getGoodsOptionId() > 0)
                    .collect(Collectors.toMap(GoodsOptionDto::getGoodsOptionId, goodsOptionDto -> goodsOptionDto));

            optionList.forEach(
                    goodsOption -> {
                        if(!optKeys.containsKey(goodsOption.getGoodsOptionId())){
                            goodsOption.updateDelYn(goodsOption, StatusEnum.FLAG_Y.getStatusMsg());
                        }else{
                            GoodsOptionDto goodsOptionDto = optKeys.get(goodsOption.getGoodsOptionId());

                            goodsOption.setColorName(goodsOptionDto.getColorName());
                            goodsOption.setDistributor(goodsOptionDto.getDistributor());
                            goodsOption.setCommonBarcode(goodsOptionDto.getCommonBarcode());
                            goodsOption.setCapacity(goodsOptionDto.getCapacity());
                            goodsOption.setDelYn(goodsOptionDto.getDelYn());
                            goodsOption.setUnLockYn(goodsOptionDto.getUnLockYn());
                        }
                    }
            );

            goodsOptionDtos.forEach(
                    goodsOptionDto -> {
                        if(goodsOptionDto.getGoodsOptionId() == 0) {
                            goodsOptionDto.setDelYn(StatusEnum.FLAG_N.getStatusMsg());
                            saveOptionList.add(
                                    GoodsOptionDto.toEntity(goodsOptionDto)
                            );
                        }
                    }
            );

            goodsOptionRepository.saveAll(saveOptionList);

        } else {
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    public ResponseDto<GoodsRegReqDto> getRegReqList(ComMgmtRequestDto requestDto) {

        requestDto.setStoreId(authenticationUtil.getStoreId());

        //관리자 외 사용자는 해당 req_store_id 정보만 출력
        Page<GoodsRegReq> goodsList = goodsRegReqRepository.getSearchPage(requestDto, authenticationUtil.isAdmin());

        return new ResponseDto(GoodsRegReqDto.class, goodsList);
    }

    @Transactional
    public void updateReqStatus(GoodsRegReqDto goodsRegReqDto) {

        GoodsRegReq goodsRegReq = goodsRegReqRepository.findById(goodsRegReqDto.getGoodsRegReqId()).orElse(null);

        if(goodsRegReq != null) {
            goodsRegReq.updateReqStatus(goodsRegReq, goodsRegReqDto.getReqStatus());

            if(goodsRegReqDto.getReqStatus() == StatusEnum.REG_REQ_APPROVAL.getStatusCode()) {
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
            }else if(goodsRegReqDto.getReqStatus() == StatusEnum.REG_REQ_REJECT.getStatusCode()){
                GoodsRegReqReject goodsRegReqReject = new GoodsRegReqReject();
                goodsRegReqReject.setGoodsRegReqId(goodsRegReq.getGoodsRegReqId());
                goodsRegReqReject.setRejectComment(goodsRegReqDto.getRegReqRejectDto().getRejectComment());
                goodsRegReqReject.setRejectDateTime(LocalDateTime.now());
                goodsRegReqReject.setRejectUserId(authenticationUtil.getMemberSeq());

                goodsRegReqRejectRepository.save(goodsRegReqReject);
            }else{
                throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
            }
        }else{
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    public ResponseDto<GoodsMgmtDto> getMatchList(ComMgmtRequestDto requestDto) {

        List<Goods> goodsList = goodsRepository.getMatchList(requestDto);

        return new ResponseDto(GoodsMgmtDto.class, goodsList);
    }

    @Transactional
    public void applyMatchStatus(ModelMap reqModelMap) {

        List<Number>  groupGoodsIds = (ArrayList<Number>) reqModelMap.get("groupGoodsId");
        Integer useGoodsId = (Integer) reqModelMap.get("useGoodsId");

        if (CommonUtil.isNotEmptyList(groupGoodsIds)) {

            List<Goods> goodsList = goodsRepository.findAllById(
                    groupGoodsIds.stream()
                            .map(Number::longValue).collect(Collectors.toList())
            );

            if(CommonUtil.isNotEmptyList(goodsList)) {
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

    @Transactional(readOnly = true)
    public GoodsMatchRespDto goodsMatchBarcode(String fullBarcode) {
        String commonBarcode = CommonUtil.getCmnBarcode(fullBarcode);
        return goodsRepository.goodsMatchBarcode(commonBarcode);
    }

}


































