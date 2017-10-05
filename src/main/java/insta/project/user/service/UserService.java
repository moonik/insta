package insta.project.user.service;

import insta.project.user.domain.OnlineUsers;
import insta.project.user.domain.UserAccount;
import insta.project.user.model.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<UserDTO> findOne(Long id);
    Optional<UserDTO> findMe();
    UserAccount getUser(String search);
    List<UserAccount> follow(String following);
    List<UserAccount> getFollowers();
    List<UserAccount> getFollowings();
    List<UserAccount> getUserFollowings(String user);
    List<UserAccount> getUserFollowers(String user);
    List<OnlineUsers> setOnlineUser(String userName);
    void offline(String userName);
    List<OnlineUsers> getOnlineUsers();
    boolean checkUser(String userName);
}
