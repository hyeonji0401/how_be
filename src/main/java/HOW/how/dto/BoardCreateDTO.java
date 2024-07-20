package HOW.how.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardCreateDTO {
    private String title;
    private String content;
}
