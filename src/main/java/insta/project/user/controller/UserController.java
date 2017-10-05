package insta.project.user.controller;

import insta.project.user.domain.OnlineUsers;
import insta.project.user.domain.UserAccount;
import insta.project.user.exceptions.UserFollowException;
import insta.project.user.exceptions.UserNotExistsFoundException;
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
        return userService.getUser(search);
    }

    /**
     * follows user
     *
     * @param following - whos follow(current user)
     * @return save followers
     */
    @PostMapping("follow/{following}")
    public List<UserAccount> follow(@PathVariable("following") String following) {
        return userService.follow(following);
    }

    /**
     * gets my followers
     *
     * @return followers
     */
    @GetMapping("myFollowers")
    public List<UserAccount> getFollowers() {
        return userService.getFollowers();
    }

    /**
     * gets people that i follow
     *
     * @return followings
     */
    @GetMapping("iFollow")
    public List<UserAccount> getFollowings() {
        return userService.getFollowings();
    }

    @GetMapping("profileFollowings/{name}")
    public List<UserAccount> getUserFollowings(@PathVariable("name") String user) {
        return userService.getUserFollowings(user);
    }

    @GetMapping("profileFollowers/{name}")
    public List<UserAccount> getUserFollowers(@PathVariable("name") String user) {
        return userService.getUserFollowers(user);
    }

    @GetMapping("check/{userProfile}")
    public void checkIfFollow(@PathVariable("userProfile") String userProfile) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        UserAccount currUser = userRepository.findOneByUsername(currentUser).get();
        UserAccount user = userRepository.findOneByUsername(userProfile).get();
        List<UserAccount> followings = currUser.getFollowings();

        if (followings.contains(user)) {
            throw new RuntimeException();
        }
    }

    @PostMapping("setOnlineUser/{username}")
    public List<OnlineUsers> setOnlineUser(@PathVariable("username") String userName) {
        return userService.setOnlineUser(userName);
    }

    @DeleteMapping("goOffline/{username}")
    public void offline(@PathVariable("username") String userName) {
        userService.offline(userName);
    }

    @GetMapping("onlineUsers")
    public List<OnlineUsers> getOnlineUsers() {
        return userService.getOnlineUsers();
    }

    @GetMapping("checkOnline/{username}")
    public boolean checkUser(@PathVariable("username") String userName){
        return userService.checkUser(userName);
    }
}
