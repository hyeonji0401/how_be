package HOW.how.repository;

import HOW.how.domain.Board;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BoardRepository extends MongoRepository<Board, String> {
    List<Board> findByTitleContainingOrContentContaining(String keyword1, String keyword2);

}
