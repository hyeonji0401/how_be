package HOW.how.service;

import HOW.how.domain.MemberDetail;
import HOW.how.dto.MemberDetailFormDTO;

public interface MemberDetailService {
    MemberDetail createMemberDetail(MemberDetailFormDTO memberDetailFormDTO);
}
