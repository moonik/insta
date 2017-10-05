package insta.project.user.service;

import insta.project.security.service.SecurityContextService;
import insta.project.user.domain.OnlineUsers;
import insta.project.user.domain.UserAccount;
import insta.project.user.exceptions.UserFollowException;
import insta.project.user.exceptions.UserNotExistsFoundException;
import insta.project.user.model.UserDTO;
import insta.project.user.repository.OnlineUsersRepository;
import insta.project.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SecurityContextService securityContextService;
    private final OnlineUsersRepository onlineUsersRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SecurityContextService securityContextService, OnlineUsersRepository onlineUsersRepository) {
        this.userRepository = userRepository;
        this.securityContextService = securityContextService;
        this.onlineUsersRepository = onlineUsersRepository;
    }

    @Override
    public Optional<UserDTO> findOne(Long id) {
        final UserAccount currentUser = securityContextService.currentUserAccount();
        final UserDTO user = new UserDTO(userRepository.findOne(id));
        if (currentUser == null) return Optional.empty();
        user.setMyself(user.getId() == currentUser.getId());
        return Optional.of(user);
    }

    @Override
    public Optional<UserDTO> findMe() {
        final UserAccount currentUser = securityContextService.currentUserAccount();
        return findOne(currentUser.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<UserAccount> user = userRepository.findOneByUsername(username);
        final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
        user.ifPresent(detailsChecker::check);
        return user.orElseThrow(() -> new UsernameNotFoundException("user not found."));
    }

    @Override
    public UserAccount getUser(String search) {
        UserAccount userAccount = userRepository.findOneByUsername(search).get();
        if (userAccount == null) {
            throw new UserNotExistsFoundException("User doesn't exist");
        } else
            return userAccount;
    }

    @Override
    public List<UserAccount> follow(String following) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String current = auth.getName();

        UserAccount currentUser = userRepository.findOneByUsername(current).get();
        UserAccount userFollow = userRepository.findOneByUsername(following).get();

        if (currentUser.getId() == userFollow.getId()) {
            throw new UserFollowException("Attempt to subscribe to yourself");
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

    @Override
    public List<UserAccount> getFollowers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();
        UserAccount currentUser = userRepository.findOneByUsername(owner).get();
        return currentUser.getFollowers();
    }

    @Override
    public List<UserAccount> getFollowings() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();
        UserAccount currentUser = userRepository.findOneByUsername(owner).get();
        return currentUser.getFollowings();
    }

    @Override
    public List<UserAccount> getUserFollowings(String user) {
        UserAccount userAccount = userRepository.findOneByUsername(user).get();
        return userAccount.getFollowings();
    }

    @Override
    public List<UserAccount> getUserFollowers(String user) {
        UserAccount userAccount = userRepository.findOneByUsername(user).get();
        return userAccount.getFollowers();
    }

    @Override
    public List<OnlineUsers> setOnlineUser(String userName) {
        UserAccount user = userRepository.findOneByUsername(userName).get();
        OnlineUsers userOnline = new OnlineUsers(user);
        List<OnlineUsers> onlineUsers = onlineUsersRepository.findAll();
        onlineUsers.add(userOnline);
        return onlineUsersRepository.save(onlineUsers);
    }

    @Override
    public void offline(String userName) {
        OnlineUsers user = onlineUsersRepository.getUser(userRepository.findOneByUsername(userName).get().getId());
        onlineUsersRepository.delete(user.getId());
    }

    @Override
    public List<OnlineUsers> getOnlineUsers() {
        return onlineUsersRepository.findAll();
    }

    public boolean checkUser(String userName){
        UserAccount user = userRepository.findOneByUsername(userName).get();
        return onlineUsersRepository.getUser(user.getId()) != null;
    }
}
