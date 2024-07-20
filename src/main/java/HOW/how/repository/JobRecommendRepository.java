package HOW.how.repository;

import HOW.how.domain.JobRecommend;
import HOW.how.domain.Member;
import HOW.how.domain.MemberDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface JobRecommendRepository extends MongoRepository<JobRecommend, String> {
    Optional<JobRecommend> findByMember(Member member);
}
