package server.service.shift;

import server.exception.ShiftPatternExistException;
import server.exception.ShiftPatternNotExistException;
import server.service.user.User;
import service.exception.AdminAccessRequiredException;

import java.util.List;

public interface IShiftPatternService {
    void initialize();

    void addShiftPattern(User loggedUser, ShiftPattern shiftPattern) throws ShiftPatternExistException, AdminAccessRequiredException;
    void deleteShiftPattern(User loggedUser, long id) throws ShiftPatternNotExistException, AdminAccessRequiredException;
    List<ShiftPattern> readShiftPatternList() throws AdminAccessRequiredException;

    ShiftPattern getShiftPattern(final long id) throws AdminAccessRequiredException;
}
