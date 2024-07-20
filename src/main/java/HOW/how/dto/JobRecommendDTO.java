package HOW.how.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class JobRecommendDTO {
    private List<Map<String, Object>> companyInfo;
}
