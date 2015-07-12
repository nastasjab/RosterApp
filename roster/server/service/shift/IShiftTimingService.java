package server.service.shift;

import server.exception.*;
import server.service.user.User;
import service.exception.AdminAccessRequiredException;

import java.util.List;

public interface IShiftTimingService {
    void addShiftTiming(User user, ShiftTiming shiftTiming) throws ShiftTimingExistException, AdminAccessRequiredException;
    void deleteShiftTiming(User user, long id) throws ShiftTimingNotExistException, AdminAccessRequiredException;
    List<ShiftTiming> readShiftTimingList(User user) throws AdminAccessRequiredException;
}
