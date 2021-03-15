package com.daema.response.handler;

import com.daema.response.io.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * 프로세스 동작에 따라 응답 메세지 생성
 */
public interface ResponseHandler {

    /** 완료 응답 **/
    <T> ResponseEntity<CommonResponse<T>> ok();

    /** 완료 및 동적 클래스 데이터 응답 **/
    <T> ResponseEntity<CommonResponse<T>> ok(Object resultObj);

    /** 실패 응답 **/
    <T> ResponseEntity<CommonResponse<T>> fail();

    /** 실패 및 메세지 응답 **/
    <T> ResponseEntity<CommonResponse<T>> fail(String resultMsg);

    /** 실패 및 상태 코드, 메세지 응답 **/
    <T> ResponseEntity<CommonResponse<T>> fail(String resultCode, String resultMsg);

    /** 런타임 오류 응답 **/
    <T> ResponseEntity<CommonResponse<T>> exception(String exceptionMsg);

    /** 결과값에 따라 완료, 실패 응답 **/
    <T> ResponseEntity<CommonResponse<T>> getResponseMessageAsCUD(Object retVal);

    /** 조회 결과값에 따라 완료, 실패 응답 **/
    <T> ResponseEntity<CommonResponse<T>> getResponseMessageAsRetrieveResult(Object retVal, String failCode, String failMsg);
}