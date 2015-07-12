package server.service.user;

import server.service.GenericObject;
import server.service.GenericService;
import service.exception.AdminAccessRequiredException;

import java.util.ArrayList;
import java.util.List;

public class UserPatternService extends GenericService implements  IUserPatternService {
    List<UserPattern> userPatterns;

    public UserPatternService() {
        this.userPatterns = new ArrayList<>();
    }

    @Override
    public void addUserPattern(User user, UserPattern userPattern) throws AdminAccessRequiredException {
        checkAdminAuthUser(user);
        // TODO check period overlapping

        userPattern.setId(getNextId(userPatterns));
        userPatterns.add(userPattern);
    }

    @Override
    public List<UserPattern> readUserPatternList(User user) throws AdminAccessRequiredException {
        checkAdminAuthUser(user);
        return userPatterns;
    }

    @Override
    protected String[] getRecord(GenericObject object) {
        return new String[0];
    }
}
