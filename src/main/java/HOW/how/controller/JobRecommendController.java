package HOW.how.controller;

import HOW.how.domain.JobRecommend;
import HOW.how.dto.JobRecommendDTO;
import HOW.how.service.JobRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommendJobs")
public class JobRecommendController {

    private final JobRecommendService jobRecommendService;

    @PostMapping("/save")
    public ResponseEntity<JobRecommend> saveRecommendJobs() {
         return ResponseEntity.ok(this.jobRecommendService.createJobRecommend());
    }

    @PutMapping("/update")
    public ResponseEntity<JobRecommend> updateRecommendJobs() {
        return ResponseEntity.ok(this.jobRecommendService.updateJobRecommend());
    }

    @GetMapping("/get")
    public ResponseEntity<JobRecommendDTO> getRecommendJobs() {
        return ResponseEntity.ok(this.jobRecommendService.getJobRecommends());
    }

}
