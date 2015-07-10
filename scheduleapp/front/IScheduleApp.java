package front;

import front.classifier.OperationType;
import service.exception.*;
import front.exception.*;

import java.io.IOException;

public interface IScheduleApp {
    void listTemplate();
    void addTemplate();
   // void modifyTemplate();
    void deleteTemplate();

    void listSchedules();
    void addSchedule();
    void showSchedule();
    void deleteSchedule();
    void modifySchedule();
    void activateSchedule();
    void deActivateSchedule();

    void selectDay();
    void addTurnEmployee();
    void removeTurnEmployee();

    void showMySchedule();
    //void sendTurnChangeRequestToAdmin();
    //void sendTurnChangeRequestToUser();

    void login() throws InvalidPasswordException, IOException, UserNotExistException, InvalidUserTypeException;
    void logoff();
    void showStatus();

    void createUser() throws PasswordsNotEqualException, InvalidUserTypeException, IOException, UserExistException, InvalidEmailAddressException;
    void listUser() throws IOException;
    //void modifyUser();
    void deleteUser() throws IOException, UserNotExistException;

    OperationType offerOperations() throws IllegalOperationException, OperationAdminException;
}
