package HOW.how.repository;


import HOW.how.domain.Comment;
import HOW.how.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByMember(Member member);
}
