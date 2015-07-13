package client;

import client.classifier.OperationType;
import server.exception.*;
import client.exception.*;
import service.exception.AdminAccessRequiredException;

import java.io.IOException;

public interface IRosterApp {
    void listShiftTimings() throws AdminAccessRequiredException;
    void addShiftTiming() throws InvalidNumberException, ShiftTimingExistException, AdminAccessRequiredException;
    void deleteShiftTiming() throws ShiftTimingNotExistException, InvalidNumberException, AdminAccessRequiredException;

    void listShiftPatterns() throws AdminAccessRequiredException;
    void addShiftPattern() throws InvalidNumberException, ShiftPatternExistException, AdminAccessRequiredException;
    void deleteShiftPattern() throws ShiftPatternNotExistException, InvalidNumberException, AdminAccessRequiredException;

    void listUserPatterns() throws AdminAccessRequiredException;
    void addUserPattern() throws InvalidNumberException, AdminAccessRequiredException, InvalidDateException;

    void generateRosterForAll() throws InvalidNumberException, IOException, AdminAccessRequiredException;
    void generateRosterForInd() throws InvalidNumberException, IOException, AdminAccessRequiredException;

    void login() throws InvalidPasswordException, IOException, UserNotExistException, InvalidUserTypeException;
    void logoff();
    void showStatus();

    void addUser() throws PasswordsNotEqualException, InvalidUserTypeException, IOException, UserExistException, InvalidEmailAddressException;
    void listUser() throws IOException;
    void deleteUser() throws IOException, UserNotExistException;

    OperationType offerOperations() throws IllegalOperationException, OperationAdminException;
}
