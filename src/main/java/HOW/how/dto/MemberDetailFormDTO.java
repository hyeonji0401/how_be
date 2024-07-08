package HOW.how.dto;

import HOW.how.domain.Degree;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberDetailFormDTO {
    @NotNull(message = "DB 생성 오류")
    String detailId;
    @NotNull(message = "사용자 정보가 정상적으로 처리되지 않음")
    String memberId;
    @NotNull(message = "사용자 정보가 정상적으로 처리되지 않음")
    private int age;
    @NotNull(message = "장애 분류는 빈칸일 수 없음")
    private String disability;
    @NotNull(message = "장애 정도는 빈칸일 수 없음")
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
