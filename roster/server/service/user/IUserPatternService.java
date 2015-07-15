package server.service.user;

import service.exception.AdminAccessRequiredException;

import java.util.List;

public interface IUserPatternService {
    void initialize();
    void addUserPattern(User loggedUser, UserPattern userPattern) throws AdminAccessRequiredException;
     List<UserPattern> readUserPatternList() throws AdminAccessRequiredException;
}
