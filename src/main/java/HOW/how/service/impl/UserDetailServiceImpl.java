package HOW.how.service.impl;

import HOW.how.domain.UserDetail;
import HOW.how.dto.UserDetailFormDTO;
import HOW.how.repository.UserDetailRepository;
import HOW.how.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    private final UserDetailRepository userDetailRepository;
    @Autowired
    public UserDetailServiceImpl(UserDetailRepository userDetailRepository){
        this.userDetailRepository = userDetailRepository;
    }

    @Override
    public UserDetail createUserDetail(UserDetailFormDTO userDetailFormDTO){
        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(userDetailFormDTO.getUserId());
        userDetail.setLocation(userDetailFormDTO.getLocation());
        userDetail.setDisabilityClassification(userDetailFormDTO.getDisabilityClassification());
        userDetail.setCertificate(userDetailFormDTO.getCertificate());
        userDetail.setAcademicBackground(userDetailFormDTO.getAcademicBackground());
        userDetail.setOccupationalCategory(userDetailFormDTO.getOccupationalCategory());
        userDetail.setAge(userDetailFormDTO.getAge());

        return userDetailRepository.save(userDetail);
    }
}
