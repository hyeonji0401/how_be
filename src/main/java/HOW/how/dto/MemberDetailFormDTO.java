package HOW.how.dto;
import HOW.how.enums.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberDetailFormDTO {
    @NotNull(message = "사용자 정보가 정상적으로 처리되지 않음")
    private int age;
    private BothHands bothHands;
    private Eyesight eyesight;
    private Handwork handwork;
    private LiftPower liftPower;
    private LstnTalk lstnTalk;
    private StndWalk stndWalk;
    private String jobNm;
    private String career;
    private String education;
    private String location;
    private List<String> licenses;
}
