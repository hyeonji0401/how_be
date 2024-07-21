package HOW.how.service.impl;

import HOW.how.domain.Member;
import HOW.how.domain.MemberDetail;
import HOW.how.dto.MemberDetailFormDTO;
import HOW.how.exception.EntityNotFoundException;
import HOW.how.repository.MemberDetailRepository;
import HOW.how.service.GetAuthenticationService;
import HOW.how.service.MemberDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberDetailServiceImpl implements MemberDetailService {

    private final MemberDetailRepository memberDetailRepository;
    private final GetAuthenticationService getAuthenticationService;


    //사용자 정보 입력
    @Override
    public MemberDetail createMemberDetail(MemberDetailFormDTO memberDetailFormDTO){
        Member member = getAuthenticationService.getAuthentication(); // Member// 도메인 조회
        Optional<MemberDetail> memberDetailOpt = memberDetailRepository.findByMember(member);
        if(memberDetailOpt.isPresent()){
            System.out.println("존재");
            MemberDetail existMemberDetail = memberDetailOpt.get();
            existMemberDetail.setAge(memberDetailFormDTO.getAge());
            existMemberDetail.setBothHands(memberDetailFormDTO.getBothHands());
            existMemberDetail.setEyesight(memberDetailFormDTO.getEyesight());
            existMemberDetail.setHandwork(memberDetailFormDTO.getHandwork());
            existMemberDetail.setLiftPower(memberDetailFormDTO.getLiftPower());
            existMemberDetail.setLstnTalk(memberDetailFormDTO.getLstnTalk());
            existMemberDetail.setStndWalk(memberDetailFormDTO.getStndWalk());
            existMemberDetail.setJobNm(memberDetailFormDTO.getJobNm());
            existMemberDetail.setCareer(memberDetailFormDTO.getCareer());
            existMemberDetail.setEducation(memberDetailFormDTO.getEducation());
            existMemberDetail.setLocation(memberDetailFormDTO.getLocation());
            existMemberDetail.setLicenses(memberDetailFormDTO.getLicenses());
            return memberDetailRepository.save(existMemberDetail);

        }
        else{
            System.out.println("처음");
            MemberDetail memberDetail = new MemberDetail();
            memberDetail.setMember(member);
            memberDetail.setAge(memberDetailFormDTO.getAge());
            memberDetail.setBothHands(memberDetailFormDTO.getBothHands());
            memberDetail.setEyesight(memberDetailFormDTO.getEyesight());
            memberDetail.setHandwork(memberDetailFormDTO.getHandwork());
            memberDetail.setLiftPower(memberDetailFormDTO.getLiftPower());
            memberDetail.setLstnTalk(memberDetailFormDTO.getLstnTalk());
            memberDetail.setStndWalk(memberDetailFormDTO.getStndWalk());
            memberDetail.setJobNm(memberDetailFormDTO.getJobNm());
            memberDetail.setCareer(memberDetailFormDTO.getCareer());
            memberDetail.setEducation(memberDetailFormDTO.getEducation());
            memberDetail.setLocation(memberDetailFormDTO.getLocation());
            memberDetail.setLicenses(memberDetailFormDTO.getLicenses());
            return memberDetailRepository.save(memberDetail);
        }
    }

}
