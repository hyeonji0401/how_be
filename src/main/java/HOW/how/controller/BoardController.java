package HOW.how.controller;

import HOW.how.domain.Board;
import HOW.how.dto.BoardCreateDTO;
import HOW.how.dto.BoardReadDTO;
import HOW.how.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/list")
    public ResponseEntity<List<BoardReadDTO>> getAllPost(){
        return ResponseEntity.ok(this.boardService.getAllPost());
    }
}
