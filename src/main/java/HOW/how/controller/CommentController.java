package HOW.how.controller;


import HOW.how.dto.CommentCreateDTO;
import HOW.how.dto.CommentReadDTO;
import HOW.how.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    //댓글 등록
    @PostMapping("/create/{id}")
    public ResponseEntity<CommentCreateDTO> createComment(@RequestBody CommentCreateDTO commentCreateDTO, @PathVariable ("id") String boardId){
        return ResponseEntity.ok(commentService.create(commentCreateDTO, boardId));
    }

    //댓글 수정
    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentCreateDTO> updateComment(@RequestBody CommentCreateDTO commentCreateDTO,
                                                        @PathVariable("commentId") String commentId){
        return ResponseEntity.ok(commentService.update(commentCreateDTO, commentId));
    }

}
