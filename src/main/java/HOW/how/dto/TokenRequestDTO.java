package HOW.how.dto;

import lombok.Data;

@Data
public class TokenRequestDTO {
    private String AccessToken;
    private String RefreshToken;
}
