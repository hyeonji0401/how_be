package HOW.how.service;

import HOW.how.dto.CommentCreateDTO;

public interface CommentService {
    CommentCreateDTO create(CommentCreateDTO commentCreateDTO, String id);
}
