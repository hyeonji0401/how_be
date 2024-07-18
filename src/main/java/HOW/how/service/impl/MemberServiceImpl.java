package HOW.how.service.impl;

import HOW.how.domain.*;
import HOW.how.dto.LoginRequestDTO;
import HOW.how.dto.MemberFormDTO;
import HOW.how.dto.TokenDTO;
import HOW.how.dto.TokenRequestDTO;
import HOW.how.jwt.JwtTokenProvider;
import HOW.how.repository.*;
import HOW.how.service.GetAuthenticationService;
import HOW.how.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final RefreshTokenRepository refreshTokenRepository;
    private final GetAuthenticationService getAuthenticationService;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final LikedRepository likedRepository;

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
        member.setPhoneNumber(memberFormDTO.getPhoneNumber());
        member.setRole("ROLE_USER");
        return memberRepository.save(member);
    }

    //로그인
    @Transactional
    @Override
    public TokenDTO login(LoginRequestDTO loginRequestDTO) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            TokenDTO tokenDTO = jwtTokenProvider.generateTokenDTO(authentication);

            RefreshToken refreshToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(tokenDTO.getRefreshToken())
                    .build();

            refreshTokenRepository.save(refreshToken);
            return tokenDTO;
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while processing your request");
        }
    }

    //토큰 재발급
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

    //멤버 정보 수정
    @Override
    public Member updateMember(MemberFormDTO memberFormDTO) {
        try {
            Optional<Member> findMember = memberRepository.findByEmail(memberFormDTO.getEmail());

            // 해당 이메일 없을 시 예외 발생
            if(findMember.isEmpty()){
                throw new NoSuchElementException("No member found with the provided email");
            }

            // 이메일 존재할 시 정보 업데이트
            Member member = findMember.get();
            member.setPassword(passwordEncoder.encode(memberFormDTO.getPassword()));
            member.setName(memberFormDTO.getName());
            member.setPhoneNumber(memberFormDTO.getPhoneNumber());

            return memberRepository.save(member);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while updating the member");
        }
    }

    //멤버 정보 받아오기
    @Override
    public Member getMemberInfo(){
        try {
            return getAuthenticationService.getAuthentication();
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while getting the member info");
        }
    }

    //회원탈퇴
    @Override
    public void withdraw(){
        Member member = getAuthenticationService.getAuthentication();
        List<Board> boards = boardRepository.findByMember(member);
        for(Board board : boards){
            board.setMember(null);
            boardRepository.save(board);
        }
        List<Comment> comments = commentRepository.findByMember(member);
        for(Comment comment : comments){
            comment.setMember(null);
            commentRepository.save(comment);
        }
        List<Liked> likeds = likedRepository.findByMember(member);
        likedRepository.deleteAll(likeds);
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(member.getEmail());
        refreshTokenRepository.delete(refreshToken.get());
        memberRepository.delete(member);
    }
}
