package HOW.how.controller;

import HOW.how.domain.Member;
import HOW.how.dto.LoginRequestDTO;
import HOW.how.dto.MemberFormDTO;
import HOW.how.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/join")
    public void createMember(@RequestBody MemberFormDTO memberFormDTO){
        System.out.print(memberFormDTO.getEmail());
        System.out.println(memberFormDTO.getPassword());
        memberService.createMember(memberFormDTO);
    }

    //로그인
    @PostMapping("/login")
    public Member login(@RequestBody LoginRequestDTO loginRequestDTO){
        System.out.println("로그인 요청");
        Member member = memberService.loginRequest(loginRequestDTO);
        return member;
    }

    //회원 정보 수정
    @PutMapping("/update")
    public ResponseEntity<Member> updateMember(@RequestBody MemberFormDTO memberFormDTO){
        Member member = memberService.updateMember(memberFormDTO);

        if(member ==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(member);
    }


}
