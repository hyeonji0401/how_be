package HOW.how.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@Document(collection = "userDetail")
public class UserDetail {
    @Id
    String detailId;
    String userId;
    String location;
    String disabilityClassification;
    String certificate;
    String academicBackground;
    String occupationalCategory;
    int age;

}
