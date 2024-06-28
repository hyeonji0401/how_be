package HOW.how.controller;

import HOW.how.domain.User;
import HOW.how.dto.UserFormDTO;
import HOW.how.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody UserFormDTO userFormDTO){
        System.out.print(userFormDTO.getEmail());
        System.out.println(userFormDTO.getPassword());
        return userService.createUser(userFormDTO);
    }
}
