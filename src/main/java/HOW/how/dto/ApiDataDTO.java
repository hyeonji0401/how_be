package HOW.how.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Data
@AllArgsConstructor
public class ApiDataDTO {
    @NotNull(message = "DB 생성 오류")
    private String id;
    @NotNull(message = "데이터가 없음")
    private Map<String, Object> data;
}
