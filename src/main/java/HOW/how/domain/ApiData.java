package HOW.how.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@Data
@Document(collection = "apiData")
public class ApiData {
    @Id
    private String id;
    private Map<String, Object> data;
}
