package com.daema.response.handler;

import com.daema.response.io.CommonResponseMessage;
import org.springframework.http.ResponseEntity;

/**
 * 프로세스 동작에 따라 응답 메세지 생성
 */
public interface ResponseHandler {

    /** 완료 응답 **/
    <T> ResponseEntity<CommonResponseMessage<T>> ok();

    /** 완료 및 동적 클래스 데이터 응답 **/
    <T> ResponseEntity<CommonResponseMessage<T>> ok(Object resultObj);

    /** 실패 응답 **/
    <T> ResponseEntity<CommonResponseMessage<T>> fail();

    /** 실패 및 메세지 응답 **/
    <T> ResponseEntity<CommonResponseMessage<T>> fail(String resultMsg);

    /** 실패 및 상태 코드, 메세지 응답 **/
    <T> ResponseEntity<CommonResponseMessage<T>> fail(String resultCode, String resultMsg);

    /** 런타임 오류 응답 **/
    <T> ResponseEntity<CommonResponseMessage<T>> exception(String exceptionMsg);

    /** 결과값에 따라 완료, 실패 응답 **/
    <T> ResponseEntity<CommonResponseMessage<T>> getResponseMessageAsCUD(Object retVal);

    /** 조회 결과값에 따라 완료, 실패 응답 **/
    <T> ResponseEntity<CommonResponseMessage<T>> getResponseMessageAsRetrieveResult(Object retVal, String failCode, String failMsg);
}