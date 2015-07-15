package server.service.shift;

import server.exception.ShiftPatternExistException;
import server.exception.ShiftPatternNotExistException;
import server.service.GenericObject;
import server.service.GenericService;
import server.service.user.User;
import service.exception.AdminAccessRequiredException;

import java.util.ArrayList;
import java.util.List;

public class ShiftPatternService extends GenericService implements IShiftPatternService {
    // TODO remove static here, when DB is used
    private static List<ShiftPattern> shiftPatterns;

    public ShiftPatternService() {
    }

    @Override
    public void initialize(){
        shiftPatterns = new ArrayList<>();}

    @Override
    public void addShiftPattern(User loggedUser, ShiftPattern shiftPattern) throws ShiftPatternExistException, AdminAccessRequiredException {
        checkAdminAuthUser(loggedUser);
        if (getShiftPattern(shiftPattern.getTitle()) != null)
            throw new ShiftPatternExistException();

        shiftPattern.setId(getNextId(shiftPatterns));
        shiftPatterns.add(shiftPattern);
    }

    private ShiftPattern getShiftPattern(final String title) {
        return shiftPatterns == null ? null :
                shiftPatterns.stream().
                        filter(p -> p.getTitle().equals(title)).
                        findFirst().
                        orElse(null);
    }

    @Override
    public ShiftPattern getShiftPattern(final long id) throws AdminAccessRequiredException {
        return shiftPatterns == null ? null :
                shiftPatterns.stream().
                        filter(p -> p.getId() == id).
                        findFirst().
                        orElse(null);
    }


    @Override
    public void deleteShiftPattern(User loggedUser, long id) throws ShiftPatternNotExistException, AdminAccessRequiredException {
        checkAdminAuthUser(loggedUser);
        if (getShiftPattern(id)==null)
            throw  new ShiftPatternNotExistException();

        shiftPatterns.remove(getShiftPattern(id));
    }

    @Override
    public List<ShiftPattern> readShiftPatternList() throws AdminAccessRequiredException {
        return shiftPatterns;
    }

    @Override
    protected String[] getRecord(GenericObject object) {
        return new String[0];
    }


}
