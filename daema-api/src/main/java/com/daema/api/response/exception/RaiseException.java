package com.daema.api.response.exception;

/**
 * 예외 처리 테스트를 위해 강제 에러 발생
 */
public final class RaiseException {

    public static RaiseException numberFormatException() {
        Integer.parseInt("asdf");

        return null;
    }
}
