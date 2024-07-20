package HOW.how.repository;

import HOW.how.domain.Member;
import HOW.how.domain.MemberDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MemberDetailRepository extends MongoRepository<MemberDetail, String> {
    Optional<MemberDetail> findByMember(Member member);
}
