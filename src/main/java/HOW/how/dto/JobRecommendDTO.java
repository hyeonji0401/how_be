package HOW.how.dto;

import HOW.how.domain.Member;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class JobRecommendDTO {
    @NotNull(message = "db오류")
    private String id;
    private List<Map<String, Object>> companyInfo;
}
