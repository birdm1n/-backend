package com.daema.service;

import com.daema.common.Constants;
import com.daema.common.enums.StatusEnum;
import com.daema.common.util.AuthenticationUtil;
import com.daema.common.util.CommonUtil;
import com.daema.common.util.SaltUtil;
import com.daema.domain.Member;
import com.daema.domain.Organization;
import com.daema.domain.dto.OrgnztListDto;
import com.daema.dto.OrganizationMemberDto;
import com.daema.dto.OrganizationMgmtDto;
import com.daema.dto.OrganizationMgmtRequestDto;
import com.daema.dto.OrganizationMgmtResponseDto;
import com.daema.repository.MemberRepository;
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

@Service
public class OrganizationMgmtService {

    private final OrganizationRepository organizationRepository;

    private final MemberRepository memberRepository;

    private final AuthService authService;

    private final AuthenticationUtil authenticationUtil;

    private final SaltUtil saltUtil;

    public OrganizationMgmtService(OrganizationRepository organizationRepository, MemberRepository memberRepository, AuthService authService, AuthenticationUtil authenticationUtil, SaltUtil saltUtil) {
        this.organizationRepository = organizationRepository;
        this.memberRepository = memberRepository;
        this.authService = authService;
        this.authenticationUtil = authenticationUtil;
        this.saltUtil = saltUtil;
    }

    public OrganizationMgmtResponseDto getOrgnztList(OrganizationMgmtRequestDto requestDto) {

        HashMap<String, List> orgnztMemberListMap = organizationRepository.getOrgnztAndMemberList();

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
        responseDto.setMemberList(orgnztMemberListMap.get("memberList"));

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
                        .storeId(organizationMgmtDto.getStoreId())
                        .delYn(StatusEnum.FLAG_N.getStatusMsg())
                        .build()
        ).getOrgId();
    }

    @Transactional
    public void updateOrgnzt(OrganizationMgmtDto organizationMgmtDto, boolean deleteAct) {

        Organization orgnzt = organizationRepository.findById(organizationMgmtDto.getOrgId()).orElse(null);

        if(orgnzt != null) {
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

        if(orgnzt != null) {
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
                .storeId(orgnztMemberDto.getStoreId())
                .orgId(orgnztMemberDto.getOrgId())
                .userStatus(orgnztMemberDto.getUserStatus())
            .build());

        return memberRepository.findByUsername(orgnztMemberDto.getUsername()).getSeq();
    }

    @Transactional
    public void updateUser(OrganizationMemberDto orgnztMemberDto) throws NotFoundException {

        Member member = memberRepository.findById(orgnztMemberDto.getSeq()).orElse(null);

        if(member != null) {
            //TODO ifnull return 함수 추가
            member.setUpdDatetime(LocalDateTime.now());
            member.setName(orgnztMemberDto.getName());
            member.setEmail(orgnztMemberDto.getEmail());
            member.setPhone(orgnztMemberDto.getPhone());
            member.setUserStatus(orgnztMemberDto.getUserStatus());

            if("Y".equals(orgnztMemberDto.getChgPassword())){
                authService.changePassword(member, orgnztMemberDto.getPassword());
                authService.requestChangePassword(member);
            }
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

        if(delUserIds != null
                && delUserIds.size() > 0){

            delUserIds.forEach(seq -> memberRepository.deleteById(seq.longValue()));
        }else{
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }
}


































