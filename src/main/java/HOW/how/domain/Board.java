package HOW.how.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "board")
public class Board {
    @Id
    private String id;
    private String title;
    private String content;
    private LocalDateTime writeDate;
    private LocalDateTime updateDate;
    @DBRef
    private Member member;
    @DBRef
    private List<Comment> commentList = new ArrayList<>();
    @DBRef
    private List<Liked> likeds = new ArrayList<>();

}
