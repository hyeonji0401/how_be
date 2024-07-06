package HOW.how.service.impl;

import HOW.how.domain.User;
import HOW.how.dto.LoginRequestDTO;
import HOW.how.dto.UserFormDTO;
import HOW.how.repository.UserRepository;
import HOW.how.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //회원가입
    @Override
    public User createUser(UserFormDTO userFormDTO){
        User user = new User();
        user.setEmail(userFormDTO.getEmail());
        user.setPassword(userFormDTO.getPassword());
        user.setName(userFormDTO.getName());
        user.setPhoneNumber(userFormDTO.getPhoneNumber());
        return userRepository.save(user);
    }


    //로그인
    @Override
    public User loginRequest(LoginRequestDTO loginRequestDTO){
        //입력받은 이메일로 조회
        Optional<User> optionalUser = userRepository.findByEmail(loginRequestDTO.getEmail());

        //해당 이메일 없을 시 null 반환
        if(optionalUser.isEmpty()){
            return null;
        }

        //이메일 존재할 시 비밀번호 비교
        User user = optionalUser.get();
        if(!user.getPassword().equals(loginRequestDTO.getPassword())){
            return null;
        }

        return user;

    }

    //@Override
    public User updateUser(UserFormDTO userFormDTO){
        Optional<User> optionalUser = userRepository.findByEmail(userFormDTO.getEmail());

        // 해당 이메일 없을 시 null 반환
        if(optionalUser.isEmpty()){
            return null;
        }

        // 이메일 존재할 시 정보 업데이트
        User user = optionalUser.get();
        user.setPassword(userFormDTO.getPassword());
        user.setName(userFormDTO.getName());
        user.setPhoneNumber(userFormDTO.getPhoneNumber());

        return userRepository.save(user);
    }

}
