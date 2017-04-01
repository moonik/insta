package insta.project.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    private String username;

    @NotNull
    private String password;

    @ManyToMany
    @JoinTable(
            name="Followings",
            joinColumns=@JoinColumn(name="follower", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="following", referencedColumnName="ID"))
    @JsonIgnore
    private List<UserAccount> followings;

    @ManyToMany
    @JoinTable(
            name="Followers",
            joinColumns=@JoinColumn(name="following", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="follower", referencedColumnName="ID"))
    @JsonIgnore
    private List<UserAccount> followers;

    public List<UserAccount> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserAccount> followers) {
        this.followers = followers;
    }

    public List<UserAccount> getFollowings() {
        return followings;
    }

    public void setFollowings(List<UserAccount> followings) {
        this.followings = followings;
    }

    @Override
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_USER");
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
