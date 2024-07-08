package HOW.how.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class LoginRequestDTO {
    @NotNull(message = "이메일은 빈칸일 수 없습니다")
    private String email;
    @NotNull(message = "비밀번호는 빈칸일 수 없습니다")
    private String password;
}
