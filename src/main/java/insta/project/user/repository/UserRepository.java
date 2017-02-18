package insta.project.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import insta.project.user.domain.UserAccount;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findOneByUsername(String username);
    UserAccount findByUsername(String username);
}
