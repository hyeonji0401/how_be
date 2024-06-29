package HOW.how.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UserDetailFormDTO {
    @NotNull(message = "DB 생성 오류")
    String detailId;
    @NotNull(message = "사용자 정보가 정상적으로 처리되지 않음")
    String userId;
    String location;
    @NotNull(message = "장애 분류는 빈칸일 수 없습니다")
    String disabilityClassification;
    String certificate;
    String academicBackground;
    String occupationalCategory;
    @NotNull(message = "나이는 빈칸일 수 없습니다")
    int age;

}
