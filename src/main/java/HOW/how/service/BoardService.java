package HOW.how.service;


import HOW.how.domain.Board;
import HOW.how.dto.BoardCreateDTO;

public interface BoardService {
    Board create(BoardCreateDTO boardCreateDTO);
}
