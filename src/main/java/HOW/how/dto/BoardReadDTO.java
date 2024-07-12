package HOW.how.dto;

import HOW.how.domain.Board;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardReadDTO {

    private String boardId;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime writeDate;
    private LocalDateTime updateDate;

    public BoardReadDTO(Board board) {
        this.boardId = board.getId();
        this.writer = board.getMember().getName();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writeDate = board.getWriteDate();
        this.updateDate = board.getUpdateDate();
    }

}
