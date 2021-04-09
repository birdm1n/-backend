package com.daema.common;

public class Constants {
    public static final String ORGANIZATION_DEFAULT_GROUP_NAME = "기본그룹";

    //@ApiOperation 추출해서 DB화 시키기 위한 nickname 속성 관리
    //그룹ID||그룹명||권한 + ||정렬순서(Controller 메소드에 직접 작성)
    public static final String API_OPENING_STORE = "1||개통점 관리||ROLE_USER,ROLE_MANAGER,ROLE_ADMIN";
    public static final String API_SALE_STORE = "2||영업점 관리||ROLE_USER,ROLE_MANAGER,ROLE_ADMIN";
    public static final String API_PUB_NOTI = "3||공시지원금 관리||ROLE_ADMIN";
    public static final String API_GOODS = "4||상품 관리||ROLE_USER,ROLE_MANAGER,ROLE_ADMIN";
    public static final String API_CHARGE = "5||요금 관리||ROLE_USER,ROLE_MANAGER,ROLE_ADMIN";
    public static final String API_ADD_SERVICE = "6||부가서비스 관리||ROLE_USER,ROLE_MANAGER,ROLE_ADMIN";
    public static final String API_ORGNZT = "7||조직 관리||ROLE_USER,ROLE_MANAGER,ROLE_ADMIN";
    public static final String API_USER = "8||사용자 관리||ROLE_USER,ROLE_MANAGER,ROLE_ADMIN";
    public static final String API_ROLE_FUNC = "9||역할 관리||ROLE_USER,ROLE_MANAGER,ROLE_ADMIN";
}