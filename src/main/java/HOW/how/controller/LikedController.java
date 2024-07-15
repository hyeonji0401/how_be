package HOW.how.controller;


import HOW.how.service.LikedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post/{id}/liked")
public class LikedController {
    private final LikedService likedService;

    @PostMapping
    public ResponseEntity<?> toggleLike(@PathVariable("id") String postId, @AuthenticationPrincipal User user){
        try {
            Optional<Boolean> isLikedOpt = likedService.toggleLiked(postId, user);
            if (isLikedOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자를 찾을 수 없습니다.");
            } else if (isLikedOpt.get()) {
                return ResponseEntity.ok("좋아요가 추가되었습니다.");
            } else {
                return ResponseEntity.ok("좋아요가 삭제되었습니다.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
