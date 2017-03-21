package insta.project.user.controller;

import insta.project.Follower.Follower;
import insta.project.Follower.FollowerDTO;
import insta.project.Follower.FollowerRepo;
import insta.project.Follower.FollowerService;
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

    @Autowired
    private FollowerService followerService;

    @Autowired
    private FollowerRepo followerRepo;

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

    @GetMapping("search/{search}")
    public UserAccount getUser(@PathVariable("search") String search) {
        UserAccount userAccount = userRepository.findByUsername(search);
        if (userAccount == null) {
            throw new UserNotFoundException();
        }else
        return userAccount;
    }

    @PostMapping("follow/{following}")
    public Follower follow(@PathVariable("following") String following) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();

        UserAccount currentUser = userRepository.findByUsername(owner);
        UserAccount userFollow = userRepository.findByUsername(following);
        if (currentUser.getUsername().equals(userFollow.getUsername()) || userFollow.getUsername() == null || followerRepo.ifFollowed(currentUser.getUsername(), userFollow.getUsername())) {

            throw new UserFollowException();
        }

        FollowerDTO followerDTO = new FollowerDTO(currentUser.getUsername(), userFollow.getUsername());

        return followerService.saveFollowers(followerDTO);

    }

    @GetMapping("myFollowers")
    public List<Follower> getFollowers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();

        UserAccount currentUser = userRepository.findByUsername(owner);

        List<Follower> followers = followerRepo.findFollowersByName(currentUser.getUsername());

        return followers;
    }

    @GetMapping("iFollow")
    public List<Follower> getFollowings() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();

        UserAccount currentUser = userRepository.findByUsername(owner);

        List<Follower> followers = followerRepo.findFollowingByName(currentUser.getUsername());

        return followers;
    }


}
