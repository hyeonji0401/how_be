package HOW.how.controller;


import HOW.how.dto.CommentCreateDTO;
import HOW.how.dto.CommentReadDTO;
import HOW.how.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

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
    @PutMapping("/update/{id}")
    public ResponseEntity<CommentCreateDTO> updateComment(@RequestBody CommentCreateDTO commentCreateDTO,
                                                        @PathVariable("id") String commentId){
        return ResponseEntity.ok(commentService.update(commentCreateDTO, commentId));
    }

    //댓글 삭제
    @DeleteMapping("{boardId}/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("boardId") String boardId ,@PathVariable("commentId") String commentId){
        try {
            this.commentService.delete(boardId, commentId);
            return ResponseEntity.ok("Comment deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

}
