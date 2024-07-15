package HOW.how.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "liked")
public class Liked {
    @Id
    private String id;
    @DBRef
    private Board board;
    @DBRef
    private Member member;
}
