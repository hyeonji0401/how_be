package HOW.how.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "jobRecommend")
public class JobRecommend {
    @Id
    private String id;
    @DBRef
    private Member member;
    private List<Map<String, Object>> companyInfos;
}
