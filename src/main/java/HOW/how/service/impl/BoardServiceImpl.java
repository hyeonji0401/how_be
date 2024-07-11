package HOW.how.service.impl;

import HOW.how.domain.Board;
import HOW.how.domain.Member;
import HOW.how.dto.BoardCreateDTO;
import HOW.how.repository.BoardRepository;
import HOW.how.repository.MemberRepository;
import HOW.how.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    public Member getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Optional<Member> member = memberRepository.findByEmail(username);
        return member.get();

    }

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
}
