package HOW.how.controller;

import HOW.how.domain.MemberDetail;
import HOW.how.dto.MemberDetailFormDTO;
import HOW.how.service.MemberDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/memberDetail")
public class MemberDetailController {
    private final MemberDetailService memberDetailService;

    @Autowired
    public MemberDetailController(MemberDetailService memberDetailService){
        this.memberDetailService = memberDetailService;
    }
    //공고 추천을 위한 사용자 정보 입력
    @PostMapping
    public MemberDetail createMemberDetail(@RequestBody MemberDetailFormDTO memberDetailFormDTO)
    {
        return memberDetailService.createMemberDetail(memberDetailFormDTO);
    }

}
