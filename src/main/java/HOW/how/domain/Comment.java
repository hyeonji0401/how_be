package HOW.how.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "comment")
public class Comment {
    @Id
    private String id;
    private String boardId;
    private String content;
    private LocalDateTime writeDate;
    private LocalDateTime updateDate;
    @DBRef
    private Member member;
}
