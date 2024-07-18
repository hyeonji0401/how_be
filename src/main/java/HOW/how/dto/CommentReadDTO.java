package HOW.how.dto;

import HOW.how.domain.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentReadDTO {
    private String id;
    private String writer;
    private String content;
    private LocalDateTime writeDate;
    private LocalDateTime updateDate;


    public CommentReadDTO(Comment comment){
        this.id = comment.getId();
        if(comment.getMember()==null){
            this.writer = "(알수없음)";
        }else{
            this.writer = comment.getMember().getName();
        }
        this.content = comment.getContent();
        this.writeDate = comment.getWriteDate();
        this.updateDate = comment.getUpdateDate();
    }

}
