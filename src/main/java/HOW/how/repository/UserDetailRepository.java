package HOW.how.repository;

import HOW.how.domain.UserDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDetailRepository extends MongoRepository<UserDetail, String> {

}
