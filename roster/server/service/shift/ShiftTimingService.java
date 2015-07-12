package server.service.shift;

import server.service.GenericObject;
import server.exception.ShiftTimingExistException;
import server.exception.ShiftTimingNotExistException;
import server.service.GenericService;
import server.service.user.User;
import service.exception.AdminAccessRequiredException;

import java.util.ArrayList;
import java.util.List;

public class ShiftTimingService extends GenericService implements IShiftTimingService {
    private List<ShiftTiming> shiftTimings;

    public ShiftTimingService() {
        shiftTimings = new ArrayList<>();
    }

    @Override
    public void addShiftTiming(User user, ShiftTiming shiftTiming) throws ShiftTimingExistException, AdminAccessRequiredException {
        checkAdminAuthUser(user);
        if (getShiftPattern(shiftTiming.getTitle()) != null)
            throw new ShiftTimingExistException();

        shiftTiming.setId(getNextId(shiftTimings));
        shiftTimings.add(shiftTiming);
    }

    private ShiftTiming getShiftPattern(final String title) {
        return shiftTimings == null ? null :
                shiftTimings.stream().
                        filter(p -> p.getTitle().equals(title)).
                        findFirst().
                        orElse(null);
    }

    private ShiftTiming getShiftPattern(final long id) {
        return shiftTimings == null ? null :
                shiftTimings.stream().
                        filter(p -> p.getId() == id).
                        findFirst().
                        orElse(null);
    }


    @Override
    public void deleteShiftTiming(User user, long id) throws ShiftTimingNotExistException, AdminAccessRequiredException {
        checkAdminAuthUser(user);
        if (getShiftPattern(id)==null)
            throw  new ShiftTimingNotExistException();

        shiftTimings.remove(getShiftPattern(id));
    }

    @Override
    public List<ShiftTiming> readShiftTimingList(User user) throws AdminAccessRequiredException {
        checkAdminAuthUser(user);
        return shiftTimings;
    }

    @Override
    protected String[] getRecord(GenericObject object) {
        return new String[0];
    }


}
