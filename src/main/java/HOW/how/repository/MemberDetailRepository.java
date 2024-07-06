package HOW.how.repository;

import HOW.how.domain.MemberDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberDetailRepository extends MongoRepository<MemberDetail, String> {

}
