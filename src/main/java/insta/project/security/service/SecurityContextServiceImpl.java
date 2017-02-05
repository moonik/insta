package insta.project.security.service;

import insta.project.user.domain.UserAccount;
import insta.project.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class SecurityContextServiceImpl implements SecurityContextService {

    private final UserRepository userRepository;

    @Autowired
    public SecurityContextServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserAccount currentUserAccount() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Optional<UserAccount> currentUser = userRepository.findOneByUsername(authentication.getName());
        // TODO It may be better to return optional.
        return currentUser.orElse(null);
    }
}
