package HOW.how.service;

import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface LikedService {
    Optional<Boolean> toggleLiked(String postId, User user);
}
