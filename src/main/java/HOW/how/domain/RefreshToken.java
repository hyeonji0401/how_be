package HOW.how.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.cert.CertPathBuilder;

@Getter
@NoArgsConstructor
@Document(collection = "refreshToken")
public class RefreshToken {

    @Id
    private String key;

    private String value;

    @Builder
    public RefreshToken(String key, String value){
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String token){
        this.value = token;
        return this;
    }

}
