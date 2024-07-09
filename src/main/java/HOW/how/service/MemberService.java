package HOW.how.service;

import HOW.how.domain.Member;
import HOW.how.dto.LoginRequestDTO;
import HOW.how.dto.MemberFormDTO;

public interface MemberService {
    void createMember(MemberFormDTO memberFormDTO);
    Member loginRequest(LoginRequestDTO loginRequestDTO);

    Member updateMember(MemberFormDTO memberFormDTO);

}
