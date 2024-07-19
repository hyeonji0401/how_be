package HOW.how.service;

import HOW.how.domain.JobRecommend;
import HOW.how.dto.JobRecommendDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface JobRecommendService {
    JobRecommend recommendJobs();
    JobRecommendDTO getJobRecommends();
}
