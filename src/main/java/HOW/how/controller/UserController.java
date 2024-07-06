package HOW.how.controller;

import HOW.how.domain.User;
import HOW.how.dto.LoginRequestDTO;
import HOW.how.dto.UserFormDTO;
import HOW.how.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/join")
    public User createUser(@RequestBody UserFormDTO userFormDTO){
        System.out.print(userFormDTO.getEmail());
        System.out.println(userFormDTO.getPassword());
        return userService.createUser(userFormDTO);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequestDTO loginRequestDTO){
        User user = userService.loginRequest(loginRequestDTO);

        //로그인 실패 시 오류 코드 전송
        if(user==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(user);
    }

    //회원정보 수정
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UserFormDTO userFormDTO){
        User user= userService.updateUser(userFormDTO);

        if(user==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(user);
    }


}
