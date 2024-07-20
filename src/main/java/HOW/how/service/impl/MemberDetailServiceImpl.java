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


    //로드맵을 위한 사용자 정보 입력
    @Override
    public MemberDetail createMemberDetail(MemberDetailFormDTO memberDetailFormDTO){
        MemberDetail memberDetail = new MemberDetail();
        Member member = getAuthenticationService.getAuthentication(); // Member// 도메인 조회
        memberDetail.setMemberId(member);
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

    @Override
    public MemberDetail updateMemberDetail(MemberDetailFormDTO memberDetailFormDTO) {
        Member member = getAuthenticationService.getAuthentication();
        Optional<MemberDetail> memberDetail = memberDetailRepository.findByMemberId(member);

        if(memberDetail.isPresent()){
            MemberDetail memberDetailDB = memberDetail.get();

            memberDetailDB.setAge(memberDetailFormDTO.getAge());
            memberDetailDB.setBothHands(memberDetailFormDTO.getBothHands());
            memberDetailDB.setEyesight(memberDetailFormDTO.getEyesight());
            memberDetailDB.setHandwork(memberDetailFormDTO.getHandwork());
            memberDetailDB.setLiftPower(memberDetailFormDTO.getLiftPower());
            memberDetailDB.setLstnTalk(memberDetailFormDTO.getLstnTalk());
            memberDetailDB.setStndWalk(memberDetailFormDTO.getStndWalk());
            memberDetailDB.setJobNm(memberDetailFormDTO.getJobNm());
            memberDetailDB.setCareer(memberDetailFormDTO.getCareer());
            memberDetailDB.setEducation(memberDetailFormDTO.getEducation());
            memberDetailDB.setLocation(memberDetailFormDTO.getLocation());
            memberDetailDB.setLicenses(memberDetailFormDTO.getLicenses());

            return memberDetailRepository.save(memberDetailDB);
        }else {
            throw new EntityNotFoundException("MemberDetail Update failed, MemberDetail not found for MemberId: " + member.getId());
        }
    }
}
