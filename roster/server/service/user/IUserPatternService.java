package server.service.user;

import service.exception.AdminAccessRequiredException;

import java.util.List;

public interface IUserPatternService {
    void addUserPattern(User user, UserPattern userPattern) throws AdminAccessRequiredException;
     List<UserPattern> readUserPatternList(User user) throws AdminAccessRequiredException;
}
