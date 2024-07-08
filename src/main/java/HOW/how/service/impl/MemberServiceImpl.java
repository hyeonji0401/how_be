package HOW.how.service.impl;

import HOW.how.domain.Member;
import HOW.how.dto.LoginRequestDTO;
import HOW.how.dto.MemberFormDTO;
import HOW.how.repository.MemberRepository;
import HOW.how.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        member.setEmail(memberFormDTO.getEmail());
        member.setPassword(memberFormDTO.getPassword());
        member.setName(memberFormDTO.getName());
        member.setPhoneNumber(memberFormDTO.getPhoneNumber());
        member.setRoles(roles);
        return memberRepository.save(member);
    }


    //로그인
    @Override
    public Member loginRequest(LoginRequestDTO loginRequestDTO){
        // 이메일을 기반으로 사용자 찾기
        Optional<Member> optionalMember = memberRepository.findByEmail(loginRequestDTO.getEmail());
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + loginRequestDTO.getEmail());
        }

        Member member = optionalMember.get();
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
