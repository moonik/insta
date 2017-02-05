package insta.project.security.service;


import insta.project.user.domain.UserAccount;

public interface SecurityContextService {
    UserAccount currentUserAccount();
}
