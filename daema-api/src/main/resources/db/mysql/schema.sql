drop table if exists comte_cop_seq;
drop table if exists comtn_bbs;
drop table if exists comtn_bbsmaster;
drop table if exists comtn_bbsmaster_optn;
drop table if exists comtn_bbs_use;
drop table if exists comtn_comment;
drop table if exists comtn_rest_de;
drop table if exists comtn_entrprs_mber;
drop table if exists comtn_login_policy;
drop table if exists comtn_menu_info;
drop table if exists comtn_emplyr_scrty_estbs;
drop table if exists comtn_emplyr_info;
drop table if exists comth_emplyr_info_change_dtls;
drop table if exists comtn_orgnzt_info;
drop table if exists comvn_usermaster;
drop table if exists comtn_faq_info;
drop table if exists comtn_qa_info;
drop table if exists comtn_stplat_info;
drop table if exists comtn_word_dicary_info;
drop table if exists comtn_file;
drop table if exists comtn_file_detail;
drop table if exists comtn_author_role_relate;
drop table if exists comtn_author_info;
drop table if exists comtn_roles_hierarchy;
drop table if exists comtn_role_info;
drop table if exists comtc_cmmn_cl_code;
drop table if exists comtc_cmmn_code;
drop table if exists comtc_cmmn_code_detail;
drop table if exists comtr_dnm_adrzip;
drop table if exists comtc_zip;
drop table if exists comtn_login_log;
drop table if exists comtn_progrm_list;


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






alter table comte_cop_seq comment = 'comte_cop_seq';
alter table comtn_bbs comment = '게시판';
alter table comtn_bbsmaster comment = '게시판마스터';
alter table comtn_bbsmaster_optn comment = '게시판마스터옵션';
alter table comtn_bbs_use comment = '게시판활용';
alter table comtn_comment comment = '댓글';
alter table comtn_rest_de comment = '휴일관리';
alter table comtn_entrprs_mber comment = '기업회원';
alter table comtn_login_policy comment = '로그인정책';
alter table comtn_menu_info comment = '메뉴정보';
alter table comtn_emplyr_scrty_estbs comment = '사용자보안설정';
alter table comtn_emplyr_info comment = '업무사용자정보';
alter table comth_emplyr_info_change_dtls comment = '업무사용자정보변경내역';
alter table comtn_orgnzt_info comment = '조직정보';
alter table comtn_faq_info comment = 'faq정보';
alter table comtn_qa_info comment = 'qa정보';
alter table comtn_stplat_info comment = '약관정보';
alter table comtn_word_dicary_info comment = '용어사전정보';
alter table comtn_file comment = '파일속성';
alter table comtn_file_detail comment = '파일상세정보';
alter table comtn_author_role_relate comment = '권한롤관계';
alter table comtn_author_info comment = '권한정보';
alter table comtn_roles_hierarchy comment = '롤 계층구조';
alter table comtn_role_info comment = '롤정보';
alter table comtc_cmmn_cl_code comment = '공통분류코드';
alter table comtc_cmmn_code comment = '공통코드';
alter table comtc_cmmn_code_detail comment = '공통상세코드';
alter table comtr_dnm_adrzip comment = '도로명주소';
alter table comtc_zip comment = '우편번호';
alter table comtn_login_log comment = '접속로그';
alter table comtn_progrm_list comment = '프로그램목록';



alter table `comte_cop_seq` change `next_id` `next_id` decimal(30,0) default null  comment '다음아이디' ;
alter table `comte_cop_seq` change `table_name` `table_name` varchar(20) not null default ''  comment '테이블명' ;
alter table `comtn_bbs` change `answer_at` `answer_at` char(1) default null  comment '댓글여부' ;
alter table `comtn_bbs` change `answer_lc` `answer_lc` decimal(8,0) default null  comment '댓글위치' ;
alter table `comtn_bbs` change `atch_file_id` `atch_file_id` char(20) default null  comment '첨부파일id' ;
alter table `comtn_bbs` change `bbs_id` `bbs_id` char(20) not null  comment '게시판id' ;
alter table `comtn_bbs` change `frst_regist_pnttm` `frst_regist_pnttm` datetime not null  comment '최초등록시점' ;
alter table `comtn_bbs` change `frst_register_id` `frst_register_id` varchar(20) not null  comment '최초등록자id' ;
alter table `comtn_bbs` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtn_bbs` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtn_bbs` change `ntce_bgnde` `ntce_bgnde` char(20) default null  comment '게시시작일' ;
alter table `comtn_bbs` change `ntce_endde` `ntce_endde` char(20) default null  comment '게시종료일' ;
alter table `comtn_bbs` change `ntcr_id` `ntcr_id` varchar(20) default null  comment '게시자id' ;
alter table `comtn_bbs` change `ntcr_nm` `ntcr_nm` varchar(20) default null  comment '게시자명' ;
alter table `comtn_bbs` change `ntt_cn` `ntt_cn` mediumtext default null  comment '게시물내용' ;
alter table `comtn_bbs` change `ntt_id` `ntt_id` decimal(20,0) not null  comment '게시물id' ;
alter table `comtn_bbs` change `ntt_no` `ntt_no` decimal(20,0) default null  comment '게시물번호' ;
alter table `comtn_bbs` change `ntt_sj` `ntt_sj` varchar(2000) default null  comment '게시물제목' ;
alter table `comtn_bbs` change `parntsctt_no` `parntsctt_no` decimal(10,0) default null  comment '부모글번호' ;
alter table `comtn_bbs` change `password` `password` varchar(200) default null  comment '비밀번호' ;
alter table `comtn_bbs` change `rdcnt` `rdcnt` decimal(10,0) default null  comment '조회수' ;
alter table `comtn_bbs` change `sort_ordr` `sort_ordr` decimal(8,0) default null  comment '정렬순서' ;
alter table `comtn_bbs` change `use_at` `use_at` char(1) not null  comment '사용여부' ;
alter table `comtn_bbsmaster` change `atch_posbl_file_number` `atch_posbl_file_number` decimal(2,0) not null  comment '첨부가능파일숫자' ;
alter table `comtn_bbsmaster` change `atch_posbl_file_size` `atch_posbl_file_size` decimal(8,0) default null  comment '첨부가능파일사이즈' ;
alter table `comtn_bbsmaster` change `bbs_attrb_code` `bbs_attrb_code` char(6) not null  comment '게시판속성코드' ;
alter table `comtn_bbsmaster` change `bbs_id` `bbs_id` char(20) not null  comment '게시판id' ;
alter table `comtn_bbsmaster` change `bbs_intrcn` `bbs_intrcn` varchar(2400) default null  comment '게시판소개' ;
alter table `comtn_bbsmaster` change `bbs_nm` `bbs_nm` varchar(255) not null  comment '게시판명' ;
alter table `comtn_bbsmaster` change `bbs_ty_code` `bbs_ty_code` char(6) not null  comment '게시판유형코드' ;
alter table `comtn_bbsmaster` change `file_atch_posbl_at` `file_atch_posbl_at` char(1) not null  comment '파일첨부가능여부' ;
alter table `comtn_bbsmaster` change `frst_regist_pnttm` `frst_regist_pnttm` datetime not null  comment '최초등록시점' ;
alter table `comtn_bbsmaster` change `frst_register_id` `frst_register_id` varchar(20) not null  comment '최초등록자id' ;
alter table `comtn_bbsmaster` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtn_bbsmaster` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtn_bbsmaster` change `reply_posbl_at` `reply_posbl_at` char(1) default null  comment '답장가능여부' ;
alter table `comtn_bbsmaster` change `tmplat_id` `tmplat_id` char(20) default null  comment '템플릿id' ;
alter table `comtn_bbsmaster` change `use_at` `use_at` char(1) not null  comment '사용여부' ;
alter table `comtn_bbsmaster_optn` change `answer_at` `answer_at` char(1) not null  comment '댓글여부' ;
alter table `comtn_bbsmaster_optn` change `bbs_id` `bbs_id` char(20) not null  comment '게시판id' ;
alter table `comtn_bbsmaster_optn` change `frst_regist_pnttm` `frst_regist_pnttm` datetime not null  comment '최초등록시점' ;
alter table `comtn_bbsmaster_optn` change `frst_register_id` `frst_register_id` varchar(20) not null  comment '최초등록자id' ;
alter table `comtn_bbsmaster_optn` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtn_bbsmaster_optn` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtn_bbsmaster_optn` change `stsfdg_at` `stsfdg_at` char(1) not null  comment '만족도여부' ;
alter table `comtn_bbs_use` change `bbs_id` `bbs_id` char(20) not null  comment '게시판id' ;
alter table `comtn_bbs_use` change `frst_regist_pnttm` `frst_regist_pnttm` datetime default null  comment '최초등록시점' ;
alter table `comtn_bbs_use` change `frst_register_id` `frst_register_id` varchar(20) not null  comment '최초등록자id' ;
alter table `comtn_bbs_use` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtn_bbs_use` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtn_bbs_use` change `regist_se_code` `regist_se_code` char(6) default null  comment '등록구분코드' ;
alter table `comtn_bbs_use` change `trget_id` `trget_id` char(20) not null default ''  comment '대상id' ;
alter table `comtn_bbs_use` change `use_at` `use_at` char(1) not null  comment '사용여부' ;
alter table `comtn_comment` change `answer_no` `answer_no` decimal(20,0) not null  comment '댓글번호' ;
alter table `comtn_comment` change `answer` `answer` varchar(200) default null  comment '댓글' ;
alter table `comtn_comment` change `bbs_id` `bbs_id` char(20) not null  comment '게시판id' ;
alter table `comtn_comment` change `frst_regist_pnttm` `frst_regist_pnttm` datetime not null  comment '최초등록시점' ;
alter table `comtn_comment` change `frst_register_id` `frst_register_id` varchar(20) not null  comment '최초등록자id' ;
alter table `comtn_comment` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtn_comment` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtn_comment` change `ntt_id` `ntt_id` decimal(20,0) not null  comment '게시물id' ;
alter table `comtn_comment` change `password` `password` varchar(200) default null  comment '비밀번호' ;
alter table `comtn_comment` change `use_at` `use_at` char(1) not null  comment '사용여부' ;
alter table `comtn_comment` change `wrter_id` `wrter_id` varchar(20) default null  comment '작성자id' ;
alter table `comtn_comment` change `wrter_nm` `wrter_nm` varchar(20) default null  comment '작성자명' ;
alter table `comtn_rest_de` change `frst_regist_pnttm` `frst_regist_pnttm` datetime default null  comment '최초등록시점' ;
alter table `comtn_rest_de` change `frst_register_id` `frst_register_id` varchar(20) default null  comment '최초등록자id' ;
alter table `comtn_rest_de` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtn_rest_de` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtn_rest_de` change `restde_dc` `restde_dc` varchar(200) default null  comment '휴일설명' ;
alter table `comtn_rest_de` change `restde_nm` `restde_nm` varchar(60) default null  comment '휴일명' ;
alter table `comtn_rest_de` change `restde_no` `restde_no` decimal(6,0) not null  comment '휴일번호' ;
alter table `comtn_rest_de` change `restde_se_code` `restde_se_code` varchar(2) default null  comment '휴일구분코드' ;
alter table `comtn_rest_de` change `restde` `restde` char(8) default null  comment '휴일' ;
alter table `comtn_entrprs_mber` change `adres` `adres` varchar(100) not null  comment '주소' ;
alter table `comtn_entrprs_mber` change `applcnt_email_adres` `applcnt_email_adres` varchar(50) not null  comment '신청자이메일주소' ;
alter table `comtn_entrprs_mber` change `applcnt_ihidnum` `applcnt_ihidnum` varchar(200) default null  comment '신청인주민등록번호' ;
alter table `comtn_entrprs_mber` change `applcnt_nm` `applcnt_nm` varchar(50) not null  comment '신청인명' ;
alter table `comtn_entrprs_mber` change `area_no` `area_no` varchar(4) not null  comment '지역번호' ;
alter table `comtn_entrprs_mber` change `bizrno` `bizrno` varchar(10) default null  comment '사업자등록번호' ;
alter table `comtn_entrprs_mber` change `cmpny_nm` `cmpny_nm` varchar(60) not null  comment '회사명' ;
alter table `comtn_entrprs_mber` change `cxfc` `cxfc` varchar(50) default null  comment '대표이사' ;
alter table `comtn_entrprs_mber` change `detail_adres` `detail_adres` varchar(100) default null  comment '상세주소' ;
alter table `comtn_entrprs_mber` change `entrprs_end_telno` `entrprs_end_telno` varchar(4) not null  comment '기업끝전화번호' ;
alter table `comtn_entrprs_mber` change `entrprs_mber_id` `entrprs_mber_id` varchar(20) not null default ''  comment '기업회원id' ;
alter table `comtn_entrprs_mber` change `entrprs_mber_password_cnsr` `entrprs_mber_password_cnsr` varchar(100) not null  comment '기업회원비밀번호정답' ;
alter table `comtn_entrprs_mber` change `entrprs_mber_password_hint` `entrprs_mber_password_hint` varchar(100) not null  comment '기업회원비밀번호힌트' ;
alter table `comtn_entrprs_mber` change `entrprs_mber_password` `entrprs_mber_password` varchar(200) default null  comment '기업회원비밀번호' ;
alter table `comtn_entrprs_mber` change `entrprs_mber_sttus` `entrprs_mber_sttus` varchar(15) default null  comment '기업회원상태' ;
alter table `comtn_entrprs_mber` change `entrprs_middle_telno` `entrprs_middle_telno` varchar(4) not null  comment '기업중간전화번호' ;
alter table `comtn_entrprs_mber` change `entrprs_se_code` `entrprs_se_code` char(8) default null  comment '기업구분코드' ;
alter table `comtn_entrprs_mber` change `esntl_id` `esntl_id` char(20) not null  comment '고유id' ;
alter table `comtn_entrprs_mber` change `fxnum` `fxnum` varchar(20) default null  comment '팩스번호' ;
alter table `comtn_entrprs_mber` change `induty_code` `induty_code` char(1) default null  comment '업종코드' ;
alter table `comtn_entrprs_mber` change `jurirno` `jurirno` varchar(13) default null  comment '법인등록번호' ;
alter table `comtn_entrprs_mber` change `sbscrb_de` `sbscrb_de` datetime default null  comment '가입일자' ;
alter table `comtn_entrprs_mber` change `zip` `zip` varchar(6) not null  comment '우편번호' ;
alter table `comtn_login_policy` change `dplct_perm_at` `dplct_perm_at` char(1) not null  comment '중복허용여부' ;
alter table `comtn_login_policy` change `emplyr_id` `emplyr_id` varchar(20) not null default ''  comment '업무사용자id' ;
alter table `comtn_login_policy` change `frst_regist_pnttm` `frst_regist_pnttm` datetime default null  comment '최초등록시점' ;
alter table `comtn_login_policy` change `frst_register_id` `frst_register_id` varchar(20) default null  comment '최초등록자id' ;
alter table `comtn_login_policy` change `ip_info` `ip_info` varchar(23) not null  comment 'ip정보' ;
alter table `comtn_login_policy` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtn_login_policy` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtn_login_policy` change `lmtt_at` `lmtt_at` char(1) not null  comment '제한여부' ;
alter table `comtn_menu_info` change `menu_dc` `menu_dc` varchar(250) default null  comment '메뉴설명' ;
alter table `comtn_menu_info` change `menu_nm` `menu_nm` varchar(60) not null  comment '메뉴명' ;
alter table `comtn_menu_info` change `menu_no` `menu_no` decimal(20,0) not null  comment '메뉴번호' ;
alter table `comtn_menu_info` change `menu_ordr` `menu_ordr` decimal(5,0) not null  comment '메뉴순서' ;
alter table `comtn_menu_info` change `progrm_file_nm` `progrm_file_nm` varchar(60) not null  comment '프로그램파일명' ;
alter table `comtn_menu_info` change `relate_image_nm` `relate_image_nm` varchar(60) default null  comment '관계이미지명' ;
alter table `comtn_menu_info` change `relate_image_path` `relate_image_path` varchar(100) default null  comment '관계이미지경로' ;
alter table `comtn_menu_info` change `upper_menu_no` `upper_menu_no` decimal(20,0)  comment '상위메뉴번호' ;
alter table `comtn_emplyr_scrty_estbs` change `author_code` `author_code` varchar(30) not null  comment '권한코드' ;
alter table `comtn_emplyr_scrty_estbs` change `mber_ty_code` `mber_ty_code` char(5) default null  comment '회원유형코드' ;
alter table `comtn_emplyr_scrty_estbs` change `scrty_dtrmn_trget_id` `scrty_dtrmn_trget_id` varchar(20) not null  comment '보안설정대상id' ;
alter table `comtn_emplyr_info` change `area_no` `area_no` varchar(4) not null  comment '지역번호' ;
alter table `comtn_emplyr_info` change `brthdy` `brthdy` char(20) default null  comment '생일' ;
alter table `comtn_emplyr_info` change `crtfc_dn_value` `crtfc_dn_value` varchar(100) default null  comment '인증dn값' ;
alter table `comtn_emplyr_info` change `detail_adres` `detail_adres` varchar(100) default null  comment '상세주소' ;
alter table `comtn_emplyr_info` change `email_adres` `email_adres` varchar(50) default null  comment '이메일주소' ;
alter table `comtn_emplyr_info` change `empl_no` `empl_no` varchar(20) default null  comment '사원번호' ;
alter table `comtn_emplyr_info` change `emplyr_id` `emplyr_id` varchar(20) not null  comment '업무사용자id' ;
alter table `comtn_emplyr_info` change `emplyr_sttus_code` `emplyr_sttus_code` char(1) not null  comment '사용자상태코드' ;
alter table `comtn_emplyr_info` change `esntl_id` `esntl_id` char(20) not null  comment '고유id' ;
alter table `comtn_emplyr_info` change `fxnum` `fxnum` varchar(20) default null  comment '팩스번호' ;
alter table `comtn_emplyr_info` change `house_adres` `house_adres` varchar(100) not null  comment '주택주소' ;
alter table `comtn_emplyr_info` change `house_end_telno` `house_end_telno` varchar(4) not null  comment '주택끝전화번호' ;
alter table `comtn_emplyr_info` change `house_middle_telno` `house_middle_telno` varchar(4) not null  comment '주택중간전화번호' ;
alter table `comtn_emplyr_info` change `ihidnum` `ihidnum` varchar(200) default null  comment '주민등록번호' ;
alter table `comtn_emplyr_info` change `mbtlnum` `mbtlnum` varchar(20) default null  comment '이동전화번호' ;
alter table `comtn_emplyr_info` change `ofcps_nm` `ofcps_nm` varchar(60) default null  comment '직위명' ;
alter table `comtn_emplyr_info` change `offm_telno` `offm_telno` varchar(20) default null  comment '사무실전화번호' ;
alter table `comtn_emplyr_info` change `orgnzt_id` `orgnzt_id` char(20)  comment '조직id' ;
alter table `comtn_emplyr_info` change `password_cnsr` `password_cnsr` varchar(100) not null  comment '비밀번호정답' ;
alter table `comtn_emplyr_info` change `password_hint` `password_hint` varchar(100) not null  comment '비밀번호힌트' ;
alter table `comtn_emplyr_info` change `password` `password` varchar(200) not null  comment '비밀번호' ;
alter table `comtn_emplyr_info` change `pstinst_code` `pstinst_code` char(8) default null  comment '소속기관코드' ;
alter table `comtn_emplyr_info` change `sbscrb_de` `sbscrb_de` datetime default null  comment '가입일자' ;
alter table `comtn_emplyr_info` change `sexdstn_code` `sexdstn_code` char(1) default null  comment '성별코드' ;
alter table `comtn_emplyr_info` change `user_nm` `user_nm` varchar(60) not null  comment '사용자명' ;
alter table `comtn_emplyr_info` change `zip` `zip` varchar(6) not null  comment '우편번호' ;
alter table `comth_emplyr_info_change_dtls` change `area_no` `area_no` varchar(4) default null  comment '지역번호' ;
alter table `comth_emplyr_info_change_dtls` change `brthdy` `brthdy` char(20) default null  comment '생일' ;
alter table `comth_emplyr_info_change_dtls` change `change_de` `change_de` char(20) not null default ''  comment '변경일' ;
alter table `comth_emplyr_info_change_dtls` change `detail_adres` `detail_adres` varchar(100) default null  comment '상세주소' ;
alter table `comth_emplyr_info_change_dtls` change `email_adres` `email_adres` varchar(50) default null  comment '이메일주소' ;
alter table `comth_emplyr_info_change_dtls` change `empl_no` `empl_no` varchar(20) default null  comment '사원번호' ;
alter table `comth_emplyr_info_change_dtls` change `emplyr_id` `emplyr_id` varchar(20) not null  comment '업무사용자id' ;
alter table `comth_emplyr_info_change_dtls` change `emplyr_sttus_code` `emplyr_sttus_code` char(1) default null  comment '사용자상태코드' ;
alter table `comth_emplyr_info_change_dtls` change `esntl_id` `esntl_id` char(20) default null  comment '고유id' ;
alter table `comth_emplyr_info_change_dtls` change `fxnum` `fxnum` varchar(20) default null  comment '팩스번호' ;
alter table `comth_emplyr_info_change_dtls` change `house_adres` `house_adres` varchar(100) default null  comment '주택주소' ;
alter table `comth_emplyr_info_change_dtls` change `house_end_telno` `house_end_telno` varchar(4) default null  comment '주택끝전화번호' ;
alter table `comth_emplyr_info_change_dtls` change `house_middle_telno` `house_middle_telno` varchar(4) default null  comment '주택중간전화번호' ;
alter table `comth_emplyr_info_change_dtls` change `mbtlnum` `mbtlnum` varchar(20) default null  comment '이동전화번호' ;
alter table `comth_emplyr_info_change_dtls` change `offm_telno` `offm_telno` varchar(20) default null  comment '사무실전화번호' ;
alter table `comth_emplyr_info_change_dtls` change `orgnzt_id` `orgnzt_id` char(20) default null  comment '조직id' ;
alter table `comth_emplyr_info_change_dtls` change `pstinst_code` `pstinst_code` char(8) default null  comment '소속기관코드' ;
alter table `comth_emplyr_info_change_dtls` change `sexdstn_code` `sexdstn_code` char(1) default null  comment '성별코드' ;
alter table `comth_emplyr_info_change_dtls` change `zip` `zip` varchar(6) default null  comment '우편번호' ;
alter table `comtn_orgnzt_info` change `orgnzt_dc` `orgnzt_dc` varchar(100) default null  comment '조직설명' ;
alter table `comtn_orgnzt_info` change `orgnzt_id` `orgnzt_id` char(20) not null default ''  comment '조직id' ;
alter table `comtn_orgnzt_info` change `orgnzt_nm` `orgnzt_nm` varchar(20) not null  comment '조직명' ;
alter table `comtn_faq_info` change `answer_cn` `answer_cn` varchar(2500) default null  comment '답변내용' ;
alter table `comtn_faq_info` change `atch_file_id` `atch_file_id` char(20)  comment '첨부파일id' ;
alter table `comtn_faq_info` change `faq_id` `faq_id` char(20) not null  comment 'faqid' ;
alter table `comtn_faq_info` change `frst_regist_pnttm` `frst_regist_pnttm` datetime not null  comment '최초등록시점' ;
alter table `comtn_faq_info` change `frst_register_id` `frst_register_id` varchar(20) not null  comment '최초등록자id' ;
alter table `comtn_faq_info` change `last_updt_pnttm` `last_updt_pnttm` datetime not null  comment '최종수정시점' ;
alter table `comtn_faq_info` change `last_updusr_id` `last_updusr_id` varchar(20) not null  comment '최종수정자id' ;
alter table `comtn_faq_info` change `qestn_cn` `qestn_cn` varchar(2500) default null  comment '질문내용' ;
alter table `comtn_faq_info` change `qestn_sj` `qestn_sj` varchar(255) default null  comment '질문제목' ;
alter table `comtn_faq_info` change `qna_process_sttus_code` `qna_process_sttus_code` char(1) default null  comment '질의응답처리상태코드' ;
alter table `comtn_faq_info` change `rdcnt` `rdcnt` decimal(10,0) default null  comment '조회수' ;
alter table `comtn_qa_info` change `answer_cn` `answer_cn` varchar(2500) default null  comment '답변내용' ;
alter table `comtn_qa_info` change `answer_de` `answer_de` char(20) default null  comment '답변일자' ;
alter table `comtn_qa_info` change `area_no` `area_no` varchar(4) default null  comment '지역번호' ;
alter table `comtn_qa_info` change `email_adres` `email_adres` varchar(50) default null  comment '이메일주소' ;
alter table `comtn_qa_info` change `email_answer_at` `email_answer_at` char(1) default null  comment '메일답변여부' ;
alter table `comtn_qa_info` change `end_telno` `end_telno` varchar(4) default null  comment '끝전화번호' ;
alter table `comtn_qa_info` change `frst_regist_pnttm` `frst_regist_pnttm` datetime default null  comment '최초등록시점' ;
alter table `comtn_qa_info` change `frst_register_id` `frst_register_id` varchar(20) default null  comment '최초등록자id' ;
alter table `comtn_qa_info` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtn_qa_info` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtn_qa_info` change `middle_telno` `middle_telno` varchar(4) default null  comment '중간전화번호' ;
alter table `comtn_qa_info` change `qa_id` `qa_id` char(20) not null  comment 'qaid' ;
alter table `comtn_qa_info` change `qestn_cn` `qestn_cn` varchar(2500) default null  comment '질문내용' ;
alter table `comtn_qa_info` change `qestn_sj` `qestn_sj` varchar(255) default null  comment '질문제목' ;
alter table `comtn_qa_info` change `qna_process_sttus_code` `qna_process_sttus_code` char(1) default null  comment '질의응답처리상태코드' ;
alter table `comtn_qa_info` change `rdcnt` `rdcnt` decimal(10,0) default null  comment '조회수' ;
alter table `comtn_qa_info` change `writng_de` `writng_de` char(20) default null  comment '작성일' ;
alter table `comtn_qa_info` change `writng_password` `writng_password` varchar(20) default null  comment '작성비밀번호' ;
alter table `comtn_qa_info` change `wrter_nm` `wrter_nm` varchar(20) default null  comment '작성자명' ;
alter table `comtn_stplat_info` change `frst_regist_pnttm` `frst_regist_pnttm` datetime default null  comment '최초등록시점' ;
alter table `comtn_stplat_info` change `frst_register_id` `frst_register_id` varchar(20) default null  comment '최초등록자id' ;
alter table `comtn_stplat_info` change `info_provd_agre_cn` `info_provd_agre_cn` mediumtext default null  comment '정보제공동의내용' ;
alter table `comtn_stplat_info` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtn_stplat_info` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtn_stplat_info` change `use_stplat_cn` `use_stplat_cn` mediumtext default null  comment '이용약관내용' ;
alter table `comtn_stplat_info` change `use_stplat_id` `use_stplat_id` char(20) not null  comment '이용약관id' ;
alter table `comtn_stplat_info` change `use_stplat_nm` `use_stplat_nm` varchar(100) default null  comment '이용약관명' ;
alter table `comtn_word_dicary_info` change `eng_nm` `eng_nm` varchar(60) default null  comment '영문명' ;
alter table `comtn_word_dicary_info` change `frst_regist_pnttm` `frst_regist_pnttm` datetime default null  comment '최초등록시점' ;
alter table `comtn_word_dicary_info` change `frst_register_id` `frst_register_id` varchar(20) default null  comment '최초등록자id' ;
alter table `comtn_word_dicary_info` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtn_word_dicary_info` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtn_word_dicary_info` change `synonm` `synonm` varchar(100) default null  comment '동의어' ;
alter table `comtn_word_dicary_info` change `word_dc` `word_dc` varchar(4000) default null  comment '용어설명' ;
alter table `comtn_word_dicary_info` change `word_id` `word_id` char(20) not null  comment '용어id' ;
alter table `comtn_word_dicary_info` change `word_nm` `word_nm` varchar(255) default null  comment '용어명' ;
alter table `comtn_file` change `atch_file_id` `atch_file_id` char(20) not null  comment '첨부파일id' ;
alter table `comtn_file` change `creat_dt` `creat_dt` datetime not null  comment '생성일시' ;
alter table `comtn_file` change `use_at` `use_at` char(1) default null  comment '사용여부' ;
alter table `comtn_file_detail` change `atch_file_id` `atch_file_id` char(20) not null  comment '첨부파일id' ;
alter table `comtn_file_detail` change `file_cn` `file_cn` mediumtext default null  comment '파일내용' ;
alter table `comtn_file_detail` change `file_extsn` `file_extsn` varchar(20) not null  comment '파일확장자' ;
alter table `comtn_file_detail` change `file_size` `file_size` decimal(8,0) default null  comment '파일크기' ;
alter table `comtn_file_detail` change `file_sn` `file_sn` decimal(10,0) not null  comment '파일순번' ;
alter table `comtn_file_detail` change `file_stre_cours` `file_stre_cours` varchar(2000) not null  comment '파일저장경로' ;
alter table `comtn_file_detail` change `orignl_file_nm` `orignl_file_nm` varchar(255) default null  comment '원파일명' ;
alter table `comtn_file_detail` change `stre_file_nm` `stre_file_nm` varchar(255) not null  comment '저장파일명' ;
alter table `comtn_author_role_relate` change `author_code` `author_code` varchar(30) not null  comment '권한코드' ;
alter table `comtn_author_role_relate` change `creat_dt` `creat_dt` datetime default null  comment '생성일시' ;
alter table `comtn_author_role_relate` change `role_code` `role_code` varchar(50) not null  comment '롤코드' ;
alter table `comtn_author_info` change `author_code` `author_code` varchar(30) not null default ''  comment '권한코드' ;
alter table `comtn_author_info` change `author_creat_de` `author_creat_de` char(20) not null  comment '권한생성일' ;
alter table `comtn_author_info` change `author_dc` `author_dc` varchar(200) default null  comment '권한설명' ;
alter table `comtn_author_info` change `author_nm` `author_nm` varchar(60) not null  comment '권한명' ;
alter table `comtn_roles_hierarchy` change `chldrn_role` `chldrn_role` varchar(30) not null  comment '자식롤' ;
alter table `comtn_roles_hierarchy` change `parnts_role` `parnts_role` varchar(30) not null  comment '부모롤' ;
alter table `comtn_role_info` change `role_code` `role_code` varchar(50) not null default ''  comment '롤코드' ;
alter table `comtn_role_info` change `role_creat_de` `role_creat_de` char(20) not null  comment '롤생성일' ;
alter table `comtn_role_info` change `role_dc` `role_dc` varchar(200) default null  comment '롤설명' ;
alter table `comtn_role_info` change `role_nm` `role_nm` varchar(60) not null  comment '롤명' ;
alter table `comtn_role_info` change `role_pttrn` `role_pttrn` varchar(300) default null  comment '롤패턴' ;
alter table `comtn_role_info` change `role_sort` `role_sort` varchar(10) default null  comment '롤정렬' ;
alter table `comtn_role_info` change `role_ty` `role_ty` varchar(80) default null  comment '롤유형' ;
alter table `comtc_cmmn_cl_code` change `cl_code_dc` `cl_code_dc` varchar(200) default null  comment '분류코드설명' ;
alter table `comtc_cmmn_cl_code` change `cl_code_nm` `cl_code_nm` varchar(60) default null  comment '분류코드명' ;
alter table `comtc_cmmn_cl_code` change `cl_code` `cl_code` char(3) not null  comment '분류코드' ;
alter table `comtc_cmmn_cl_code` change `frst_regist_pnttm` `frst_regist_pnttm` datetime default null  comment '최초등록시점' ;
alter table `comtc_cmmn_cl_code` change `frst_register_id` `frst_register_id` varchar(20) default null  comment '최초등록자id' ;
alter table `comtc_cmmn_cl_code` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtc_cmmn_cl_code` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtc_cmmn_cl_code` change `use_at` `use_at` char(1) default null  comment '사용여부' ;
alter table `comtc_cmmn_code` change `cl_code` `cl_code` char(3)  comment '분류코드' ;
alter table `comtc_cmmn_code` change `code_id_dc` `code_id_dc` varchar(200) default null  comment '코드id설명' ;
alter table `comtc_cmmn_code` change `code_id_nm` `code_id_nm` varchar(60) default null  comment '코드id명' ;
alter table `comtc_cmmn_code` change `code_id` `code_id` varchar(6) not null  comment '코드id' ;
alter table `comtc_cmmn_code` change `frst_regist_pnttm` `frst_regist_pnttm` datetime default null  comment '최초등록시점' ;
alter table `comtc_cmmn_code` change `frst_register_id` `frst_register_id` varchar(20) default null  comment '최초등록자id' ;
alter table `comtc_cmmn_code` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtc_cmmn_code` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtc_cmmn_code` change `use_at` `use_at` char(1) default null  comment '사용여부' ;
alter table `comtc_cmmn_code_detail` change `code_dc` `code_dc` varchar(200) default null  comment '코드설명' ;
alter table `comtc_cmmn_code_detail` change `code_id` `code_id` varchar(6) not null  comment '코드id' ;
alter table `comtc_cmmn_code_detail` change `code_nm` `code_nm` varchar(60) default null  comment '코드명' ;
alter table `comtc_cmmn_code_detail` change `code` `code` varchar(15) not null  comment '코드' ;
alter table `comtc_cmmn_code_detail` change `frst_regist_pnttm` `frst_regist_pnttm` datetime default null  comment '최초등록시점' ;
alter table `comtc_cmmn_code_detail` change `frst_register_id` `frst_register_id` varchar(20) default null  comment '최초등록자id' ;
alter table `comtc_cmmn_code_detail` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtc_cmmn_code_detail` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtc_cmmn_code_detail` change `use_at` `use_at` char(1) default null  comment '사용여부' ;
alter table `comtr_dnm_adrzip` change `bdnbr_mnnm` `bdnbr_mnnm` varchar(5) default null  comment '건물번호본번' ;
alter table `comtr_dnm_adrzip` change `bdnbr_slno` `bdnbr_slno` varchar(5) default null  comment '건물번호부번' ;
alter table `comtr_dnm_adrzip` change `buld_nm` `buld_nm` varchar(60) default null  comment '건물명' ;
alter table `comtr_dnm_adrzip` change `ctprvn_nm` `ctprvn_nm` varchar(20) default null  comment '시도명' ;
alter table `comtr_dnm_adrzip` change `detail_buld_nm` `detail_buld_nm` varchar(60) default null  comment '상세건물명' ;
alter table `comtr_dnm_adrzip` change `frst_regist_pnttm` `frst_regist_pnttm` datetime default null  comment '최초등록시점' ;
alter table `comtr_dnm_adrzip` change `frst_register_id` `frst_register_id` varchar(20) default null  comment '최초등록자id' ;
alter table `comtr_dnm_adrzip` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtr_dnm_adrzip` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtr_dnm_adrzip` change `rdmn_code` `rdmn_code` varchar(12) not null  comment '도로명코드' ;
alter table `comtr_dnm_adrzip` change `rdmn` `rdmn` varchar(60) default null  comment '도로명' ;
alter table `comtr_dnm_adrzip` change `signgu_nm` `signgu_nm` varchar(20) default null  comment '시군구명' ;
alter table `comtr_dnm_adrzip` change `sn` `sn` decimal(10,0) not null  comment '일련번호' ;
alter table `comtr_dnm_adrzip` change `zip` `zip` varchar(6) not null  comment '우편번호' ;
alter table `comtc_zip` change `ctprvn_nm` `ctprvn_nm` varchar(20) default null  comment '시도명' ;
alter table `comtc_zip` change `emd_nm` `emd_nm` varchar(60) default null  comment '읍면동명' ;
alter table `comtc_zip` change `frst_regist_pnttm` `frst_regist_pnttm` datetime default null  comment '최초등록시점' ;
alter table `comtc_zip` change `frst_register_id` `frst_register_id` varchar(20) default null  comment '최초등록자id' ;
alter table `comtc_zip` change `last_updt_pnttm` `last_updt_pnttm` datetime default null  comment '최종수정시점' ;
alter table `comtc_zip` change `last_updusr_id` `last_updusr_id` varchar(20) default null  comment '최종수정자id' ;
alter table `comtc_zip` change `li_buld_nm` `li_buld_nm` varchar(60) default null  comment '리건물명' ;
alter table `comtc_zip` change `lnbr_dong_ho` `lnbr_dong_ho` varchar(20) default null  comment '번지동호' ;
alter table `comtc_zip` change `signgu_nm` `signgu_nm` varchar(20) default null  comment '시군구명' ;
alter table `comtc_zip` change `sn` `sn` decimal(10,0) not null default '0'  comment '일련번호' ;
alter table `comtc_zip` change `zip` `zip` varchar(6) not null  comment '우편번호' ;
alter table `comtn_login_log` change `conect_id` `conect_id` varchar(20) default null  comment '접속id' ;
alter table `comtn_login_log` change `conect_ip` `conect_ip` varchar(23) default null  comment '접속ip' ;
alter table `comtn_login_log` change `conect_mthd` `conect_mthd` char(4) default null  comment '접속방식' ;
alter table `comtn_login_log` change `creat_dt` `creat_dt` datetime default null  comment '생성일시' ;
alter table `comtn_login_log` change `error_code` `error_code` char(3) default null  comment '오류코드' ;
alter table `comtn_login_log` change `error_occrrnc_at` `error_occrrnc_at` char(1) default null  comment '오류발생여부' ;
alter table `comtn_login_log` change `log_id` `log_id` char(20) not null  comment '로그id' ;
alter table `comtn_progrm_list` change `progrm_dc` `progrm_dc` varchar(200) default null  comment '프로그램설명' ;
alter table `comtn_progrm_list` change `progrm_file_nm` `progrm_file_nm` varchar(60) not null default ''  comment '프로그램파일명' ;
alter table `comtn_progrm_list` change `progrm_korean_nm` `progrm_korean_nm` varchar(60) default null  comment '프로그램한글명' ;
alter table `comtn_progrm_list` change `progrm_stre_path` `progrm_stre_path` varchar(100) not null  comment '프로그램저장경로' ;
alter table `comtn_progrm_list` change `url` `url` varchar(100) not null  comment 'url' ;




