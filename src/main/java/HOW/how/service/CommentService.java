package HOW.how.service;

import HOW.how.dto.CommentCreateDTO;

public interface CommentService {
    CommentCreateDTO create(CommentCreateDTO commentCreateDTO, String id);
    CommentCreateDTO update(CommentCreateDTO commentCreateDTO, String commentId);
    void delete(String boardId, String commentId);
}
