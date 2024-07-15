package HOW.how.dto;

import HOW.how.domain.Board;
import HOW.how.domain.Comment;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BoardReadDTO {

    private String boardId;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime writeDate;
    private LocalDateTime updateDate;
    private List<CommentReadDTO> commentReadDTOS = new ArrayList<>();
    private int likedCount;

    public BoardReadDTO(Board board) {
        this.boardId = board.getId();
        this.writer = board.getMember().getName();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writeDate = board.getWriteDate();
        this.updateDate = board.getUpdateDate();
        if (board.getCommentList() != null) {
            this.commentReadDTOS = board.getCommentList().stream()
                    .map(CommentReadDTO::new)
                    .collect(Collectors.toList());
        }
        if(board.getLikeds()!=null){
            this.likedCount = board.getLikeds().size();
        }

    }

}
