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
        Optional<Member>memberOpt = memberRepository.findByEmail(user.getUsername());
        if(memberOpt.isPresent()){
            Member member = memberOpt.get();
            Optional<Liked> likeOpt = likedRepository.findByBoardAndMember(board, member);
            if (likeOpt.isPresent()) {
                likedRepository.delete(likeOpt.get());
                board.getLikeds().removeIf(liked -> liked.getId().equals(likeOpt.get().getId()));
                boardRepository.save(board);
                return Optional.of(false); // "좋아요" 삭제
            } else {
                Liked liked = new Liked();
                liked.setMember(member);
                liked.setBoard(board);
                likedRepository.save(liked);
                board.getLikeds().add(liked);
                boardRepository.save(board);
                return Optional.of(true); // "좋아요" 추가
            }
        }
        else{
            return Optional.empty(); // 사용자 찾을 수 없음
        }
    }


}
