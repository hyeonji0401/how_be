package HOW.how.service;

import HOW.how.domain.JobRecommend;
import HOW.how.dto.JobRecommendDTO;



public interface JobRecommendService {
    JobRecommend createJobRecommend();
    JobRecommend updateJobRecommend();
    JobRecommend recommendJobs(boolean isUpdate);
    JobRecommendDTO getJobRecommends();
}
