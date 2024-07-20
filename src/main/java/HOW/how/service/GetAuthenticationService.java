package HOW.how.service;

import HOW.how.domain.Member;
import HOW.how.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAuthenticationService {

    private final MemberRepository memberRepository;
    public Member getAuthentication() throws UsernameNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Cannot find the current user's authentication. Are you sure you're logged in?");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetails userDetails)) {
            throw new IllegalArgumentException("Principal must be an instance of UserDetails");
        }

        String username = userDetails.getUsername();
        Optional<Member> member = memberRepository.findByEmail(username);

        if (member.isEmpty()) {
            throw new UsernameNotFoundException("Cannot find user with email: " + username);
        }

        return member.get();
    }

}
