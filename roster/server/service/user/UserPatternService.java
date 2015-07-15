package server.service.user;

import server.service.GenericObject;
import server.service.GenericService;
import service.exception.AdminAccessRequiredException;

import java.util.ArrayList;
import java.util.List;

public class UserPatternService extends GenericService implements  IUserPatternService {
    // TODO remove static here, when DB is used
    private static List<UserPattern> userPatterns;

    public UserPatternService() {
    }

    @Override
    public void initialize(){
        userPatterns = new ArrayList<>();}

    @Override
    public void addUserPattern(User loggedUser, UserPattern userPattern) throws AdminAccessRequiredException {
        checkAdminAuthUser(loggedUser);
        // TODO check period overlapping, and if overlaps, split old periods.

        userPattern.setId(getNextId(userPatterns));
        userPatterns.add(userPattern);
    }

    @Override
    public List<UserPattern> readUserPatternList() throws AdminAccessRequiredException {
        return userPatterns;
    }

    @Override
    protected String[] getRecord(GenericObject object) {
        return new String[0];
    }
}
