package com.daema.rest.common.consts;

import java.io.File;

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

    public static final String[] SECURITY_PERMIT_ALL = {
            "/user/signup", "/user/login", "/user/invalidate", "/user/verify/**",
            "/dataHandle/**", "/oauth/**", "/**/joinStore", "/**/insertUser",

            "/api/user/signup", "/api/user/login", "/api/user/invalidate", "/api/user/verify/**",
            "/api/dataHandle/**", "/api/oauth/**",

            "/v1/api/user/login"
    };

    public static final String[] SECURITY_EXCLUDE_URLS = {
            "/user/invalidate", "/api/user/invalidate"
    };

    public static final String[] SECURITY_WEB_IGNORE_URLS = {
            "/v2/api-docs", "/swagger-resources/**",
            "/swagger-ui.html", "/webjars/**", "/swagger/**"
    };

    public static final String BIZ_JOIN_URL = "/store/sign-up";
    public static final String USER_JOIN_URL = "/user/sign-up";

    public static final String DEVICE_HOME_PATH = File.separator.concat("home").concat(File.separator).concat("centos");

    public static final String XLS_ROOT_PATH = DEVICE_HOME_PATH.concat(File.separator).concat("excel");

    public static final String XLS_UPLOAD_PATH = XLS_ROOT_PATH.concat(File.separator).concat("upload");
    public static final String XLS_DOWNLOAD_PATH = XLS_ROOT_PATH.concat(File.separator).concat("download");
    public static final String XLS_TEMPLATE_PATH = XLS_ROOT_PATH.concat(File.separator).concat("template");
    public static final String XLS_TMP_PATH = XLS_ROOT_PATH.concat(File.separator).concat("tmp");

    public static final String FILE_DOWNLOAD_VIEW = "fileDownloadView";
    public static final String EXCEL_DOWNLOAD_VIEW = "excelDownloadView";

}