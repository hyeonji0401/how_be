package HOW.how.service;

import HOW.how.domain.Member;
import HOW.how.dto.LoginRequestDTO;
import HOW.how.dto.MemberFormDTO;
import HOW.how.dto.TokenDTO;
import HOW.how.dto.TokenRequestDTO;
import org.apache.el.parser.Token;

public interface MemberService {
    Member signup(MemberFormDTO memberFormDTO);

    TokenDTO login(LoginRequestDTO loginRequestDTO);

    TokenDTO reissue(TokenRequestDTO tokenRequestDTO);
    Member updateMember(MemberFormDTO memberFormDTO);

    Member getMemberInfo();

    void withdraw();

}
