package HOW.how.service;

import HOW.how.domain.User;
import HOW.how.domain.UserDetail;
import HOW.how.dto.LoginRequestDTO;
import HOW.how.dto.UserDetailFormDTO;
import HOW.how.dto.UserFormDTO;

public interface UserService {
    User createUser(UserFormDTO userFormDTO);
    User loginRequest(LoginRequestDTO loginRequestDTO);

    User updateUser(UserFormDTO userFormDTO);

}
