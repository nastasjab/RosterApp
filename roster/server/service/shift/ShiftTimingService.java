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
    public void addShiftTiming(User currentUser, ShiftTiming shiftTiming) throws ShiftTimingExistException, AdminAccessRequiredException {
        checkAdminAuthUser(currentUser);
        if (getShiftTiming(currentUser, shiftTiming.getTitle()) != null)
            throw new ShiftTimingExistException();

        shiftTiming.setId(getNextId(shiftTimings));
        shiftTimings.add(shiftTiming);
    }

    private ShiftTiming getShiftTiming(User currentUser, final String title) throws AdminAccessRequiredException {
        checkAdminAuthUser(currentUser);
        return shiftTimings == null ? null :
                shiftTimings.stream().
                        filter(p -> p.getTitle().equals(title)).
                        findFirst().
                        orElse(null);
    }

    @Override
    public ShiftTiming getShiftTiming(User currentUser, final long id) throws AdminAccessRequiredException {
        checkAdminAuthUser(currentUser);
        return shiftTimings == null ? null :
                shiftTimings.stream().
                        filter(p -> p.getId() == id).
                        findFirst().
                        orElse(null);
    }


    @Override
    public void deleteShiftTiming(User currentUser, long id) throws ShiftTimingNotExistException, AdminAccessRequiredException {
        checkAdminAuthUser(currentUser);
        if (getShiftTiming(currentUser, id)==null)
            throw  new ShiftTimingNotExistException();

        shiftTimings.remove(getShiftTiming(currentUser, id));
    }

    @Override
    public List<ShiftTiming> readShiftTimingList(User currentUser) throws AdminAccessRequiredException {
        checkAdminAuthUser(currentUser);
        return shiftTimings;
    }

    @Override
    protected String[] getRecord(GenericObject object) {
        return new String[0];
    }


}
