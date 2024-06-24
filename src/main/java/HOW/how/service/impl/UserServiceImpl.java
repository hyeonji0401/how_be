package HOW.how.service.impl;

import HOW.how.domain.User;
import HOW.how.dto.UserFormDTO;
import HOW.how.repository.UserRepository;
import HOW.how.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserFormDTO userFormDTO){
        User user = new User();
        user.setEmail(userFormDTO.getEmail());
        user.setPassword(userFormDTO.getPassword());
        return userRepository.save(user);
    }
}
