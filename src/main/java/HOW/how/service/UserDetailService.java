package HOW.how.service;

import HOW.how.domain.UserDetail;
import HOW.how.dto.UserDetailFormDTO;

public interface UserDetailService {
    UserDetail createUserDetail(UserDetailFormDTO userDetailFormDTO);
}
