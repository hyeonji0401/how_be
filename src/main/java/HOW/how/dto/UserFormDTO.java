package HOW.how.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UserFormDTO {
    private String id;
    private String email;
    private String password;
}
