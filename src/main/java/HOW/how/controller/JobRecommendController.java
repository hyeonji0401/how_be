package HOW.how.controller;

import HOW.how.domain.JobRecommend;
import HOW.how.dto.JobRecommendDTO;
import HOW.how.service.JobRecommendService;
import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommendJobs")
public class JobRecommendController {

    private final JobRecommendService jobRecommendService;

    @PostMapping("/save")
    public ResponseEntity<JobRecommend> saveRecommendJobs() {
         return ResponseEntity.ok(this.jobRecommendService.recommendJobs());
    }

    @GetMapping("/get")
    public ResponseEntity<JobRecommendDTO> getRecommendJobs(@RequestBody JobRecommendDTO jobRecommendDTO) {
        return ResponseEntity.ok(this.jobRecommendService.getJobRecommends());
    }
}
