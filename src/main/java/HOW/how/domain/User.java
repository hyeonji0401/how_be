package HOW.how.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String email;
    private String password;

}
