package com.daema.rest.commgmt.service;

import com.daema.core.base.domain.Members;
import com.daema.core.base.enums.StatusEnum;
import com.daema.core.base.enums.UserRole;
import com.daema.core.base.repository.MemberRepository;
import com.daema.core.base.repository.MemberRoleRepository;
import com.daema.core.commgmt.domain.MembersRole;
import com.daema.core.commgmt.domain.Organization;
import com.daema.core.commgmt.domain.RoleMgmt;
import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;
import com.daema.core.commgmt.dto.response.OrgnztListDto;
import com.daema.core.commgmt.dto.response.OrgnztMemberListDto;
import com.daema.core.commgmt.repository.OrganizationRepository;
import com.daema.rest.base.service.AuthService;
import com.daema.core.commgmt.dto.FuncRoleMgmtDto;
import com.daema.core.commgmt.dto.OrganizationMemberDto;
import com.daema.core.commgmt.dto.OrganizationMgmtDto;
import com.daema.core.commgmt.dto.response.OrganizationMgmtResponseDto;
import com.daema.rest.common.consts.Constants;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.common.util.JwtUtil;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.daema.core.commgmt.dto.response.OrganizationMgmtResponseDto.OrgnztMemberAndRoleDto;

@Service
public class OrganizationMgmtService {

    private final OrganizationRepository organizationRepository;

    private final MemberRepository memberRepository;

    private final MemberRoleRepository memberRoleRepository;

    private final AuthService authService;

    private final RoleFuncMgmtService roleFuncMgmtService;

    private final AuthenticationUtil authenticationUtil;

    private final JwtUtil jwtUtil;

    public OrganizationMgmtService(OrganizationRepository organizationRepository, MemberRepository memberRepository, MemberRoleRepository memberRoleRepository
            ,AuthService authService, RoleFuncMgmtService roleFuncMgmtService, AuthenticationUtil authenticationUtil
    ,JwtUtil jwtUtil) {
        this.organizationRepository = organizationRepository;
        this.memberRepository = memberRepository;
        this.memberRoleRepository = memberRoleRepository;
        this.authService = authService;
        this.roleFuncMgmtService = roleFuncMgmtService;
        this.authenticationUtil = authenticationUtil;
        this.jwtUtil = jwtUtil;
    }

    public OrganizationMgmtResponseDto getOrgnztList(ComMgmtRequestDto requestDto) {

        requestDto.setStoreId(authenticationUtil.getTargetStoreId(requestDto.getStoreId()));

        HashMap<String, List> orgnztMemberListMap = organizationRepository.getOrgnztAndMemberList(requestDto);

        OrganizationMgmtResponseDto responseDto = new OrganizationMgmtResponseDto();

        List<OrgnztListDto> dataList = orgnztMemberListMap.get("orgnztList");
        List<OrganizationMgmtDto> orgnztList = new ArrayList<>();

        for(Iterator<OrgnztListDto> iterator = dataList.iterator(); iterator.hasNext();){

            OrgnztListDto orgnztDto = iterator.next();

            if(StringUtils.countOccurrencesOf(orgnztDto.getHierarchy(), "/") == 1){

                orgnztList.add(orgnztList.size(), OrganizationMgmtDto.dtoToDto(orgnztDto));

            }else if(StringUtils.countOccurrencesOf(orgnztDto.getHierarchy(), "/") == 2){

                addChildrenElementToOrgnzt(orgnztList.get(orgnztList.size() - 1), orgnztDto);

            }else if(StringUtils.countOccurrencesOf(orgnztDto.getHierarchy(), "/") == 3){

                addChildrenElementToOrgnzt(orgnztList.get(orgnztList.size() - 1).getChildren()
                        .get(orgnztList.get(orgnztList.size() - 1).getChildren().size() - 1), orgnztDto);
            }
        }

        responseDto.setOrgnztList(orgnztList);

        List<OrgnztMemberAndRoleDto> mList = new ArrayList<>();

        if(CommonUtil.isNotEmptyList(orgnztMemberListMap.get("memberList"))){

            orgnztMemberListMap.get("memberList").forEach(
                member -> {
                    mList.add(new OrgnztMemberAndRoleDto((OrgnztMemberListDto) member
                                    ,((List<MembersRole>) orgnztMemberListMap.get("memberRoleList"))
                                    .stream()
                                    .filter(roles -> roles.getSeq() == ((OrgnztMemberListDto) member).getSeq())
                                    .map(MembersRole::getRoleId)
                                    .collect(Collectors.toList()))
                    );
                }
            );
        }

        responseDto.setMemberList(mList);

        List<RoleMgmt> roleList = roleFuncMgmtService.getRoleList(requestDto.getStoreId());

        List<FuncRoleMgmtDto.RoleMgmtDto> roleDtoList = roleList.stream()
                .map(FuncRoleMgmtDto.RoleMgmtDto::from)
                .collect(Collectors.toList());

        responseDto.setStoreRoleList(roleDtoList);

        return responseDto;
    }

    private void addChildrenElementToOrgnzt(OrganizationMgmtDto parent, OrgnztListDto child){
        if(parent.getChildren() == null){
            parent.setChildren(new ArrayList<>());
        }

        parent.getChildren().add(OrganizationMgmtDto.dtoToDto(child));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public long insertOrgnzt(OrganizationMgmtDto organizationMgmtDto) {
        //TODO ifnull return ?????? ??????
        return organizationRepository.save(
                Organization.builder()
                        .orgName(organizationMgmtDto.getOrgName())
                        .parentOrgId(organizationMgmtDto.getParentOrgId())
                        .storeId(authenticationUtil.getTargetStoreId(organizationMgmtDto.getStoreId()))
                        .delYn(StatusEnum.FLAG_N.getStatusMsg())
                        .build()
        ).getOrgId();
    }

    @Transactional
    public void updateOrgnzt(OrganizationMgmtDto organizationMgmtDto) {

        Organization orgnzt = organizationRepository.findById(organizationMgmtDto.getOrgId()).orElse(null);
        long storeId = authenticationUtil.getTargetStoreId(organizationMgmtDto.getStoreId());

        if(orgnzt != null
            && orgnzt.getStoreId() == storeId) {
            //TODO ifnull return ?????? ??????
            if(StringUtils.hasText(organizationMgmtDto.getOrgName())) {
                orgnzt.setOrgName(organizationMgmtDto.getOrgName());
            }
        }else{
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    @Transactional
    public void deleteOrgnzt(OrganizationMgmtDto organizationMgmtDto) {

        Organization orgnzt = organizationRepository.findById(organizationMgmtDto.getOrgId()).orElse(null);
        long storeId = authenticationUtil.getTargetStoreId(organizationMgmtDto.getStoreId());

        if(orgnzt != null
                && orgnzt.getStoreId() == storeId) {
            orgnzt.setDelYn(StatusEnum.FLAG_Y.getStatusMsg());

            List<Members> membersList = memberRepository.findByOrgId(orgnzt.getOrgId());

            if(CommonUtil.isNotEmptyList(membersList)) {
                Organization orgnztBasicData = organizationRepository.findByStoreIdAndOrgName(orgnzt.getStoreId(), Constants.ORGANIZATION_DEFAULT_GROUP_NAME);

                if (orgnztBasicData != null) {
                    Optional.ofNullable(membersList).orElseGet(Collections::emptyList).forEach(members -> {
                        members.updateOrgnztId(members, orgnztBasicData.getOrgId());
                    });
                }
            }
        }else{
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    /**
     *
     * @param orgnztMemberDto
     * @param request
     * @param getToken : token ??? ???????????? store, org ?????? ???????????? ??????
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public long insertUser(OrganizationMemberDto orgnztMemberDto, HttpServletRequest request, boolean getToken) {

        if(String.valueOf(StatusEnum.USER_APPROVAL.getStatusCode())
                .equals(orgnztMemberDto.getUserStatus())){
            if(UserRole.ROLE_NOT_PERMITTED == orgnztMemberDto.getRole()){
                orgnztMemberDto.setRole(UserRole.ROLE_USER);
            }
        }

        //?????? ????????? ?????? ????????? ?????? ????????????
        String accessToken = jwtUtil.getAccessTokenFromHeader(request, JwtUtil.AUTHORIZATION);

        if(StringUtils.hasText(accessToken)
                && getToken){

            Long storeId = (Long) jwtUtil.getClaim(accessToken, "sId", Long.class);
            Long orgId = (Long) jwtUtil.getClaim(accessToken, "gId", Long.class);

            if(storeId != null
                    && storeId > 0) {
                orgnztMemberDto.setStoreId(storeId);
            }

            if(orgId != null
                    && orgId > 0) {
                orgnztMemberDto.setOrgId(orgId);
            }
        }

        authService.signUpUser(Members.builder()
                .username(orgnztMemberDto.getUsername())
                .password(orgnztMemberDto.getPassword())
                .name(orgnztMemberDto.getName())
                .email(orgnztMemberDto.getEmail())
                .address(orgnztMemberDto.getAddress())
                .phone(
                        orgnztMemberDto.getPhone1()
                        .concat(orgnztMemberDto.getPhone2())
                        .concat(orgnztMemberDto.getPhone3())
                )
                .phone1(orgnztMemberDto.getPhone1())
                .phone2(orgnztMemberDto.getPhone2())
                .phone3(orgnztMemberDto.getPhone3())
                .regiDatetime(LocalDateTime.now())
                .role(orgnztMemberDto.getRole())
                .updDatetime(LocalDateTime.now())
                .storeId(authenticationUtil.getTargetStoreId(orgnztMemberDto.getStoreId()))
                .orgId(orgnztMemberDto.getOrgId())
                .userStatus(orgnztMemberDto.getUserStatus())
            .build());

        long seq = memberRepository.findByUsername(orgnztMemberDto.getUsername()).getSeq();

        orgnztMemberDto.setSeq(seq);

        ctrlMemberRole(orgnztMemberDto);

        return seq;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void ctrlMemberRole(OrganizationMemberDto orgnztMemberDto){

        long memberSeq = orgnztMemberDto.getSeq();

        memberRoleRepository.deleteBySeq(memberSeq);

        if(orgnztMemberDto.getRoleIds() != null
                && CommonUtil.isNotEmptyList(Arrays.asList(orgnztMemberDto.getRoleIds()))) {

            List<MembersRole> memberRoles = new ArrayList<>();

            for (long roleId : orgnztMemberDto.getRoleIds()) {
                memberRoles.add(new MembersRole(memberSeq, roleId));
            }

            memberRoleRepository.saveAll(memberRoles);
        }
    }

    @Transactional
    public void updateUser(OrganizationMemberDto orgnztMemberDto) throws NotFoundException {

        Members member = memberRepository.findById(orgnztMemberDto.getSeq()).orElse(null);
        long storeId = authenticationUtil.getTargetStoreId(orgnztMemberDto.getStoreId());

        if(member != null
            && member.getStoreId() == storeId) {
            //TODO ifnull return ?????? ??????
            member.setUpdDatetime(LocalDateTime.now());
            member.setName(orgnztMemberDto.getName());
            member.setEmail(orgnztMemberDto.getEmail());
            member.setPhone(
                    orgnztMemberDto.getPhone1()
                    .concat(orgnztMemberDto.getPhone2())
                    .concat(orgnztMemberDto.getPhone3())
            );
            member.setPhone1(orgnztMemberDto.getPhone1());
            member.setPhone2(orgnztMemberDto.getPhone2());
            member.setPhone3(orgnztMemberDto.getPhone3());

            if(String.valueOf(StatusEnum.USER_APPROVAL.getStatusCode())
                    .equals(orgnztMemberDto.getUserStatus())){
                orgnztMemberDto.setRole(UserRole.ROLE_USER);
            }else{
                orgnztMemberDto.setRole(UserRole.ROLE_NOT_PERMITTED);
            }

            member.setUserStatus(orgnztMemberDto.getUserStatus());

            if(StatusEnum.FLAG_Y.getStatusMsg().equals(orgnztMemberDto.getChgPassword())){
                authService.changePassword(member, orgnztMemberDto.getPassword());
                authService.requestChangePassword(member);
            }

            ctrlMemberRole(orgnztMemberDto);

        }else{
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    @Transactional
    public void updateUserUse(ModelMap reqModelMap) {

        List<Number> userIds = (ArrayList<Number>) reqModelMap.get("userId");

        if (CommonUtil.isNotEmptyList(userIds)) {

            List<Members> membersList = memberRepository.findAllById(
                    userIds.stream()
                            .map(Number::longValue).collect(Collectors.toList())
            );

            if(CommonUtil.isNotEmptyList(membersList)) {
                Optional.ofNullable(membersList).orElseGet(Collections::emptyList).forEach(member -> {

                    member.setRole(UserRole.ROLE_USER);
                    member.updateUserStatus(member, String.valueOf(StatusEnum.USER_APPROVAL.getStatusCode()));
                });
            }else{
                throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }

        }else{
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }

    @Transactional
    public void deleteUser(ModelMap reqModelMap) {

        List<Number> delUserIds = (ArrayList<Number>) reqModelMap.get("delUserId");

        if(CommonUtil.isNotEmptyList(delUserIds)){
            delUserIds.forEach(
                    seq -> {
                        memberRepository.deleteById(seq.longValue());
                        memberRoleRepository.deleteBySeq(seq.longValue());
                    }
            );
        }else{
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }
}


































