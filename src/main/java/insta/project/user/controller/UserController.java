package insta.project.user.controller;

import insta.project.user.domain.UserAccount;
import insta.project.user.exceptions.UserFollowException;
import insta.project.user.model.UserDTO;
import insta.project.user.model.UserParams;
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
     * @param search - user name
     * @return user
     */
    @GetMapping("search/{search}")
    public UserAccount getUser(@PathVariable("search") String search) {
        UserAccount userAccount = userRepository.findByUsername(search);
        if (userAccount == null) {
            throw new UserNotFoundException();
        }else
        return userAccount;
    }

    /**
     * follows user
     * @param following - whos follow(current user)
     * @return save followers
     */
    @PostMapping("follow/{following}")
    public List<UserAccount> follow(@PathVariable("following") Long following) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();

        UserAccount currentUser = userRepository.findByUsername(owner);
        UserAccount userFollow = userRepository.findOne(following);

        if(currentUser.getId() == userFollow.getId()){
            throw new UserFollowException();
        }

        List<UserAccount> followings = currentUser.getFollowings();
        List<UserAccount> followers = userFollow.getFollowers();
        if(followings.contains(userFollow)){
            throw new UserFollowException();
        }

        followings.add(userFollow);
        followers.add(currentUser);

        return userRepository.save(followings);

    }

    /**
     * gets my followers
     * @return followers
     */
    @GetMapping("myFollowers")
    public List<UserAccount> getFollowers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();

        UserAccount currentUser = userRepository.findByUsername(owner);

        List<UserAccount> followers = currentUser.getFollowers();
        return followers;
    }

    /**
     * gets people that i follow
     * @return followings
     */
    @GetMapping("iFollow")
    public List<UserAccount> getFollowings() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();

        UserAccount currentUser = userRepository.findByUsername(owner);

        List<UserAccount> followings = currentUser.getFollowings();

        return followings;
    }


}
