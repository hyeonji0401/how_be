package HOW.how.service.impl;

import HOW.how.domain.Member;
import HOW.how.domain.RefreshToken;
import HOW.how.dto.LoginRequestDTO;
import HOW.how.dto.MemberFormDTO;
import HOW.how.dto.TokenDTO;
import HOW.how.dto.TokenRequestDTO;
import HOW.how.jwt.JwtTokenProvider;
import HOW.how.repository.MemberRepository;
import HOW.how.repository.RefreshTokenRepository;
import HOW.how.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final RefreshTokenRepository refreshTokenRepository;

    //회원가입
    @Transactional
    @Override
    public Member signup(MemberFormDTO memberFormDTO){
        if(memberRepository.existsByEmail(memberFormDTO.getEmail())){
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        Member member = new Member();
        member.setEmail(memberFormDTO.getEmail());
        member.setPassword(passwordEncoder.encode(memberFormDTO.getPassword()));
        member.setName(memberFormDTO.getName());
        member.setPhoneNumber(member.getPhoneNumber());
        member.setRole("ROLE_USER");
        return memberRepository.save(member);
    }

    @Transactional
    @Override
    public TokenDTO login(LoginRequestDTO loginRequestDTO){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        TokenDTO tokenDTO = jwtTokenProvider.generateTokenDTO(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDTO.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        return tokenDTO;

    }

    @Transactional
    public TokenDTO reissue(TokenRequestDTO tokenRequestDTO){
        if(!jwtTokenProvider.validateToken(tokenRequestDTO.getRefreshToken())){
            throw new RuntimeException("Refresh Token이 유효하지 않습니다");
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDTO.getAccessToken());

        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName()).orElseThrow(()->new RuntimeException("로그아웃 된 사용자입니다"));

        if(!refreshToken.getValue().equals(tokenRequestDTO.getRefreshToken())){
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다");
        }

        TokenDTO tokenDTO = jwtTokenProvider.generateTokenDTO(authentication);

        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDTO.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDTO;
    }


    //@Override
    public Member updateMember(MemberFormDTO memberFormDTO){
        Member findMember = memberRepository.findByEmail(memberFormDTO.getEmail());

        // 해당 이메일 없을 시 null 반환
        if(findMember==null){
            return null;
        }

        // 이메일 존재할 시 정보 업데이트
        Member member = findMember;
        member.setPassword(passwordEncoder.encode(memberFormDTO.getPassword()));
        member.setName(memberFormDTO.getName());
        member.setPhoneNumber(memberFormDTO.getPhoneNumber());

        return memberRepository.save(member);
    }

}
