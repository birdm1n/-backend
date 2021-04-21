package com.daema.rest.common.handler;

import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.common.io.response.FailResponse;
import com.daema.rest.common.io.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 프로세스 동작에 따라 응답 메세지 생성
 */
@Component
public class ResponseHandler {

    /** 완료 응답 **/
    public <T> ResponseEntity<CommonResponse<T>> ok() {
        return makeResponseMessage(new SuccessResponse());
    }

    /** 완료 및 동적 클래스 데이터 응답 **/
    public <T> ResponseEntity<CommonResponse<T>> ok(Object resultObj) {
        return makeResponseMessage(new SuccessResponse(resultObj));
    }

    /** 실패 응답 **/
    public <T> ResponseEntity<CommonResponse<T>> fail() {
        return makeResponseMessage(new FailResponse());
    }

    /** 실패 및 메세지 응답 **/
    public <T> ResponseEntity<CommonResponse<T>> fail(String resultMsg) {
        return makeResponseMessage(new FailResponse(resultMsg));
    }

    /** 실패 및 상태 코드, 메세지 응답 **/
    public <T> ResponseEntity<CommonResponse<T>> fail(String resultCode, String resultMsg) {
        return makeResponseMessage(new FailResponse(resultCode, resultMsg));
    }

    /** 런타임 오류 응답 **/
    public <T> ResponseEntity<CommonResponse<T>> exception(String exceptionMsg) {
        return makeResponseMessage(new FailResponse(exceptionMsg));
    }

    /**
     * 조회 결과에 대한 분기만 처리. 2021-02-17. rhko
     */
    
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
        if(StringUtils.hasText(failCode)){
            return fail(failCode, failMsg);
        }else{
            return StringUtils.hasText(failMsg) ? fail(failMsg) : fail();
        }
    }

    private <T> ResponseEntity<CommonResponse<T>> makeResponseMessage(CommonResponse CommonResponse){
        return new ResponseEntity<>(CommonResponse, HttpStatus.OK);
    }
}