package com.daema.api.response.service;

import com.daema.api.response.enums.ServiceReturnMsgEnum;
import com.daema.api.response.io.CommonResponse;
import com.daema.api.response.io.FailResponse;
import com.daema.api.response.io.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Override
    public <T> ResponseEntity<CommonResponse<T>> ok() {
        return makeResponseMessage(new SuccessResponse());
    }

    @Override
    public <T> ResponseEntity<CommonResponse<T>> ok(Object resultObj) {
        return makeResponseMessage(new SuccessResponse(resultObj));
    }

    @Override
    public <T> ResponseEntity<CommonResponse<T>> fail() {
        return makeResponseMessage(new FailResponse());
    }

    @Override
    public <T> ResponseEntity<CommonResponse<T>> fail(String resultMsg) {
        return makeResponseMessage(new FailResponse(resultMsg));
    }

    @Override
    public <T> ResponseEntity<CommonResponse<T>> fail(String resultCode, String resultMsg) {
        return makeResponseMessage(new FailResponse(resultCode, resultMsg));
    }

    @Override
    public <T> ResponseEntity<CommonResponse<T>> exception(String exceptionMsg) {
        return makeResponseMessage(new FailResponse(exceptionMsg));
    }

    /**
     * 조회 결과에 대한 분기만 처리. 2021-02-17. rhko
     */
    @Override
    public <T> ResponseEntity<CommonResponse<T>> getResponseMessageAsRetrieveResult(Object retVal, String failCode, String failMsg) {
        if(retVal != null){
            if(retVal instanceof List) {
                try {
                    if(((List) retVal).size() > 0){
                        return ok(retVal);
                    }else{
                        return checkFailCode(failCode, failMsg);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    return checkFailCode(failCode, failMsg);
                }
            }else{
                return ok(retVal);
            }
            /* list check
            if(retVal.getClass().isArray()){
                return Array.getLength(retVal) > 0 ? ok(retVal) : checkFailCode(failCode, failMsg);
            }else{
                return ok(retVal);
            }
            */
        }else{
            return checkFailCode(failCode, failMsg);
        }
    }

    /**
     * bool, string 에 대한 분기만 처리. 2021-02-17. rhko
     * 추가되는 타입은 add else if
     */
    @Override
    public <T> ResponseEntity<CommonResponse<T>> getResponseMessageAsCUD(Object retVal) {
        if(retVal instanceof Boolean){
            if((Boolean) retVal) {
                return ok();
            }else{
                return fail();
            }
        }else if(retVal instanceof String){
            if( ServiceReturnMsgEnum.SUCCESS.name().equals(String.valueOf(retVal))) {
                return ok();
            }else{
                return fail(String.valueOf(retVal));
            }
        }else{
            return fail();
        }
    }

    /**
     * failCode 유무에 따라 리턴 구분 처리
     */
    private <T> ResponseEntity<CommonResponse<T>> checkFailCode(String failCode, String failMsg){
        if(!StringUtils.isEmpty(failCode)){
            return fail(failCode, failMsg);
        }else{
            return StringUtils.isEmpty(failMsg) ? fail() : fail(failMsg);
        }
    }

    private <T> ResponseEntity<CommonResponse<T>> makeResponseMessage(CommonResponse commonResponse){
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
