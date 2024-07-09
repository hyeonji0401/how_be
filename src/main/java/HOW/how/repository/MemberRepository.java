package HOW.how.repository;

import HOW.how.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MemberRepository extends MongoRepository<Member, String> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findById(String id);
    Boolean existsByEmail(String email);
}
