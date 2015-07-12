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
    public void addShiftPattern(User user, ShiftPattern shiftTiming) throws ShiftPatternExistException, AdminAccessRequiredException {
        checkAdminAuthUser(user);
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

    private ShiftPattern getShiftPattern(final long id) {
        return shiftTimings == null ? null :
                shiftTimings.stream().
                        filter(p -> p.getId() == id).
                        findFirst().
                        orElse(null);
    }


    @Override
    public void deleteShiftPattern(User user, long id) throws ShiftPatternNotExistException, AdminAccessRequiredException {
        checkAdminAuthUser(user);
        if (getShiftPattern(id)==null)
            throw  new ShiftPatternNotExistException();

        shiftTimings.remove(getShiftPattern(id));
    }

    @Override
    public List<ShiftPattern> readShiftPatternList(User user) throws AdminAccessRequiredException {
        checkAdminAuthUser(user);
        return shiftTimings;
    }

    @Override
    protected String[] getRecord(GenericObject object) {
        return new String[0];
    }


}
