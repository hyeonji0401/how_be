package HOW.how.controller;

import HOW.how.domain.Board;
import HOW.how.dto.BoardCreateDTO;
import HOW.how.dto.BoardReadDTO;
import HOW.how.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    
    //글 작성
    @PostMapping("post/create")
    public ResponseEntity<?> create(@RequestBody BoardCreateDTO boardCreateDTO){
        try {
            Board board = boardService.create(boardCreateDTO);
            return ResponseEntity.ok(board);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터베이스에 접근하는 동안 문제가 발생했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글을 생성하는 동안 예상치 못한 오류가 발생했습니다.");
        }
    }

    //글 전체 조회 & 검색
    @GetMapping("board/list")
    public ResponseEntity<?> getAllPost(@RequestParam(value = "keyword", defaultValue = "") String keyword){
        try {
            //url 한글 디코딩
            keyword = URLDecoder.decode(keyword, "UTF-8");
            System.out.println(keyword);
            List<BoardReadDTO> posts = this.boardService.getAllPost(keyword);
            return ResponseEntity.ok(posts);
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("키워드 인코딩에 문제가 발생했습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터베이스에 접근하는 동안 문제가 발생했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글을 검색하는 동안 예상치 못한 오류가 발생했습니다.");
        }
    }


    //글 상세 조회
    @GetMapping("board/{id}")
    public ResponseEntity<?> getDetailPost(@PathVariable("id") String id){
        try {
            BoardReadDTO post = this.boardService.getDetailPost(id);
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터베이스에 접근하는 동안 문제가 발생했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글을 검색하는 동안 예상치 못한 오류가 발생했습니다.");
        }
    }

    //글 수정
    @PutMapping("post/update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") String id, @RequestBody BoardCreateDTO boardCreateDTO){
        try {
            this.boardService.updatePost(id, boardCreateDTO);
            return ResponseEntity.ok("Post update Successfully");
        }catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not the owner of this post");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    //게시글 삭제
    @DeleteMapping("post/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id){
        try {
            this.boardService.deletePost(id);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not the owner of this post");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
    
}
