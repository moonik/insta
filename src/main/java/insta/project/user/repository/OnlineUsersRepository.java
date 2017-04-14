package insta.project.user.repository;

import insta.project.user.domain.OnlineUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OnlineUsersRepository extends JpaRepository<OnlineUsers, Long>{

    @Query("select u from OnlineUsers u JOIN u.userId ua Where ua.id = ?1")
    OnlineUsers checkUser(Long id);

}
