package HOW.how.repository;

import HOW.how.domain.Board;
import HOW.how.domain.Liked;
import HOW.how.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface LikedRepository extends MongoRepository<Liked, String> {
    Optional<Liked> findByBoardAndMember(Board board, Member member);
    List<Liked> findByMember(Member member);
}
