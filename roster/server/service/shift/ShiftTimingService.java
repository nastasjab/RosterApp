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
    // TODO remove static here, when DB is used
    private static List<ShiftTiming> shiftTimings;

    public ShiftTimingService() {
    }

    @Override
    public void initialize(){shiftTimings = new ArrayList<>();}

    @Override
    public void addShiftTiming(User loggedUser, ShiftTiming shiftTiming) throws ShiftTimingExistException, AdminAccessRequiredException {
        checkAdminAuthUser(loggedUser);
        if (getShiftTiming(shiftTiming.getTitle()) != null)
            throw new ShiftTimingExistException();

        shiftTiming.setId(getNextId(shiftTimings));
        shiftTimings.add(shiftTiming);
    }

    private ShiftTiming getShiftTiming(final String title) throws AdminAccessRequiredException {
        return shiftTimings == null ? null :
                shiftTimings.stream().
                        filter(p -> p.getTitle().equals(title)).
                        findFirst().
                        orElse(null);
    }

    @Override
    public ShiftTiming getShiftTiming(final long id) throws AdminAccessRequiredException {
        return shiftTimings == null ? null :
                shiftTimings.stream().
                        filter(p -> p.getId() == id).
                        findFirst().
                        orElse(null);
    }


    @Override
    public void deleteShiftTiming(User loggedUser, long id) throws ShiftTimingNotExistException, AdminAccessRequiredException {
        checkAdminAuthUser(loggedUser);
        if (getShiftTiming( id)==null)
            throw  new ShiftTimingNotExistException();

        shiftTimings.remove(getShiftTiming(id));
    }

    @Override
    public List<ShiftTiming> readShiftTimingList() throws AdminAccessRequiredException {
        return shiftTimings;
    }

    @Override
    protected String[] getRecord(GenericObject object) {
        return new String[0];
    }


}
