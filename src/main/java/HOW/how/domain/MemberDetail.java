package HOW.how.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "memberDetail")
public class MemberDetail {
    @Id
    String detailId;
    @DBRef //참조
    Member memberId;
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

