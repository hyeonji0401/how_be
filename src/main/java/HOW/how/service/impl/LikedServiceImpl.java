package HOW.how.service.impl;

import HOW.how.domain.Board;
import HOW.how.domain.Liked;
import HOW.how.domain.Member;
import HOW.how.repository.BoardRepository;
import HOW.how.repository.LikedRepository;
import HOW.how.repository.MemberRepository;
import HOW.how.service.LikedService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikedServiceImpl implements LikedService {
    private final BoardRepository boardRepository;
    private final LikedRepository likedRepository;
    private final MemberRepository memberRepository;

    @Override
    public Optional<Boolean> toggleLiked(String postId, User user){
        Board board = boardRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // 사용자 찾기
        Member member = memberRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // "좋아요" 상태 확인
        Optional<Liked> likeOpt = likedRepository.findByBoardAndMember(board, member);

        if (likeOpt.isPresent()) {
            // "좋아요"가 이미 있으면 삭제
            Liked liked = likeOpt.get();
            likedRepository.delete(liked);
            board.getLikeds().removeIf(l -> l.getId().equals(liked.getId()));
        } else {
            // "좋아요"가 없으면 추가
            Liked liked = new Liked();
            liked.setMember(member);
            liked.setBoard(board);
            likedRepository.save(liked);
            board.getLikeds().add(liked);
        }

        // 게시글 저장
        boardRepository.save(board);

        // "좋아요" 상태 반환 (존재하면 false, 없으면 true)
        return Optional.of(!likeOpt.isPresent());
    }


}
