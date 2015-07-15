
import client.*;
import client.classifier.OperationType;
import server.exception.ShiftPatternExistException;
import server.exception.ShiftTimingExistException;
import service.exception.AdminAccessRequiredException;

import java.io.IOException;

class EntryPoint {
    public static void main(String[] args) throws IOException, ShiftTimingExistException, ShiftPatternExistException, AdminAccessRequiredException {
        IRosterApp rosterApp = new RosterApp();

        OperationType operationType=null;
        do {
            try{
                operationType = rosterApp.offerOperations();
                switch (operationType.getName()) {
                    case OperationType.LOGIN:
                        rosterApp.login();
                        break;
                    case OperationType.LOGOFF:
                        rosterApp.logoff();
                        break;
                    case OperationType.SHOW_STATUS:
                        rosterApp.showStatus();
                        break;

                    case OperationType.USER_CREATE:
                        rosterApp.addUser();
                        break;
                    case OperationType.USER_LIST:
                        rosterApp.listUser();
                        break;
                    case OperationType.USER_DELETE:
                        rosterApp.deleteUser();
                        break;

                    case OperationType.SHIFT_TIMING_LIST:
                        rosterApp.listShiftTimings();
                        break;
                    case OperationType.SHIFT_TIMING_ADD:
                        rosterApp.addShiftTiming();
                        break;
                    case OperationType.SHIFT_TIMING_DELETE:
                        rosterApp.deleteShiftTiming();
                        break;

                    case OperationType.SHIFT_PATTERN_LIST:
                        rosterApp.listShiftPatterns();
                        break;
                    case OperationType.SHIFT_PATTERN_ADD:
                        rosterApp.addShiftPattern();
                        break;
                    case OperationType.SHIFT_PATTERN_DELETE:
                        rosterApp.deleteShiftPattern();
                        break;

                    case OperationType.USER_PATTERN_LIST:
                        rosterApp.listUserPatterns();
                        break;
                    case OperationType.USER_PATTERN_ADD:
                        rosterApp.addUserPattern();
                        break;

                    case OperationType.ROSTER_GENERATE_ALL:
                        rosterApp.generateRosterForAll();
                        break;
                    case OperationType.ROSTER_GENERATE_IND:
                        rosterApp.generateRosterForInd();
                        break;

                    default:
                        break;
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }

        } while (operationType==null || !operationType.getName().equals(OperationType.EXIT));
    }
}
