package HOW.how.service.impl;

import HOW.how.domain.Member;
import HOW.how.dto.LoginRequestDTO;
import HOW.how.dto.MemberFormDTO;
import HOW.how.repository.MemberRepository;
import HOW.how.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    //회원가입
    @Override
    public Member createMember(MemberFormDTO memberFormDTO){
        Member member = new Member();
        member.setEmail(memberFormDTO.getEmail());
        member.setPassword(memberFormDTO.getPassword());
        member.setName(memberFormDTO.getName());
        member.setPhoneNumber(memberFormDTO.getPhoneNumber());
        return memberRepository.save(member);
    }


    //로그인
    @Override
    public Member loginRequest(LoginRequestDTO loginRequestDTO){
        //입력받은 이메일로 조회
        Optional<Member> optionalMember = memberRepository.findByEmail(loginRequestDTO.getEmail());

        //해당 이메일 없을 시 null 반환
        if(optionalMember.isEmpty()){
            return null;
        }

        //이메일 존재할 시 비밀번호 비교
        Member member = optionalMember.get();
        if(!member.getPassword().equals(loginRequestDTO.getPassword())){
            return null;
        }

        return member;

    }

    //@Override
    public Member updateMember(MemberFormDTO memberFormDTO){
        Optional<Member> optionalMember = memberRepository.findByEmail(memberFormDTO.getEmail());

        // 해당 이메일 없을 시 null 반환
        if(optionalMember.isEmpty()){
            return null;
        }

        // 이메일 존재할 시 정보 업데이트
        Member member = optionalMember.get();
        member.setPassword(memberFormDTO.getPassword());
        member.setName(memberFormDTO.getName());
        member.setPhoneNumber(memberFormDTO.getPhoneNumber());

        return memberRepository.save(member);
    }

}
