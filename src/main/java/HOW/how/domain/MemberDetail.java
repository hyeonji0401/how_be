package HOW.how.domain;

import HOW.how.enums.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "memberDetail")
public class MemberDetail {
    @Id
    private String detailId;
    @DBRef //참조
    private Member memberId;
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

