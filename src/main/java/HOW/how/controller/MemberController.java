package HOW.how.controller;

import HOW.how.domain.Member;
import HOW.how.dto.LoginRequestDTO;
import HOW.how.dto.MemberFormDTO;
import HOW.how.dto.TokenDTO;
import HOW.how.dto.TokenRequestDTO;
import HOW.how.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    
    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberFormDTO memberFormDTO){
        try{
            this.memberService.signup(memberFormDTO);
            return ResponseEntity.ok("회원가입이 성공적으로 이루어졌습니다");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO){
        try {
            return ResponseEntity.ok(memberService.login(loginRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    //토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenRequestDTO tokenRequestDTO){
        try {
            return ResponseEntity.ok(memberService.reissue(tokenRequestDTO));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    //회원 정보 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateMember(@RequestBody MemberFormDTO memberFormDTO){
        Member member = memberService.updateMember(memberFormDTO);
        if(member ==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(member);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getMemberInfo(){
        try{
            Member member = memberService.getMemberInfo();
            if(member ==null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            return ResponseEntity.ok(member);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }


    }



}
