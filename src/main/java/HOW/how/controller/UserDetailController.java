package HOW.how.controller;

import HOW.how.domain.UserDetail;
import HOW.how.dto.UserDetailFormDTO;
import HOW.how.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userDetail")
public class UserDetailController {
    private final UserDetailService userDetailService;

    @Autowired
    public UserDetailController(UserDetailService userDetailService){
        this.userDetailService = userDetailService;
    }

    //사용자 정보 입력
    @PostMapping
    public UserDetail createUserDetail(@RequestBody UserDetailFormDTO userDetailFormDTO)
    {
        return userDetailService.createUserDetail(userDetailFormDTO);
    }



}
