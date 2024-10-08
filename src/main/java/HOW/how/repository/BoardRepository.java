package HOW.how.repository;

import HOW.how.domain.Board;
import HOW.how.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends MongoRepository<Board, String> {
    List<Board> findByMember(Member member);
}
