package server.service.shift;

import server.exception.ShiftPatternExistException;
import server.exception.ShiftPatternNotExistException;
import server.service.user.User;
import service.exception.AdminAccessRequiredException;

import java.util.List;

public interface IShiftPatternService {
    void addShiftPattern(User user, ShiftPattern shiftPattern) throws ShiftPatternExistException, AdminAccessRequiredException;
    void deleteShiftPattern(User user, long id) throws ShiftPatternNotExistException, AdminAccessRequiredException;
    List<ShiftPattern> readShiftPatternList(User user) throws AdminAccessRequiredException;
}
