package tutorial.Test.security.service;


import tutorial.Test.user.domain.UserAccount;

public interface SecurityContextService {
    UserAccount currentUserAccount();
}
