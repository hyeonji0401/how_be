package HOW.how.controller;


import HOW.how.dto.CommentCreateDTO;
import HOW.how.dto.CommentReadDTO;
import HOW.how.service.CommentService;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
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
    public ResponseEntity<?> createComment(@RequestBody CommentCreateDTO commentCreateDTO, @PathVariable ("id") String boardId){
        try {
            CommentCreateDTO comment = commentService.create(commentCreateDTO, boardId);
            return ResponseEntity.ok(comment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터베이스에 접근하는 동안 문제가 발생했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글을 생성하는 동안 예상치 못한 오류가 발생했습니다.");
        }
    }

    //댓글 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@RequestBody CommentCreateDTO commentCreateDTO,
                                           @PathVariable("id") String commentId){
        try {
            this.commentService.update(commentCreateDTO, commentId);
            return ResponseEntity.ok("comment update successfully");
        }catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comment not found");
        }catch (java.lang.SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not the owner of this comment");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    //댓글 삭제
    @DeleteMapping("{boardId}/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("boardId") String boardId ,@PathVariable("commentId") String commentId){
        try {
            this.commentService.delete(boardId, commentId);
            return ResponseEntity.ok("Comment deleted successfully");
        }catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comment not found");
        }catch (java.lang.SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not the owner of this comment");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

}
