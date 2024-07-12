package HOW.how.service.impl;

import HOW.how.domain.Board;
import HOW.how.domain.Member;
import HOW.how.dto.BoardCreateDTO;
import HOW.how.dto.BoardReadDTO;
import HOW.how.repository.BoardRepository;
import HOW.how.repository.MemberRepository;
import HOW.how.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    //사용자 찾기
    public Member getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Optional<Member> member = memberRepository.findByEmail(username);
        return member.get();

    }

    //게시물 작성
    public Board create(BoardCreateDTO boardCreateDTO){
        Board board = new Board();
        board.setTitle(boardCreateDTO.getTitle());
        System.out.println(board.getTitle());
        board.setContent(boardCreateDTO.getContent());
        System.out.println(board.getContent());
        board.setMember(getAuthentication());
        board.setWriteDate(LocalDateTime.now());
        this.boardRepository.save(board);
        return board;
    }

    //게시물 전체 조회
    public List<BoardReadDTO> getAllPost(){
        List<Board> boards = this.boardRepository.findAll();
        return boards.stream()
                .map(BoardReadDTO::new)
                .collect(Collectors.toList());
    }

    //게시물 상세 조회
    public BoardReadDTO getDetailPost(String id){
        Optional<Board> OptionalBoard = this.boardRepository.findById(id);
        return new BoardReadDTO(OptionalBoard.get());
    }


}
