package server.service.shift;

import server.exception.ShiftPatternExistException;
import server.exception.ShiftPatternNotExistException;
import server.service.user.User;
import service.exception.AdminAccessRequiredException;

import java.util.List;

public interface IShiftPatternService {
    void addShiftPattern(User currentUser, ShiftPattern shiftPattern) throws ShiftPatternExistException, AdminAccessRequiredException;
    void deleteShiftPattern(User currentUser, long id) throws ShiftPatternNotExistException, AdminAccessRequiredException;
    List<ShiftPattern> readShiftPatternList(User currentUser) throws AdminAccessRequiredException;

    ShiftPattern getShiftPattern(User currentUser, final long id) throws AdminAccessRequiredException;
}
