package HOW.how.service.impl;

import HOW.how.domain.Board;
import HOW.how.domain.Comment;
import HOW.how.domain.Member;
import HOW.how.dto.CommentCreateDTO;
import HOW.how.repository.BoardRepository;
import HOW.how.repository.CommentRepository;
import HOW.how.service.CommentService;
import HOW.how.service.GetAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final GetAuthenticationService getAuthenticationService;
    private final BoardRepository boardRepository;

    //댓글 생성
    @Override
    public CommentCreateDTO create(CommentCreateDTO commentCreateDTO, String id){
        try {
            Member member = getAuthenticationService.getAuthentication();
            Comment comment = new Comment();
            comment.setMember(member);
            comment.setBoardId(id);
            comment.setContent(commentCreateDTO.getContent());
            comment.setWriteDate(LocalDateTime.now());
            this.commentRepository.save(comment);
            Optional<Board> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isEmpty()) {
                throw new RuntimeException("게시판을 찾을 수 없습니다.");
            }
            Board board = optionalBoard.get();
            if (board.getCommentList() == null) {
                board.setCommentList(new ArrayList<>());
            }
            board.getCommentList().add(comment);
            this.boardRepository.save(board);
            return commentCreateDTO;
        } catch (Exception e) {
            // 예외 처리 로직
            throw new RuntimeException("댓글을 생성하는 동안 오류가 발생했습니다.", e);
        }
    }

    //댓글 수정
    @Override
    public CommentCreateDTO update(CommentCreateDTO commentCreateDTO, String commentId){
        Optional<Comment> optionalComment = this.commentRepository.findById(commentId);
        if(optionalComment.isEmpty()){
            throw new RuntimeException("해당 댓글을 찾을 수 없습니다.");
        }
        Comment comment = optionalComment.get();
        comment.setContent(commentCreateDTO.getContent());
        comment.setUpdateDate(LocalDateTime.now());
        this.commentRepository.save(comment);
        return commentCreateDTO;
    }

    //댓글 삭제
    @Override
    public void delete(String boardId, String commentId){
        Optional<Comment> optionalComment = this.commentRepository.findById(commentId);
        if(optionalComment.isPresent()){
            this.commentRepository.deleteById(commentId);
        } else {
            throw new NoSuchElementException("Comment not found");
        }

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            board.getCommentList().removeIf(comment -> comment.getId().equals(commentId));
            boardRepository.save(board);
        } else {
            throw new RuntimeException("게시판을 찾을 수 없습니다.");
        }
    }
}
