package com.daema.service;

import com.daema.common.Constants;
import com.daema.common.enums.StatusEnum;
import com.daema.common.util.AuthenticationUtil;
import com.daema.common.util.CommonUtil;
import com.daema.common.util.SaltUtil;
import com.daema.domain.Member;
import com.daema.domain.MemberRole;
import com.daema.domain.Organization;
import com.daema.domain.RoleMgmt;
import com.daema.domain.dto.OrgnztListDto;
import com.daema.domain.dto.OrgnztMemberListDto;
import com.daema.dto.*;
import com.daema.repository.MemberRepository;
import com.daema.repository.MemberRoleRepository;
import com.daema.repository.OrganizationRepository;
import com.daema.response.enums.ServiceReturnMsgEnum;
import com.daema.response.exception.DataNotFoundException;
import com.daema.response.exception.ProcessErrorException;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.daema.dto.OrganizationMgmtResponseDto.OrgnztMemberAndRoleDto;

@Service
public class OrganizationMgmtService {

    private final OrganizationRepository organizationRepository;

    private final MemberRepository memberRepository;

    private final MemberRoleRepository memberRoleRepository;

    private final AuthService authService;

    private final RoleFuncMgmtService roleFuncMgmtService;

    private final AuthenticationUtil authenticationUtil;

    private final SaltUtil saltUtil;

    public OrganizationMgmtService(OrganizationRepository organizationRepository, MemberRepository memberRepository, MemberRoleRepository memberRoleRepository
            ,AuthService authService, RoleFuncMgmtService roleFuncMgmtService, AuthenticationUtil authenticationUtil, SaltUtil saltUtil) {
        this.organizationRepository = organizationRepository;
        this.memberRepository = memberRepository;
        this.memberRoleRepository = memberRoleRepository;
        this.authService = authService;
        this.roleFuncMgmtService = roleFuncMgmtService;
        this.authenticationUtil = authenticationUtil;
        this.saltUtil = saltUtil;
    }

    public OrganizationMgmtResponseDto getOrgnztList(OrganizationMgmtRequestDto requestDto) {

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
                                    ,((List<MemberRole>) orgnztMemberListMap.get("memberRoleList"))
                                    .stream()
                                    .filter(roles -> roles.getSeq() == ((OrgnztMemberListDto) member).getSeq())
                                    .map(MemberRole::getRoleId)
                                    .collect(Collectors.toList()))
                    );
                }
            );
        }

        responseDto.setMemberList(mList);

        List<RoleMgmt> roleList = roleFuncMgmtService.getRoleList(requestDto.getParentStoreId());

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

    @Transactional
    public long insertOrgnzt(OrganizationMgmtDto organizationMgmtDto) {
        //TODO ifnull return 함수 추가
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
    public void updateOrgnzt(OrganizationMgmtDto organizationMgmtDto, boolean deleteAct) {

        Organization orgnzt = organizationRepository.findById(organizationMgmtDto.getOrgId()).orElse(null);
        long storeId = authenticationUtil.getTargetStoreId(organizationMgmtDto.getStoreId());

        if(orgnzt != null
            && orgnzt.getStoreId() == storeId) {
            //TODO ifnull return 함수 추가
            if(StringUtils.hasText(organizationMgmtDto.getOrgName())) {
                orgnzt.setOrgName(organizationMgmtDto.getOrgName());
            }

            if(deleteAct
                    && StringUtils.hasText(organizationMgmtDto.getDelYn())) {
                orgnzt.setDelYn(organizationMgmtDto.getDelYn());
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

            List<Member> membersList = memberRepository.findByOrgId(orgnzt.getOrgId());

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

    @Transactional
    public long insertUser(OrganizationMemberDto orgnztMemberDto) {
        authService.signUpUser(Member.builder()
                .username(orgnztMemberDto.getUsername())
                .password(orgnztMemberDto.getPassword())
                .name(orgnztMemberDto.getName())
                .email(orgnztMemberDto.getEmail())
                .address(orgnztMemberDto.getAddress())
                .phone(orgnztMemberDto.getPhone())
                .regiDatetime(LocalDateTime.now())
                .updDatetime(null)
                .storeId(authenticationUtil.getTargetStoreId(orgnztMemberDto.getStoreId()))
                .orgId(orgnztMemberDto.getOrgId())
                .userStatus(orgnztMemberDto.getUserStatus())
            .build());

        long seq = memberRepository.findByUsername(orgnztMemberDto.getUsername()).getSeq();

        orgnztMemberDto.setSeq(seq);

        ctrlMemberRole(orgnztMemberDto);

        return seq;
    }

    @Transactional
    public void ctrlMemberRole(OrganizationMemberDto orgnztMemberDto){

        if(orgnztMemberDto.getRoleIds() != null
                && CommonUtil.isNotEmptyList(Arrays.asList(orgnztMemberDto.getRoleIds()))){
            long memberSeq = orgnztMemberDto.getSeq();

            memberRoleRepository.deleteBySeq(memberSeq);

            List<MemberRole> memberRoles = new ArrayList<>();

            for(int roleId : orgnztMemberDto.getRoleIds()){
                memberRoles.add(new MemberRole(memberSeq, roleId));
            }

            memberRoleRepository.saveAll(memberRoles);
        }
    }

    @Transactional
    public void updateUser(OrganizationMemberDto orgnztMemberDto) throws NotFoundException {

        Member member = memberRepository.findById(orgnztMemberDto.getSeq()).orElse(null);
        long storeId = authenticationUtil.getTargetStoreId(orgnztMemberDto.getStoreId());

        if(member != null
            && member.getStoreId() == storeId) {
            //TODO ifnull return 함수 추가
            member.setUpdDatetime(LocalDateTime.now());
            member.setName(orgnztMemberDto.getName());
            member.setEmail(orgnztMemberDto.getEmail());
            member.setPhone(orgnztMemberDto.getPhone());
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

            List<Member> membersList = memberRepository.findAllById(
                    userIds.stream()
                            .map(Number::longValue).collect(Collectors.toList())
            );

            if(CommonUtil.isNotEmptyList(membersList)) {
                Optional.ofNullable(membersList).orElseGet(Collections::emptyList).forEach(member -> {
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


































