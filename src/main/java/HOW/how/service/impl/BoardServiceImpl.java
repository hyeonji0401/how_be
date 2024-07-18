package HOW.how.service.impl;

import HOW.how.domain.Board;
import HOW.how.domain.Member;
import HOW.how.dto.BoardCreateDTO;
import HOW.how.dto.BoardReadDTO;
import HOW.how.repository.BoardRepository;
import HOW.how.repository.MemberRepository;
import HOW.how.service.BoardService;
import HOW.how.service.GetAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final GetAuthenticationService getAuthenticationService;



    //게시물 작성
    public Board create(BoardCreateDTO boardCreateDTO){
        Board board = new Board();
        board.setTitle(boardCreateDTO.getTitle());
        System.out.println(board.getTitle());
        board.setContent(boardCreateDTO.getContent());
        System.out.println(board.getContent());
        board.setMember(getAuthenticationService.getAuthentication());
        board.setWriteDate(LocalDateTime.now());
        this.boardRepository.save(board);
        return board;
    }

    //게시물 전체 조회
    public List<BoardReadDTO> getAllPost(String keyword){
        if(keyword.isEmpty()){
            List<Board> boards = this.boardRepository.findAll();
            return boards.stream()
                    .map(BoardReadDTO::new)
                    .collect(Collectors.toList());

        }
        //게시물 검색
        else{
            List<Board> allBoards = boardRepository.findAll();
            List<Board> matchedBoards = new ArrayList<>();

            for (Board board : allBoards) {
                String title = board.getTitle();
                String contentWithoutHtml = removeHtmlTags(board.getContent());

                if (title.contains(keyword) || contentWithoutHtml.contains(keyword)) {
                    System.out.println("게시글:"+board.getContent()+"제목:"+board.getTitle());
                    matchedBoards.add(board);
                }
            }

            return matchedBoards.stream()
                    .distinct()
                    .map(BoardReadDTO::new)
                    .collect(Collectors.toList());
        }


    }

    //게시물 상세 조회
    public BoardReadDTO getDetailPost(String id){
        Optional<Board> optionalBoard = this.boardRepository.findById(id);
        return new BoardReadDTO(optionalBoard.get());
    }

    //게시물 수정
    public BoardCreateDTO updatePost(String id, BoardCreateDTO boardCreateDTO){
        Optional<Board> optionalBoard = this.boardRepository.findById(id);
        Board board = optionalBoard.orElseThrow(() -> new NoSuchElementException("Post not found"));

        if(!getAuthenticationService.getAuthentication().equals(board.getMember())){
            throw new SecurityException("You are not the owner of this post");
        }

        board.setTitle(boardCreateDTO.getTitle());
        board.setContent(boardCreateDTO.getContent());
        board.setUpdateDate(LocalDateTime.now());
        this.boardRepository.save(board);
        return boardCreateDTO;
    }

    //게시물 삭제
    public void deletePost(String id){
        Optional<Board> boardOpt = this.boardRepository.findById(id);
        Board board = boardOpt.orElseThrow(() -> new NoSuchElementException("Post not found"));
        if(!getAuthenticationService.getAuthentication().equals(board.getMember())){
            throw new SecurityException("You are not the owner of this post");
        }
        this.boardRepository.delete(board);
    }

    //마크업 태그 파싱 함수
    private String removeHtmlTags(String content) {
        return Jsoup.clean(content, Safelist.none());
    }


}
