package tutorial.Test.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import tutorial.Test.user.model.UserDTO;


import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<UserDTO> findOne(Long id);

    Optional<UserDTO> findMe();
}
