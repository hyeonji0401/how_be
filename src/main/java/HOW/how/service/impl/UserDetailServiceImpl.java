package HOW.how.service.impl;

import HOW.how.domain.Degree;
import HOW.how.domain.User;
import HOW.how.domain.UserDetail;
import HOW.how.dto.UserDetailFormDTO;
import HOW.how.repository.UserDetailRepository;
import HOW.how.repository.UserRepository;
import HOW.how.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    private final UserDetailRepository userDetailRepository;
    private final UserRepository userRepository;
    @Autowired
    public UserDetailServiceImpl(UserDetailRepository userDetailRepository, UserRepository userRepository){
        this.userDetailRepository = userDetailRepository;
        this.userRepository = userRepository;
    }

    //로드맵을 위한 사용자 정보 입력
    @Override
    public UserDetail createUserDetail(UserDetailFormDTO userDetailFormDTO){
        UserDetail userDetail = new UserDetail();
        User user = userRepository.findById(userDetailFormDTO.getUserId()).orElse(null); // User 도메인 조회
        userDetail.setUserId(user);
        userDetail.setAge(userDetailFormDTO.getAge());
        userDetail.setDisability(userDetailFormDTO.getDisability());
        Degree disabilityDegree = new Degree();
        disabilityDegree.setValue(userDetailFormDTO.getDisabilityDegree().getValue());
        disabilityDegree.setLabel(userDetailFormDTO.getDisabilityDegree().getLabel());
        userDetail.setDisabilityDegree(disabilityDegree);
        Degree education = new Degree();
        education.setValue(userDetailFormDTO.getEducation().getValue());
        education.setLabel(userDetailFormDTO.getEducation().getLabel());
        userDetail.setEducation(education);
        userDetail.setLicenses(userDetailFormDTO.getLicenses());
        userDetail.setExperience(userDetailFormDTO.getExperience());
        userDetail.setRegion(userDetailFormDTO.getRegion());
        userDetail.setJob(userDetailFormDTO.getJob());
        userDetail.setDigitalLiteracy(userDetailFormDTO.getDigitalLiteracy());
        userDetail.setLanguageSkills(userDetailFormDTO.getLanguageSkills());
        userDetail.setInterests(userDetailFormDTO.getInterests());
        userDetail.setStrengths(userDetailFormDTO.getStrengths());
        userDetail.setWorkSupport(userDetailFormDTO.getWorkSupport());
        return userDetailRepository.save(userDetail);
    }
}
