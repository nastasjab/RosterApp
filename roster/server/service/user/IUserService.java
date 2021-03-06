package server.service.user;

import server.exception.*;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    void addUser(User user) throws UserExistException, IOException;
    void deleteUser(User user) throws UserNotExistException, IOException;
    List<User> readUserList() throws IOException;

    User getUser(String login) throws UserNotExistException, IOException;

    User authenticateUser(String login, String password) throws IOException, InvalidPasswordException, UserNotExistException, InvalidUserTypeException;
}
