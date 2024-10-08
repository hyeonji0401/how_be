package HOW.how.service;


import HOW.how.domain.Board;
import HOW.how.dto.BoardCreateDTO;
import HOW.how.dto.BoardReadDTO;

import java.util.List;

public interface BoardService {
    Board create(BoardCreateDTO boardCreateDTO);
    List<BoardReadDTO> getAllPost(String keyword);
    BoardReadDTO getDetailPost(String id);
    BoardCreateDTO updatePost(String id, BoardCreateDTO boardCreateDTO);
    void deletePost(String id);

//    List<BoardReadDTO> searchPostWithKeyword(String keyword);
}
