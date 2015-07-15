package server.service.shift;

import server.exception.*;
import server.service.user.User;
import service.exception.AdminAccessRequiredException;

import java.util.List;

public interface IShiftTimingService {
    void initialize();

    void addShiftTiming(User loggedUser, ShiftTiming shiftTiming) throws ShiftTimingExistException, AdminAccessRequiredException;
    void deleteShiftTiming(User loggedUser, long id) throws ShiftTimingNotExistException, AdminAccessRequiredException;
    List<ShiftTiming> readShiftTimingList() throws AdminAccessRequiredException;

    ShiftTiming getShiftTiming(long shiftTimingId) throws AdminAccessRequiredException;
}
