package HOW.how.repository;

import HOW.how.domain.Member;
import HOW.how.domain.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findByKey(String key);
}
