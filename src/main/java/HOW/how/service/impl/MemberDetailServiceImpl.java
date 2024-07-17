package HOW.how.service.impl;

import HOW.how.domain.Degree;
import HOW.how.domain.Member;
import HOW.how.domain.MemberDetail;
import HOW.how.dto.MemberDetailFormDTO;
import HOW.how.repository.MemberDetailRepository;
import HOW.how.repository.MemberRepository;
import HOW.how.service.MemberDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailServiceImpl implements MemberDetailService {

    private final MemberDetailRepository memberDetailRepository;
    private final MemberRepository memberRepository;
    @Autowired
    public MemberDetailServiceImpl(MemberDetailRepository memberDetailRepository, MemberRepository memberRepository){
        this.memberDetailRepository = memberDetailRepository;
        this.memberRepository = memberRepository;
    }

    //로드맵을 위한 사용자 정보 입력
    @Override
    public MemberDetail createMemberDetail(MemberDetailFormDTO memberDetailFormDTO){
        MemberDetail memberDetail = new MemberDetail();
        Member member = memberRepository.findById(memberDetailFormDTO.getMemberId()).orElse(null); // Member// 도메인 조회
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
}
