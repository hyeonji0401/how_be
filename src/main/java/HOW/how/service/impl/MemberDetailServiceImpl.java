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
        memberDetail.setDisability(memberDetailFormDTO.getDisability());
        Degree disabilityDegree = new Degree();
        disabilityDegree.setValue(memberDetailFormDTO.getDisabilityDegree().getValue());
        disabilityDegree.setLabel(memberDetailFormDTO.getDisabilityDegree().getLabel());
        memberDetail.setDisabilityDegree(disabilityDegree);
        Degree education = new Degree();
        education.setValue(memberDetailFormDTO.getEducation().getValue());
        education.setLabel(memberDetailFormDTO.getEducation().getLabel());
        memberDetail.setEducation(education);
        memberDetail.setLicenses(memberDetailFormDTO.getLicenses());
        memberDetail.setExperience(memberDetailFormDTO.getExperience());
        memberDetail.setRegion(memberDetailFormDTO.getRegion());
        memberDetail.setJob(memberDetailFormDTO.getJob());
        memberDetail.setDigitalLiteracy(memberDetailFormDTO.getDigitalLiteracy());
        memberDetail.setLanguageSkills(memberDetailFormDTO.getLanguageSkills());
        memberDetail.setInterests(memberDetailFormDTO.getInterests());
        memberDetail.setStrengths(memberDetailFormDTO.getStrengths());
        memberDetail.setWorkSupport(memberDetailFormDTO.getWorkSupport());
        return memberDetailRepository.save(memberDetail);
    }
}
