package HOW.how.repository;

import HOW.how.domain.ApiData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiDataRepository extends MongoRepository<ApiData, String> {
}
