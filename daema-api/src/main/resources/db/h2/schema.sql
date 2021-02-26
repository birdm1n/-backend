create table comte_cop_seq
(
    table_name            varchar(20) not null,
    next_id               numeric(30) null,
    primary key (table_name)
)
;



create unique index comte_cop_seq_pk on comte_cop_seq
(
	table_name
)
;



create table comtn_bbsmaster
(
    bbs_id                char(20) not null,
    bbs_nm                varchar(255) not null,
    bbs_intrcn            varchar(2400) null,
    bbs_ty_code           char(6) not null,
    bbs_attrb_code        char(6) not null,
    reply_posbl_at        char(1) null,
    file_atch_posbl_at    char(1) not null,
    atch_posbl_file_number  numeric(2) not null,
    atch_posbl_file_size  numeric(8) null,
    use_at                char(1) not null,
    tmplat_id             char(20) null,
    frst_register_id      varchar(20) not null,
    frst_regist_pnttm     datetime not null,
    last_updusr_id        varchar(20) null,
    last_updt_pnttm       datetime null,
    primary key (bbs_id)
)
;



create unique index comtn_bbsmaster_pk on comtn_bbsmaster
(
	bbs_id
)
;



create table comtn_bbs
(
    ntt_id                numeric(20) not null,
    bbs_id                char(20) not null,
    ntt_no                numeric(20) null,
    ntt_sj                varchar(2000) null,
    ntt_cn                mediumtext null,
    answer_at             char(1) null,
    parntsctt_no          numeric(10) null,
    answer_lc             numeric(8) null,
    sort_ordr             numeric(8) null,
    rdcnt                 numeric(10) null,
    use_at                char(1) not null,
    ntce_bgnde            char(20) null,
    ntce_endde            char(20) null,
    ntcr_id               varchar(20) null,
    ntcr_nm               varchar(20) null,
    password              varchar(200) null,
    atch_file_id          char(20) null,
    frst_regist_pnttm     datetime not null,
    frst_register_id      varchar(20) not null,
    last_updt_pnttm       datetime null,
    last_updusr_id        varchar(20) null,
    primary key (ntt_id,bbs_id)
)
;



create unique index comtn_bbs_pk on comtn_bbs
(
	ntt_id,
	bbs_id
)
;



create index comtn_bbs_i01 on comtn_bbs
(
	bbs_id
)
;



create table comtn_comment
(
    ntt_id                numeric(20) not null,
    bbs_id                char(20) not null,
    answer_no             numeric(20) not null,
    wrter_id              varchar(20) null,
    wrter_nm              varchar(20) null,
    answer                varchar(200) null,
    use_at                char(1) not null,
    frst_regist_pnttm     datetime not null,
    frst_register_id      varchar(20) not null,
    last_updt_pnttm       datetime null,
    last_updusr_id        varchar(20) null,
    password              varchar(200) null,
    primary key (ntt_id,bbs_id,answer_no)
)
;



create unique index comtn_comment_pk on comtn_comment
(
	ntt_id,
	bbs_id,
	answer_no
)
;



create index comtn_comment_i01 on comtn_comment
(
	ntt_id,
	bbs_id
)
;



create table comtn_bbsmaster_optn
(
    bbs_id                char(20) not null,
    answer_at             char(1) not null,
    stsfdg_at             char(1) not null,
    frst_regist_pnttm     datetime not null,
    last_updt_pnttm       datetime null,
    frst_register_id      varchar(20) not null,
    last_updusr_id        varchar(20) null,
    primary key (bbs_id)
)
;



create unique index comtn_bbsmaster_optn_pk on comtn_bbsmaster_optn
(
	bbs_id
)
;



create table comtn_bbs_use
(
    bbs_id                char(20) not null,
    trget_id              char(20) not null,
    use_at                char(1) not null,
    regist_se_code        char(6) null,
    frst_regist_pnttm     datetime null,
    frst_register_id      varchar(20) not null,
    last_updt_pnttm       datetime null,
    last_updusr_id        varchar(20) null,
    primary key (bbs_id,trget_id)
)
;



create unique index comtn_bbs_use_pk on comtn_bbs_use
(
	bbs_id,
	trget_id
)
;



create index comtn_bbs_use_i01 on comtn_bbs_use
(
	bbs_id
)
;


create table comtn_rest_de
(
    restde_no             numeric(6) not null,
    restde                char(8) null,
    restde_nm             varchar(60) null,
    restde_dc             varchar(200) null,
    restde_se_code        varchar(2) null,
    frst_regist_pnttm     datetime null,
    frst_register_id      varchar(20) null,
    last_updt_pnttm       datetime null,
    last_updusr_id        varchar(20) null,
    primary key (restde_no)
)
;



create unique index comtn_rest_de_pk on comtn_rest_de
(
	restde_no
)
;


create table comtn_entrprs_mber
(
    entrprs_mber_id       varchar(20) not null,
    entrprs_se_code       char(8) null,
    bizrno                varchar(10) null,
    jurirno               varchar(13) null,
    cmpny_nm              varchar(60) not null,
    cxfc                  varchar(50) null,
    zip                   varchar(6) not null,
    adres                 varchar(100) not null,
    entrprs_middle_telno  varchar(4) not null,
    fxnum                 varchar(20) null,
    induty_code           char(1) null,
    applcnt_nm            varchar(50) not null,
    applcnt_ihidnum       varchar(200) null,
    sbscrb_de             datetime null,
    entrprs_mber_sttus    varchar(15) null,
    entrprs_mber_password  varchar(200) null,
    entrprs_mber_password_hint  varchar(100) not null,
    entrprs_mber_password_cnsr  varchar(100) not null,
    detail_adres          varchar(100) null,
    entrprs_end_telno     varchar(4) not null,
    area_no               varchar(4) not null,
    applcnt_email_adres   varchar(50) not null,
    esntl_id              char(20) not null,
    primary key (entrprs_mber_id)
)
;



create unique index comtn_entrprs_mber_pk on comtn_entrprs_mber
(
	entrprs_mber_id
)
;



create table comtn_login_policy
(
    emplyr_id             varchar(20) not null,
    ip_info               varchar(23) not null,
    dplct_perm_at         char(1) not null,
    lmtt_at               char(1) not null,
    frst_register_id      varchar(20) null,
    frst_regist_pnttm     datetime null,
    last_updusr_id        varchar(20) null,
    last_updt_pnttm       datetime null,
    primary key (emplyr_id)
)
;



create unique index comtn_login_policy_pk on comtn_login_policy
(
	emplyr_id
)
;



create table comtn_menu_info
(
    menu_nm               varchar(60) not null,
    progrm_file_nm        varchar(60) not null,
    menu_no               numeric(20) not null,
    upper_menu_no         numeric(20) null,
    menu_ordr             numeric(5) not null,
    menu_dc               varchar(250) null,
    relate_image_path     varchar(100) null,
    relate_image_nm       varchar(60) null,
    primary key (menu_no)
)
;



create unique index comtn_menu_info_pk on comtn_menu_info
(
	menu_no
)
;






create index comtn_menu_info_i02 on comtn_menu_info
(
	upper_menu_no
)
;



create table comtn_emplyr_scrty_estbs
(
    scrty_dtrmn_trget_id  varchar(20) not null,
    mber_ty_code          char(5) null,
    author_code           varchar(30) not null,
    primary key (scrty_dtrmn_trget_id)
)
;



create table comtn_emplyr_info
(
    emplyr_id             varchar(20) not null,
    orgnzt_id             char(20) null,
    user_nm               varchar(60) not null,
    password              varchar(200) not null,
    empl_no               varchar(20) null,
    ihidnum               varchar(200) null,
    sexdstn_code          char(1) null,
    brthdy                char(20) null,
    fxnum                 varchar(20) null,
    house_adres           varchar(100) not null,
    password_hint         varchar(100) not null,
    password_cnsr         varchar(100) not null,
    house_end_telno       varchar(4) not null,
    area_no               varchar(4) not null,
    detail_adres          varchar(100) null,
    zip                   varchar(6) not null,
    offm_telno            varchar(20) null,
    mbtlnum               varchar(20) null,
    email_adres           varchar(50) null,
    ofcps_nm              varchar(60) null,
    house_middle_telno    varchar(4) not null,
    pstinst_code          char(8) null,
    emplyr_sttus_code     char(1) not null,
    esntl_id              char(20) not null,
    crtfc_dn_value        varchar(100) null,
    sbscrb_de             datetime null,
    primary key (emplyr_id)
)
;



create unique index comtn_emplyr_info_pk on comtn_emplyr_info
(
	emplyr_id
)
;



create index comtn_emplyr_info_i01 on comtn_emplyr_info
(
	orgnzt_id
)
;






create table comth_emplyr_info_change_dtls
(
    emplyr_id             varchar(20) not null,
    change_de             char(20) not null,
    orgnzt_id             char(20) null,
    empl_no               varchar(20) null,
    sexdstn_code          char(1) null,
    brthdy                char(20) null,
    fxnum                 varchar(20) null,
    house_adres           varchar(100) null,
    house_end_telno       varchar(4) null,
    area_no               varchar(4) null,
    detail_adres          varchar(100) null,
    zip                   varchar(6) null,
    offm_telno            varchar(20) null,
    mbtlnum               varchar(20) null,
    email_adres           varchar(50) null,
    house_middle_telno    varchar(4) null,
    pstinst_code          char(8) null,
    emplyr_sttus_code     char(1) null,
    esntl_id              char(20) null,
    primary key (emplyr_id,change_de)
)
;



create unique index comth_emplyr_info_change_dtls_pk on comth_emplyr_info_change_dtls
(
	emplyr_id,
	change_de
)
;



create index comth_emplyr_info_change_dtls_i01 on comth_emplyr_info_change_dtls
(
	emplyr_id
)
;





create table comtn_orgnzt_info
(
    orgnzt_id             char(20) not null,
    orgnzt_nm             varchar(20) not null,
    orgnzt_dc             varchar(100) null,
    primary key (orgnzt_id)
)
;



create unique index comtn_orgnzt_info_pk on comtn_orgnzt_info
(
	orgnzt_id
)
;



create table comtn_faq_info
(
    faq_id                char(20) not null,
    qestn_sj              varchar(255) null,
    qestn_cn              varchar(2500) null,
    answer_cn             varchar(2500) null,
    rdcnt                 numeric(10) null,
    frst_regist_pnttm     datetime not null,
    frst_register_id      varchar(20) not null,
    last_updt_pnttm       datetime not null,
    last_updusr_id        varchar(20) not null,
    atch_file_id          char(20) null,
    qna_process_sttus_code  char(1) null,
    primary key (faq_id)
)
;



create unique index comtn_faq_info_pk on comtn_faq_info
(
	faq_id
)
;



create index comtn_faq_info_i01 on comtn_faq_info
(
	atch_file_id
)
;



create table comtn_qa_info
(
    qa_id                 char(20) not null,
    qestn_sj              varchar(255) null,
    qestn_cn              varchar(2500) null,
    writng_de             char(20) null,
    rdcnt                 numeric(10) null,
    email_adres           varchar(50) null,
    frst_regist_pnttm     datetime null,
    frst_register_id      varchar(20) null,
    last_updt_pnttm       datetime null,
    last_updusr_id        varchar(20) null,
    qna_process_sttus_code  char(1) null,
    wrter_nm              varchar(20) null,
    answer_cn             varchar(2500) null,
    writng_password       varchar(20) null,
    answer_de             char(20) null,
    email_answer_at       char(1) null,
    area_no               varchar(4) null,
    middle_telno          varchar(4) null,
    end_telno             varchar(4) null,
    primary key (qa_id)
)
;



create unique index comtn_qa_info_pk on comtn_qa_info
(
	qa_id
)
;




create table comtn_stplat_info
(
    use_stplat_id         char(20) not null,
    use_stplat_nm         varchar(100) null,
    use_stplat_cn         mediumtext null,
    info_provd_agre_cn    mediumtext null,
    frst_regist_pnttm     datetime null,
    frst_register_id      varchar(20) null,
    last_updt_pnttm       datetime null,
    last_updusr_id        varchar(20) null,
    primary key (use_stplat_id)
)
;



create unique index comtn_stplat_info_pk on comtn_stplat_info
(
	use_stplat_id
)
;


create table comtn_word_dicary_info
(
    word_id               char(20) not null,
    word_nm               varchar(255) null,
    eng_nm                varchar(60) null,
    word_dc               varchar(4000) null,
    synonm                varchar(100) null,
    frst_regist_pnttm     datetime null,
    frst_register_id      varchar(20) null,
    last_updt_pnttm       datetime null,
    last_updusr_id        varchar(20) null,
    primary key (word_id)
)
;



create unique index comtn_word_dicary_info_pk on comtn_word_dicary_info
(
	word_id
)
;




create table comtn_file
(
    atch_file_id          char(20) not null,
    creat_dt              datetime not null,
    use_at                char(1) null,
    primary key (atch_file_id)
)
;



create unique index comtn_file_pk on comtn_file
(
	atch_file_id
)
;



create table comtn_file_detail
(
    atch_file_id          char(20) not null,
    file_sn               numeric(10) not null,
    file_stre_cours       varchar(2000) not null,
    stre_file_nm          varchar(255) not null,
    orignl_file_nm        varchar(255) null,
    file_extsn            varchar(20) not null,
    file_cn               mediumtext null,
    file_size             numeric(8) null,
    primary key (atch_file_id,file_sn)
)
;



create unique index comtn_file_detail_pk on comtn_file_detail
(
	atch_file_id,
	file_sn
)
;



create index comtn_file_detail_i01 on comtn_file_detail
(
	atch_file_id
)
;




create table comtn_author_role_relate
(
    author_code           varchar(30) not null,
    role_code             varchar(50) not null,
    creat_dt              datetime null,
    primary key (author_code,role_code)
)
;



create unique index comtn_author_role_relate_pk on comtn_author_role_relate
(
	author_code,
	role_code
)
;



create index comtn_author_role_relate_i01 on comtn_author_role_relate
(
	author_code
)
;



create index comtn_author_role_relate_i02 on comtn_author_role_relate
(
	role_code
)
;





create table comtn_author_info
(
    author_code           varchar(30) not null,
    author_nm             varchar(60) not null,
    author_dc             varchar(200) null,
    author_creat_de       datetime not null,
    primary key (author_code)
)
;



create unique index comtn_author_info_pk on comtn_author_info
(
	author_code
)
;




create table comtn_roles_hierarchy
(
    parnts_role           varchar(30) not null,
    chldrn_role           varchar(30) not null,
    primary key (parnts_role,chldrn_role)
)
;



create unique index comtn_roles_hierarchy_pk on comtn_roles_hierarchy
(
	parnts_role,
	chldrn_role
)
;



create unique index comtn_roles_hierarchy_i01 on comtn_roles_hierarchy
(
	parnts_role
)
;



create index comtn_roles_hierarchy_i02 on comtn_roles_hierarchy
(
	chldrn_role
)
;



create table comtn_role_info
(
    role_code             varchar(50) not null,
    role_nm               varchar(60) not null,
    role_pttrn            varchar(300) null,
    role_dc               varchar(200) null,
    role_ty               varchar(80) null,
    role_sort             varchar(10) null,
    role_creat_de         char(20) not null,
    primary key (role_code)
)
;



create unique index comtn_role_info_pk on comtn_role_info
(
	role_code
)
;


create table comtc_cmmn_cl_code
(
    cl_code               char(3) not null,
    cl_code_nm            varchar(60) null,
    cl_code_dc            varchar(200) null,
    use_at                char(1) null,
    frst_regist_pnttm     datetime null,
    frst_register_id      varchar(20) null,
    last_updt_pnttm       datetime null,
    last_updusr_id        varchar(20) null,
    primary key (cl_code)
)
;



create unique index comtc_cmmn_cl_code_pk on comtc_cmmn_cl_code
(
	cl_code
)
;



create table comtc_cmmn_code
(
    code_id               varchar(6) not null,
    code_id_nm            varchar(60) null,
    code_id_dc            varchar(200) null,
    use_at                char(1) null,
    cl_code               char(3) null,
    frst_regist_pnttm     datetime null,
    frst_register_id      varchar(20) null,
    last_updt_pnttm       datetime null,
    last_updusr_id        varchar(20) null,
    primary key (code_id)
)
;



create unique index comtc_cmmn_code_pk on comtc_cmmn_code
(
	code_id
)
;



create index comtc_cmmn_code_i01 on comtc_cmmn_code
(
	cl_code
)
;



create table comtc_cmmn_code_detail
(
    code_id               varchar(6) not null,
    code                  varchar(15) not null,
    code_nm               varchar(60) null,
    code_dc               varchar(200) null,
    use_at                char(1) null,
    frst_regist_pnttm     datetime null,
    frst_register_id      varchar(20) null,
    last_updt_pnttm       datetime null,
    last_updusr_id        varchar(20) null,
    primary key (code_id,code)
)
;



create unique index comtc_cmmn_code_detail_pk on comtc_cmmn_code_detail
(
	code_id,
	code
)
;



create index comtc_cmmn_code_detail_i01 on comtc_cmmn_code_detail
(
	code_id
)
;



create table comtr_dnm_adrzip
(
    rdmn_code             varchar(12) not null,
    sn                    numeric(10) not null,
    ctprvn_nm             varchar(20) null,
    signgu_nm             varchar(20) null,
    rdmn                  varchar(60) null,
    bdnbr_mnnm            varchar(5) null,
    bdnbr_slno            varchar(5) null,
    buld_nm               varchar(60) null,
    detail_buld_nm        varchar(60) null,
    zip                   varchar(6) not null,
    frst_regist_pnttm     datetime null,
    frst_register_id      varchar(20) null,
    last_updt_pnttm       datetime null,
    last_updusr_id        varchar(20) null,
    primary key (sn)
)
;



create unique index comtr_dnm_adrzip_pk on comtr_dnm_adrzip
(
	rdmn_code,
	sn
)
;




create table comtc_zip
(
    zip                   varchar(6) not null,
    sn                    numeric(10) not null,
    ctprvn_nm             varchar(20) null,
    signgu_nm             varchar(20) null,
    emd_nm                varchar(60) null,
    li_buld_nm            varchar(60) null,
    lnbr_dong_ho          varchar(20) null,
    frst_regist_pnttm     datetime null,
    frst_register_id      varchar(20) null,
    last_updt_pnttm       datetime null,
    last_updusr_id        varchar(20) null,
    primary key (zip,sn)
)
;



create unique index comtc_zip_pk on comtc_zip
(
	zip,
	sn
)
;



create table comtn_login_log
(
    log_id                char(20) not null,
    conect_id             varchar(20) null,
    conect_ip             varchar(23) null,
    conect_mthd           char(4) null,
    error_occrrnc_at      char(1) null,
    error_code            char(3) null,
    creat_dt              datetime null,
    primary key (log_id)
)
;



create unique index comtn_login_log_pk on comtn_login_log
(
	log_id
)
;



create table comtn_progrm_list
(
    progrm_file_nm        varchar(60) not null,
    progrm_stre_path      varchar(100) not null,
    progrm_korean_nm      varchar(60) null,
    progrm_dc             varchar(200) null,
    url                   varchar(100) not null,
    primary key (progrm_file_nm)
)
;



create unique index comtn_progrm_list_pk on comtn_progrm_list
(
	progrm_file_nm
)
;




create  view comvn_usermaster ( esntl_id,user_id,password,user_nm,user_zip,user_adres,user_email, user_se, orgnzt_id )
as
select esntl_id,emplyr_id,password,user_nm,zip,house_adres,email_adres,'usr' as user_se, orgnzt_id
from comtn_emplyr_info
union all
select esntl_id,entrprs_mber_id,entrprs_mber_password,cmpny_nm,zip,adres,applcnt_email_adres,'ent' as user_se, ' ' orgnzt_id
from comtn_entrprs_mber
order by esntl_id;
;





comment on table comte_cop_seq is 'comte_cop_seq';
comment on table comtn_bbs is '게시판';
comment on table comtn_bbsmaster is '게시판마스터';
comment on table comtn_bbsmaster_optn is '게시판마스터옵션';
comment on table comtn_bbs_use is '게시판활용';
comment on table comtn_comment is '댓글';
comment on table comtn_rest_de is '휴일관리';
comment on table comtn_entrprs_mber is '기업회원';
comment on table comtn_login_policy is '로그인정책';
comment on table comtn_menu_info is '메뉴정보';
comment on table comtn_emplyr_scrty_estbs is '사용자보안설정';
comment on table comtn_emplyr_info is '업무사용자정보';
comment on table comth_emplyr_info_change_dtls is '업무사용자정보변경내역';
comment on table comtn_orgnzt_info is '조직정보';
comment on table comtn_faq_info is 'faq정보';
comment on table comtn_qa_info is 'qa정보';
comment on table comtn_stplat_info is '약관정보';
comment on table comtn_word_dicary_info is '용어사전정보';
comment on table comtn_file is '파일속성';
comment on table comtn_file_detail is '파일상세정보';
comment on table comtn_author_role_relate is '권한롤관계';
comment on table comtn_author_info is '권한정보';
comment on table comtn_roles_hierarchy is '롤 계층구조';
comment on table comtn_role_info is '롤정보';
comment on table comtc_cmmn_cl_code is '공통분류코드';
comment on table comtc_cmmn_code is '공통코드';
comment on table comtc_cmmn_code_detail is '공통상세코드';
comment on table comtr_dnm_adrzip is '도로명주소';
comment on table comtc_zip is '우편번호';
comment on table comtn_login_log is '접속로그';
comment on table comtn_progrm_list is '프로그램목록';

comment on column comte_cop_seq.next_id is '다음아이디' ;
comment on column comte_cop_seq.table_name is '테이블명' ;
comment on column comtn_bbs.answer_at is '댓글여부' ;
comment on column comtn_bbs.answer_lc is '댓글위치' ;
comment on column comtn_bbs.atch_file_id is '첨부파일id' ;
comment on column comtn_bbs.bbs_id is '게시판id' ;
comment on column comtn_bbs.frst_regist_pnttm is '최초등록시점' ;
comment on column comtn_bbs.frst_register_id is '최초등록자id' ;
comment on column comtn_bbs.last_updt_pnttm is '최종수정시점' ;
comment on column comtn_bbs.last_updusr_id is '최종수정자id' ;
comment on column comtn_bbs.ntce_bgnde is '게시시작일' ;
comment on column comtn_bbs.ntce_endde is '게시종료일' ;
comment on column comtn_bbs.ntcr_id is '게시자id' ;
comment on column comtn_bbs.ntcr_nm is '게시자명' ;
comment on column comtn_bbs.ntt_cn is '게시물내용' ;
comment on column comtn_bbs.ntt_id is '게시물id' ;
comment on column comtn_bbs.ntt_no is '게시물번호' ;
comment on column comtn_bbs.ntt_sj is '게시물제목' ;
comment on column comtn_bbs.parntsctt_no is '부모글번호' ;
comment on column comtn_bbs.password is '비밀번호' ;
comment on column comtn_bbs.rdcnt is '조회수' ;
comment on column comtn_bbs.sort_ordr is '정렬순서' ;
comment on column comtn_bbs.use_at is '사용여부' ;
comment on column comtn_bbsmaster.atch_posbl_file_number is '첨부가능파일숫자' ;
comment on column comtn_bbsmaster.atch_posbl_file_size is '첨부가능파일사이즈' ;
comment on column comtn_bbsmaster.bbs_attrb_code is '게시판속성코드' ;
comment on column comtn_bbsmaster.bbs_id is '게시판id' ;
comment on column comtn_bbsmaster.bbs_intrcn is '게시판소개' ;
comment on column comtn_bbsmaster.bbs_nm is '게시판명' ;
comment on column comtn_bbsmaster.bbs_ty_code is '게시판유형코드' ;
comment on column comtn_bbsmaster.file_atch_posbl_at is '파일첨부가능여부' ;
comment on column comtn_bbsmaster.frst_regist_pnttm is '최초등록시점' ;
comment on column comtn_bbsmaster.frst_register_id is '최초등록자id' ;
comment on column comtn_bbsmaster.last_updt_pnttm is '최종수정시점' ;
comment on column comtn_bbsmaster.last_updusr_id is '최종수정자id' ;
comment on column comtn_bbsmaster.reply_posbl_at is '답장가능여부' ;
comment on column comtn_bbsmaster.tmplat_id is '템플릿id' ;
comment on column comtn_bbsmaster.use_at is '사용여부' ;
comment on column comtn_bbsmaster_optn.answer_at is '댓글여부' ;
comment on column comtn_bbsmaster_optn.bbs_id is '게시판id' ;
comment on column comtn_bbsmaster_optn.frst_regist_pnttm is '최초등록시점' ;
comment on column comtn_bbsmaster_optn.frst_register_id is '최초등록자id' ;
comment on column comtn_bbsmaster_optn.last_updt_pnttm is '최종수정시점' ;
comment on column comtn_bbsmaster_optn.last_updusr_id is '최종수정자id' ;
comment on column comtn_bbsmaster_optn.stsfdg_at is '만족도여부' ;
comment on column comtn_bbs_use.bbs_id is '게시판id' ;
comment on column comtn_bbs_use.frst_regist_pnttm is '최초등록시점' ;
comment on column comtn_bbs_use.frst_register_id is '최초등록자id' ;
comment on column comtn_bbs_use.last_updt_pnttm is '최종수정시점' ;
comment on column comtn_bbs_use.last_updusr_id is '최종수정자id' ;
comment on column comtn_bbs_use.regist_se_code is '등록구분코드' ;
comment on column comtn_bbs_use.trget_id is '대상id' ;
comment on column comtn_bbs_use.use_at is '사용여부' ;
comment on column comtn_comment.answer_no is '댓글번호' ;
comment on column comtn_comment.answer is '댓글' ;
comment on column comtn_comment.bbs_id is '게시판id' ;
comment on column comtn_comment.frst_regist_pnttm is '최초등록시점' ;
comment on column comtn_comment.frst_register_id is '최초등록자id' ;
comment on column comtn_comment.last_updt_pnttm is '최종수정시점' ;
comment on column comtn_comment.last_updusr_id is '최종수정자id' ;
comment on column comtn_comment.ntt_id is '게시물id' ;
comment on column comtn_comment.password is '비밀번호' ;
comment on column comtn_comment.use_at is '사용여부' ;
comment on column comtn_comment.wrter_id is '작성자id' ;
comment on column comtn_comment.wrter_nm is '작성자명' ;
comment on column comtn_rest_de.frst_regist_pnttm is '최초등록시점' ;
comment on column comtn_rest_de.frst_register_id is '최초등록자id' ;
comment on column comtn_rest_de.last_updt_pnttm is '최종수정시점' ;
comment on column comtn_rest_de.last_updusr_id is '최종수정자id' ;
comment on column comtn_rest_de.restde_dc is '휴일설명' ;
comment on column comtn_rest_de.restde_nm is '휴일명' ;
comment on column comtn_rest_de.restde_no is '휴일번호' ;
comment on column comtn_rest_de.restde_se_code is '휴일구분코드' ;
comment on column comtn_rest_de.restde is '휴일' ;
comment on column comtn_entrprs_mber.adres is '주소' ;
comment on column comtn_entrprs_mber.applcnt_email_adres is '신청자이메일주소' ;
comment on column comtn_entrprs_mber.applcnt_ihidnum is '신청인주민등록번호' ;
comment on column comtn_entrprs_mber.applcnt_nm is '신청인명' ;
comment on column comtn_entrprs_mber.area_no is '지역번호' ;
comment on column comtn_entrprs_mber.bizrno is '사업자등록번호' ;
comment on column comtn_entrprs_mber.cmpny_nm is '회사명' ;
comment on column comtn_entrprs_mber.cxfc is '대표이사' ;
comment on column comtn_entrprs_mber.detail_adres is '상세주소' ;
comment on column comtn_entrprs_mber.entrprs_end_telno is '기업끝전화번호' ;
comment on column comtn_entrprs_mber.entrprs_mber_id is '기업회원id' ;
comment on column comtn_entrprs_mber.entrprs_mber_password_cnsr is '기업회원비밀번호정답' ;
comment on column comtn_entrprs_mber.entrprs_mber_password_hint is '기업회원비밀번호힌트' ;
comment on column comtn_entrprs_mber.entrprs_mber_password is '기업회원비밀번호' ;
comment on column comtn_entrprs_mber.entrprs_mber_sttus is '기업회원상태' ;
comment on column comtn_entrprs_mber.entrprs_middle_telno is '기업중간전화번호' ;
comment on column comtn_entrprs_mber.entrprs_se_code is '기업구분코드' ;
comment on column comtn_entrprs_mber.esntl_id is '고유id' ;
comment on column comtn_entrprs_mber.fxnum is '팩스번호' ;
comment on column comtn_entrprs_mber.induty_code is '업종코드' ;
comment on column comtn_entrprs_mber.jurirno is '법인등록번호' ;
comment on column comtn_entrprs_mber.sbscrb_de is '가입일자' ;
comment on column comtn_entrprs_mber.zip is '우편번호' ;
comment on column comtn_login_policy.dplct_perm_at is '중복허용여부' ;
comment on column comtn_login_policy.emplyr_id is '업무사용자id' ;
comment on column comtn_login_policy.frst_regist_pnttm is '최초등록시점' ;
comment on column comtn_login_policy.frst_register_id is '최초등록자id' ;
comment on column comtn_login_policy.ip_info is 'ip정보' ;
comment on column comtn_login_policy.last_updt_pnttm is '최종수정시점' ;
comment on column comtn_login_policy.last_updusr_id is '최종수정자id' ;
comment on column comtn_login_policy.lmtt_at is '제한여부' ;
comment on column comtn_menu_info.menu_dc is '메뉴설명' ;
comment on column comtn_menu_info.menu_nm is '메뉴명' ;
comment on column comtn_menu_info.menu_no is '메뉴번호' ;
comment on column comtn_menu_info.menu_ordr is '메뉴순서' ;
comment on column comtn_menu_info.progrm_file_nm is '프로그램파일명' ;
comment on column comtn_menu_info.relate_image_nm is '관계이미지명' ;
comment on column comtn_menu_info.relate_image_path is '관계이미지경로' ;
comment on column comtn_menu_info.upper_menu_no is '상위메뉴번호' ;
comment on column comtn_emplyr_scrty_estbs.author_code is '권한코드' ;
comment on column comtn_emplyr_scrty_estbs.mber_ty_code is '회원유형코드' ;
comment on column comtn_emplyr_scrty_estbs.scrty_dtrmn_trget_id is '보안설정대상id' ;
comment on column comtn_emplyr_info.area_no is '지역번호' ;
comment on column comtn_emplyr_info.brthdy is '생일' ;
comment on column comtn_emplyr_info.crtfc_dn_value is '인증dn값' ;
comment on column comtn_emplyr_info.detail_adres is '상세주소' ;
comment on column comtn_emplyr_info.email_adres is '이메일주소' ;
comment on column comtn_emplyr_info.empl_no is '사원번호' ;
comment on column comtn_emplyr_info.emplyr_id is '업무사용자id' ;
comment on column comtn_emplyr_info.emplyr_sttus_code is '사용자상태코드' ;
comment on column comtn_emplyr_info.esntl_id is '고유id' ;
comment on column comtn_emplyr_info.fxnum is '팩스번호' ;
comment on column comtn_emplyr_info.house_adres is '주택주소' ;
comment on column comtn_emplyr_info.house_end_telno is '주택끝전화번호' ;
comment on column comtn_emplyr_info.house_middle_telno is '주택중간전화번호' ;
comment on column comtn_emplyr_info.ihidnum is '주민등록번호' ;
comment on column comtn_emplyr_info.mbtlnum is '이동전화번호' ;
comment on column comtn_emplyr_info.ofcps_nm is '직위명' ;
comment on column comtn_emplyr_info.offm_telno is '사무실전화번호' ;
comment on column comtn_emplyr_info.orgnzt_id is '조직id' ;
comment on column comtn_emplyr_info.password_cnsr is '비밀번호정답' ;
comment on column comtn_emplyr_info.password_hint is '비밀번호힌트' ;
comment on column comtn_emplyr_info.password is '비밀번호' ;
comment on column comtn_emplyr_info.pstinst_code is '소속기관코드' ;
comment on column comtn_emplyr_info.sbscrb_de is '가입일자' ;
comment on column comtn_emplyr_info.sexdstn_code is '성별코드' ;
comment on column comtn_emplyr_info.user_nm is '사용자명' ;
comment on column comtn_emplyr_info.zip is '우편번호' ;
comment on column comth_emplyr_info_change_dtls.area_no is '지역번호' ;
comment on column comth_emplyr_info_change_dtls.brthdy is '생일' ;
comment on column comth_emplyr_info_change_dtls.change_de is '변경일' ;
comment on column comth_emplyr_info_change_dtls.detail_adres is '상세주소' ;
comment on column comth_emplyr_info_change_dtls.email_adres is '이메일주소' ;
comment on column comth_emplyr_info_change_dtls.empl_no is '사원번호' ;
comment on column comth_emplyr_info_change_dtls.emplyr_id is '업무사용자id' ;
comment on column comth_emplyr_info_change_dtls.emplyr_sttus_code is '사용자상태코드' ;
comment on column comth_emplyr_info_change_dtls.esntl_id is '고유id' ;
comment on column comth_emplyr_info_change_dtls.fxnum is '팩스번호' ;
comment on column comth_emplyr_info_change_dtls.house_adres is '주택주소' ;
comment on column comth_emplyr_info_change_dtls.house_end_telno is '주택끝전화번호' ;
comment on column comth_emplyr_info_change_dtls.house_middle_telno is '주택중간전화번호' ;
comment on column comth_emplyr_info_change_dtls.mbtlnum is '이동전화번호' ;
comment on column comth_emplyr_info_change_dtls.offm_telno is '사무실전화번호' ;
comment on column comth_emplyr_info_change_dtls.orgnzt_id is '조직id' ;
comment on column comth_emplyr_info_change_dtls.pstinst_code is '소속기관코드' ;
comment on column comth_emplyr_info_change_dtls.sexdstn_code is '성별코드' ;
comment on column comth_emplyr_info_change_dtls.zip is '우편번호' ;
comment on column comtn_orgnzt_info.orgnzt_dc is '조직설명' ;
comment on column comtn_orgnzt_info.orgnzt_id is '조직id' ;
comment on column comtn_orgnzt_info.orgnzt_nm is '조직명' ;
comment on column comtn_faq_info.answer_cn is '답변내용' ;
comment on column comtn_faq_info.atch_file_id is '첨부파일id' ;
comment on column comtn_faq_info.faq_id is 'faqid' ;
comment on column comtn_faq_info.frst_regist_pnttm is '최초등록시점' ;
comment on column comtn_faq_info.frst_register_id is '최초등록자id' ;
comment on column comtn_faq_info.last_updt_pnttm is '최종수정시점' ;
comment on column comtn_faq_info.last_updusr_id is '최종수정자id' ;
comment on column comtn_faq_info.qestn_cn is '질문내용' ;
comment on column comtn_faq_info.qestn_sj is '질문제목' ;
comment on column comtn_faq_info.qna_process_sttus_code is '질의응답처리상태코드' ;
comment on column comtn_faq_info.rdcnt is '조회수' ;
comment on column comtn_qa_info.answer_cn is '답변내용' ;
comment on column comtn_qa_info.answer_de is '답변일자' ;
comment on column comtn_qa_info.area_no is '지역번호' ;
comment on column comtn_qa_info.email_adres is '이메일주소' ;
comment on column comtn_qa_info.email_answer_at is '메일답변여부' ;
comment on column comtn_qa_info.end_telno is '끝전화번호' ;
comment on column comtn_qa_info.frst_regist_pnttm is '최초등록시점' ;
comment on column comtn_qa_info.frst_register_id is '최초등록자id' ;
comment on column comtn_qa_info.last_updt_pnttm is '최종수정시점' ;
comment on column comtn_qa_info.last_updusr_id is '최종수정자id' ;
comment on column comtn_qa_info.middle_telno is '중간전화번호' ;
comment on column comtn_qa_info.qa_id is 'qaid' ;
comment on column comtn_qa_info.qestn_cn is '질문내용' ;
comment on column comtn_qa_info.qestn_sj is '질문제목' ;
comment on column comtn_qa_info.qna_process_sttus_code is '질의응답처리상태코드' ;
comment on column comtn_qa_info.rdcnt is '조회수' ;
comment on column comtn_qa_info.writng_de is '작성일' ;
comment on column comtn_qa_info.writng_password is '작성비밀번호' ;
comment on column comtn_qa_info.wrter_nm is '작성자명' ;
comment on column comtn_stplat_info.frst_regist_pnttm is '최초등록시점' ;
comment on column comtn_stplat_info.frst_register_id is '최초등록자id' ;
comment on column comtn_stplat_info.info_provd_agre_cn is '정보제공동의내용' ;
comment on column comtn_stplat_info.last_updt_pnttm is '최종수정시점' ;
comment on column comtn_stplat_info.last_updusr_id is '최종수정자id' ;
comment on column comtn_stplat_info.use_stplat_cn is '이용약관내용' ;
comment on column comtn_stplat_info.use_stplat_id is '이용약관id' ;
comment on column comtn_stplat_info.use_stplat_nm is '이용약관명' ;
comment on column comtn_word_dicary_info.eng_nm is '영문명' ;
comment on column comtn_word_dicary_info.frst_regist_pnttm is '최초등록시점' ;
comment on column comtn_word_dicary_info.frst_register_id is '최초등록자id' ;
comment on column comtn_word_dicary_info.last_updt_pnttm is '최종수정시점' ;
comment on column comtn_word_dicary_info.last_updusr_id is '최종수정자id' ;
comment on column comtn_word_dicary_info.synonm is '동의어' ;
comment on column comtn_word_dicary_info.word_dc is '용어설명' ;
comment on column comtn_word_dicary_info.word_id is '용어id' ;
comment on column comtn_word_dicary_info.word_nm is '용어명' ;
comment on column comtn_file.atch_file_id is '첨부파일id' ;
comment on column comtn_file.creat_dt is '생성일시' ;
comment on column comtn_file.use_at is '사용여부' ;
comment on column comtn_file_detail.atch_file_id is '첨부파일id' ;
comment on column comtn_file_detail.file_cn is '파일내용' ;
comment on column comtn_file_detail.file_extsn is '파일확장자' ;
comment on column comtn_file_detail.file_size is '파일크기' ;
comment on column comtn_file_detail.file_sn is '파일순번' ;
comment on column comtn_file_detail.file_stre_cours is '파일저장경로' ;
comment on column comtn_file_detail.orignl_file_nm is '원파일명' ;
comment on column comtn_file_detail.stre_file_nm is '저장파일명' ;
comment on column comtn_author_role_relate.author_code is '권한코드' ;
comment on column comtn_author_role_relate.creat_dt is '생성일시' ;
comment on column comtn_author_role_relate.role_code is '롤코드' ;
comment on column comtn_author_info.author_code is '권한코드' ;
comment on column comtn_author_info.author_creat_de is '권한생성일' ;
comment on column comtn_author_info.author_dc is '권한설명' ;
comment on column comtn_author_info.author_nm is '권한명' ;
comment on column comtn_roles_hierarchy.chldrn_role is '자식롤' ;
comment on column comtn_roles_hierarchy.parnts_role is '부모롤' ;
comment on column comtn_role_info.role_code is '롤코드' ;
comment on column comtn_role_info.role_creat_de is '롤생성일' ;
comment on column comtn_role_info.role_dc is '롤설명' ;
comment on column comtn_role_info.role_nm is '롤명' ;
comment on column comtn_role_info.role_pttrn is '롤패턴' ;
comment on column comtn_role_info.role_sort is '롤정렬' ;
comment on column comtn_role_info.role_ty is '롤유형' ;
comment on column comtc_cmmn_cl_code.cl_code_dc is '분류코드설명' ;
comment on column comtc_cmmn_cl_code.cl_code_nm is '분류코드명' ;
comment on column comtc_cmmn_cl_code.cl_code is '분류코드' ;
comment on column comtc_cmmn_cl_code.frst_regist_pnttm is '최초등록시점' ;
comment on column comtc_cmmn_cl_code.frst_register_id is '최초등록자id' ;
comment on column comtc_cmmn_cl_code.last_updt_pnttm is '최종수정시점' ;
comment on column comtc_cmmn_cl_code.last_updusr_id is '최종수정자id' ;
comment on column comtc_cmmn_cl_code.use_at is '사용여부' ;
comment on column comtc_cmmn_code.cl_code is '분류코드' ;
comment on column comtc_cmmn_code.code_id_dc is '코드id설명' ;
comment on column comtc_cmmn_code.code_id_nm is '코드id명' ;
comment on column comtc_cmmn_code.code_id is '코드id' ;
comment on column comtc_cmmn_code.frst_regist_pnttm is '최초등록시점' ;
comment on column comtc_cmmn_code.frst_register_id is '최초등록자id' ;
comment on column comtc_cmmn_code.last_updt_pnttm is '최종수정시점' ;
comment on column comtc_cmmn_code.last_updusr_id is '최종수정자id' ;
comment on column comtc_cmmn_code.use_at is '사용여부' ;
comment on column comtc_cmmn_code_detail.code_dc is '코드설명' ;
comment on column comtc_cmmn_code_detail.code_id is '코드id' ;
comment on column comtc_cmmn_code_detail.code_nm is '코드명' ;
comment on column comtc_cmmn_code_detail.code is '코드' ;
comment on column comtc_cmmn_code_detail.frst_regist_pnttm is '최초등록시점' ;
comment on column comtc_cmmn_code_detail.frst_register_id is '최초등록자id' ;
comment on column comtc_cmmn_code_detail.last_updt_pnttm is '최종수정시점' ;
comment on column comtc_cmmn_code_detail.last_updusr_id is '최종수정자id' ;
comment on column comtc_cmmn_code_detail.use_at is '사용여부' ;
comment on column comtr_dnm_adrzip.bdnbr_mnnm is '건물번호본번' ;
comment on column comtr_dnm_adrzip.bdnbr_slno is '건물번호부번' ;
comment on column comtr_dnm_adrzip.buld_nm is '건물명' ;
comment on column comtr_dnm_adrzip.ctprvn_nm is '시도명' ;
comment on column comtr_dnm_adrzip.detail_buld_nm is '상세건물명' ;
comment on column comtr_dnm_adrzip.frst_regist_pnttm is '최초등록시점' ;
comment on column comtr_dnm_adrzip.frst_register_id is '최초등록자id' ;
comment on column comtr_dnm_adrzip.last_updt_pnttm is '최종수정시점' ;
comment on column comtr_dnm_adrzip.last_updusr_id is '최종수정자id' ;
comment on column comtr_dnm_adrzip.rdmn_code is '도로명코드' ;
comment on column comtr_dnm_adrzip.rdmn is '도로명' ;
comment on column comtr_dnm_adrzip.signgu_nm is '시군구명' ;
comment on column comtr_dnm_adrzip.sn is '일련번호' ;
comment on column comtr_dnm_adrzip.zip is '우편번호' ;
comment on column comtc_zip.ctprvn_nm is '시도명' ;
comment on column comtc_zip.emd_nm is '읍면동명' ;
comment on column comtc_zip.frst_regist_pnttm is '최초등록시점' ;
comment on column comtc_zip.frst_register_id is '최초등록자id' ;
comment on column comtc_zip.last_updt_pnttm is '최종수정시점' ;
comment on column comtc_zip.last_updusr_id is '최종수정자id' ;
comment on column comtc_zip.li_buld_nm is '리건물명' ;
comment on column comtc_zip.lnbr_dong_ho is '번지동호' ;
comment on column comtc_zip.signgu_nm is '시군구명' ;
comment on column comtc_zip.sn is '일련번호' ;
comment on column comtc_zip.zip is '우편번호' ;
comment on column comtn_login_log.conect_id is '접속id' ;
comment on column comtn_login_log.conect_ip is '접속ip' ;
comment on column comtn_login_log.conect_mthd is '접속방식' ;
comment on column comtn_login_log.creat_dt is '생성일시' ;
comment on column comtn_login_log.error_code is '오류코드' ;
comment on column comtn_login_log.error_occrrnc_at is '오류발생여부' ;
comment on column comtn_login_log.log_id is '로그id' ;
comment on column comtn_progrm_list.progrm_dc is '프로그램설명' ;
comment on column comtn_progrm_list.progrm_file_nm is '프로그램파일명' ;
comment on column comtn_progrm_list.progrm_korean_nm is '프로그램한글명' ;
comment on column comtn_progrm_list.progrm_stre_path is '프로그램저장경로' ;
comment on column comtn_progrm_list.url is 'url' ;