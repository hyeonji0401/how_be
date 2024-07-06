package HOW.how.controller;

import HOW.how.domain.Member;
import HOW.how.dto.LoginRequestDTO;
import HOW.how.dto.MemberFormDTO;
import HOW.how.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Member createMember(@RequestBody MemberFormDTO memberFormDTO){
        System.out.print(memberFormDTO.getEmail());
        System.out.println(memberFormDTO.getPassword());
        return memberService.createMember(memberFormDTO);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody LoginRequestDTO loginRequestDTO){
        Member member = memberService.loginRequest(loginRequestDTO);

        //로그인 실패 시 오류 코드 전송
        if(member ==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(member);
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
