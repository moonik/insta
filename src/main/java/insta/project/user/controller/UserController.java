package insta.project.user.controller;

import insta.project.user.domain.OnlineUsers;
import insta.project.user.domain.UserAccount;
import insta.project.user.exceptions.UserFollowException;
import insta.project.user.model.UserDTO;
import insta.project.user.model.UserParams;
import insta.project.user.repository.OnlineUsersRepository;
import insta.project.user.repository.UserRepository;
import insta.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private OnlineUsersRepository onlineUsersRepository;

    @PersistenceContext
    EntityManager em;

    @RequestMapping(method = RequestMethod.POST)
    public UserAccount create(@Valid @RequestBody UserParams params) {
        return userRepository.save(params.toUser());
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO showMe() {
        return userService.findMe().orElseThrow(UserNotFoundException::new);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No user")
    private class UserNotFoundException extends RuntimeException {
    }

    /**
     * searches user by name
     *
     * @param search - user name
     * @return user
     */
    @GetMapping("search/{search}")
    public UserAccount getUser(@PathVariable("search") String search) {
        UserAccount userAccount = userRepository.findByUsername(search);
        if (userAccount == null) {
            throw new UserNotFoundException();
        } else
            return userAccount;
    }

    /**
     * follows user
     *
     * @param following - whos follow(current user)
     * @return save followers
     */
    @PostMapping("follow/{following}")
    public List<UserAccount> follow(@PathVariable("following") String following) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String current = auth.getName();

        UserAccount currentUser = userRepository.findByUsername(current);
        UserAccount userFollow = userRepository.findByUsername(following);

        if (currentUser.getId() == userFollow.getId()) {
            throw new UserFollowException();
        }

        List<UserAccount> followings = currentUser.getFollowings();
        List<UserAccount> followers = userFollow.getFollowers();
        if (followings.contains(userFollow)) {
            followings.remove(userFollow);
            followers.remove(currentUser);
        } else {

            followings.add(userFollow);
            followers.add(currentUser);
        }

        return userRepository.save(followings);

    }

    /**
     * gets my followers
     *
     * @return followers
     */
    @GetMapping("myFollowers")
    public List<UserAccount> getFollowers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();
        UserAccount currentUser = userRepository.findByUsername(owner);
        return currentUser.getFollowers();
    }

    /**
     * gets people that i follow
     *
     * @return followings
     */
    @GetMapping("iFollow")
    public List<UserAccount> getFollowings() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();
        UserAccount currentUser = userRepository.findByUsername(owner);
        return currentUser.getFollowings();
    }

    @GetMapping("profileFollowings/{name}")
    public List<UserAccount> getUserFollowings(@PathVariable("name") String user) {
        UserAccount userAccount = userRepository.findByUsername(user);
        return userAccount.getFollowings();
    }

    @GetMapping("profileFollowers/{name}")
    public List<UserAccount> getUserFollowers(@PathVariable("name") String user) {
        UserAccount userAccount = userRepository.findByUsername(user);
        return userAccount.getFollowers();
    }

    @GetMapping("check/{userProfile}")
    public void checkIfFollow(@PathVariable("userProfile") String userProfile) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        UserAccount currUser = userRepository.findByUsername(currentUser);
        UserAccount user = userRepository.findByUsername(userProfile);
        List<UserAccount> followings = currUser.getFollowings();

        if (followings.contains(user)) {
            throw new RuntimeException();
        }
    }

    @PostMapping("setOnlineUser/{username}")
    public List<OnlineUsers> setOnlineUser(@PathVariable("username") String userName) {
        UserAccount user = userRepository.findByUsername(userName);
        OnlineUsers userOnline = new OnlineUsers(user);
        List<OnlineUsers> onlineUsers = onlineUsersRepository.findAll();
        onlineUsers.add(userOnline);

        return onlineUsersRepository.save(onlineUsers);
    }

    @DeleteMapping("goOffline/{username}")
    public void offline(@PathVariable("username") String userName) {
        OnlineUsers user = onlineUsersRepository.getUser(userRepository.findOneByUsername(userName).get().getId());
        onlineUsersRepository.delete(user.getId());
    }

    @GetMapping("onlineUsers")
    public List<OnlineUsers> getOnlineUsers() {
        return onlineUsersRepository.findAll();
    }

    @GetMapping("checkOnline/{username}")
    public boolean checkUser(@PathVariable("username") String userName){
        UserAccount user = userRepository.findByUsername(userName);
        return onlineUsersRepository.getUser(user.getId()) != null;
    }
}
