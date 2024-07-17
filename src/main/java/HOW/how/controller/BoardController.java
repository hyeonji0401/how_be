package HOW.how.controller;

import HOW.how.domain.Board;
import HOW.how.dto.BoardCreateDTO;
import HOW.how.dto.BoardReadDTO;
import HOW.how.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    
    //글 작성
    @PostMapping("post/create")
    public ResponseEntity<Board> create(@RequestBody BoardCreateDTO boardCreateDTO){
        return ResponseEntity.ok(boardService.create(boardCreateDTO));
    }

    //글 전체 조회
    @GetMapping("board/list")
    public ResponseEntity<List<BoardReadDTO>> getAllPost(){
        return ResponseEntity.ok(this.boardService.getAllPost());
    }

    //글 상세 조회
    @GetMapping("board/{id}")
    public ResponseEntity<BoardReadDTO> getDetailPost(@PathVariable("id") String id){
        return ResponseEntity.ok(this.boardService.getDetailPost(id));
    }

    //글 수정
    @PutMapping("post/update/{id}")
    public ResponseEntity<BoardCreateDTO> updatePost(@PathVariable("id") String id, @RequestBody BoardCreateDTO boardCreateDTO){
        System.out.println(boardCreateDTO.getTitle());
        return ResponseEntity.ok(this.boardService.updatePost(id, boardCreateDTO));
    }

    //게시글 삭제
    @DeleteMapping("post/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id){
        try {
            this.boardService.deletePost(id);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    //게시글 검색
    @GetMapping("board/list/search")
    public ResponseEntity<List<BoardReadDTO>> SearchPostWithKeyword(@RequestParam(value = "keyword") String keyword){
        return ResponseEntity.ok(boardService.searchPostWithKeyword(keyword));
    }

}
