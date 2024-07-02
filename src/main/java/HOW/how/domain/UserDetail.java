package HOW.how.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Data
@Document(collection = "userDetail")
public class UserDetail {
    @Id
    String detailId;
    @DBRef //참조
    User userId;
    private int age;
    private String disability;
    private Degree disabilityDegree;
    private Degree education;
    private List<String> licenses;
    private String experience;
    private String region;
    private String job;
    private String digitalLiteracy;
    private List<String> languageSkills;
    private String interests;
    private String strengths;
    private String workSupport;

}

