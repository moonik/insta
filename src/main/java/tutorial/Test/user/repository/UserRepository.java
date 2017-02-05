package tutorial.Test.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tutorial.Test.user.domain.UserAccount;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findOneByUsername(String username);
}
