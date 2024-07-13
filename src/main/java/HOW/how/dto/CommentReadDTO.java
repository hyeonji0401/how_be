package HOW.how.dto;

import HOW.how.domain.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentReadDTO {
    private String writer;
    private String content;
    private LocalDateTime writeDate;
    private LocalDateTime updateDate;


    public CommentReadDTO(Comment comment){
        this.writer = comment.getMember().getName();
        this.content = comment.getContent();
        this.writeDate = comment.getWriteDate();
        this.updateDate = comment.getUpdateDate();
    }

}
