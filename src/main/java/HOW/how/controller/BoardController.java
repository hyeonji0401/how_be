package HOW.how.controller;

import HOW.how.domain.Board;
import HOW.how.dto.BoardCreateDTO;
import HOW.how.dto.BoardReadDTO;
import HOW.how.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    
    //글 작성
    @PostMapping("/create")
    public ResponseEntity<Board> create(@RequestBody BoardCreateDTO boardCreateDTO){
        System.out.println("제목"+boardCreateDTO.getTitle());
        System.out.println("내용"+boardCreateDTO.getContent());
        return ResponseEntity.ok(boardService.create(boardCreateDTO));
    }

    //글 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<BoardReadDTO>> getAllPost(){
        return ResponseEntity.ok(this.boardService.getAllPost());
    }

    //글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardReadDTO> getDetailPost(@PathVariable("id") String id){
        return ResponseEntity.ok(this.boardService.getDetailPost(id));
    }
}
