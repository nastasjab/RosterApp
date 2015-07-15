package server.service.shift;

import server.exception.*;
import server.service.user.User;
import service.exception.AdminAccessRequiredException;

import java.util.List;

public interface IShiftTimingService {
    void initialize();

    void addShiftTiming(User currentUser, ShiftTiming shiftTiming) throws ShiftTimingExistException, AdminAccessRequiredException;
    void deleteShiftTiming(User currentUser, long id) throws ShiftTimingNotExistException, AdminAccessRequiredException;
    List<ShiftTiming> readShiftTimingList(User currentUser) throws AdminAccessRequiredException;

    ShiftTiming getShiftTiming(User currentUser, long shiftTimingId) throws AdminAccessRequiredException;
}
