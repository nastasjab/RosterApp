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
    private List<ShiftPattern> shiftTimings;

    public ShiftPatternService() {
        shiftTimings = new ArrayList<>();
    }

    @Override
    public void addShiftPattern(User currentUser, ShiftPattern shiftTiming) throws ShiftPatternExistException, AdminAccessRequiredException {
        checkAdminAuthUser(currentUser);
        if (getShiftPattern(shiftTiming.getTitle()) != null)
            throw new ShiftPatternExistException();

        shiftTiming.setId(getNextId(shiftTimings));
        shiftTimings.add(shiftTiming);
    }

    private ShiftPattern getShiftPattern(final String title) {
        return shiftTimings == null ? null :
                shiftTimings.stream().
                        filter(p -> p.getTitle().equals(title)).
                        findFirst().
                        orElse(null);
    }

    @Override
    public ShiftPattern getShiftPattern(User currentUser, final long id) throws AdminAccessRequiredException {
        checkAdminAuthUser(currentUser);
        return shiftTimings == null ? null :
                shiftTimings.stream().
                        filter(p -> p.getId() == id).
                        findFirst().
                        orElse(null);
    }


    @Override
    public void deleteShiftPattern(User currentUser, long id) throws ShiftPatternNotExistException, AdminAccessRequiredException {
        checkAdminAuthUser(currentUser);
        if (getShiftPattern(currentUser,id)==null)
            throw  new ShiftPatternNotExistException();

        shiftTimings.remove(getShiftPattern(currentUser, id));
    }

    @Override
    public List<ShiftPattern> readShiftPatternList(User currentUser) throws AdminAccessRequiredException {
        checkAdminAuthUser(currentUser);
        return shiftTimings;
    }

    @Override
    protected String[] getRecord(GenericObject object) {
        return new String[0];
    }


}
