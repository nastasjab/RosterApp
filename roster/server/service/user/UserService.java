package server.service.user;

import server.exception.InvalidPasswordException;
import server.exception.InvalidUserTypeException;
import server.exception.UserExistException;
import server.exception.UserNotExistException;
import server.classifier.UserType;
import server.service.GenericService;

import java.io.IOException;
import java.util.List;

public abstract class UserService extends GenericService implements IUserService {
    public void addUser(User user) throws UserExistException, IOException {
       List<User> users = readUserList();
        if(getUser(users, user.getLogin())==null) {
            user.setId(getNextId(users));
            users.add(user);
            saveUserList(users);
        } else
            throw new UserExistException();
    }



    private User getUser(List<User> users, String login) {
        if (users==null || users.isEmpty())
            return null;

        for (User us : users){
            if (us.getLogin().equals(login))
                return us;
        }
        return null;
    }

    public User getUser(String login) throws UserNotExistException, IOException {
        List<User> users = readUserList();
        User result = getUser(users, login);
        if (result!=null) return result;

        throw new UserNotExistException();
    }

    public void deleteUser(User user) throws UserNotExistException, IOException {
        List<User> users = readUserList();
        User userFromDb= getUser(users, user.getLogin());
        if(userFromDb!=null && userFromDb.getLogin().equals(user.getLogin())) {
            users.remove(userFromDb);
            saveUserList(users);
        } else
            throw new UserNotExistException();
    }

    public User authenticateUser(String login, String password)
            throws IOException, InvalidPasswordException, UserNotExistException, InvalidUserTypeException {
        List<User> users = readUserList();
        boolean adminFound=false;
        for (User us : users){
            if (us.getType().equals(UserType.ADMIN)) {
                adminFound = true;
                break;
            }
        }

        // make fake admin user in case there isn't any other admin user
        if (!adminFound) {
            User user = new User();
            user.setLogin(login);
            user.setType(UserType.ADMIN);
            user.setPlainPassword("admin");
            user.verifyPassword("admin");
            return user;
        } else {
            User user = getUser(login);
            user.verifyPassword(password);
            return user;
        }
    }

    protected abstract void saveUserList(List<User> users) throws IOException;
    public abstract List<User> readUserList() throws IOException;
}
