
import front.*;
import front.classifier.OperationType;

import java.io.IOException;

class EntryPoint {
    public static void main(String[] args) throws IOException {
        IScheduleApp scheduleApp = new ScheduleApp();

        OperationType operationType=null;
        do {
            try{
                operationType = scheduleApp.offerOperations();
                switch (operationType.getName()) {
                    case OperationType.LOGIN:
                        scheduleApp.login();
                        break;
                    case OperationType.LOGOFF:
                        scheduleApp.logoff();
                        break;
                    case OperationType.SHOW_STATUS:
                        scheduleApp.showStatus();
                        break;

                    case OperationType.USER_CREATE:
                        scheduleApp.createUser();
                        break;
                    case OperationType.USER_LIST:
                        scheduleApp.listUser();
                        break;
                    case OperationType.USER_DELETE:
                        scheduleApp.deleteUser();
                        break;

                    case OperationType.TEMPLATE_LIST:
                        scheduleApp.listTemplate();
                        break;
                    case OperationType.TEMPLATE_ADD:
                        scheduleApp.addTemplate();
                        break;
                    case OperationType.TEMPLATE_DELETE:
                        scheduleApp.deleteTemplate();
                        break;

                    case OperationType.SCHEDULE_LIST:
                        scheduleApp.listSchedules();
                        break;
                    case OperationType.SCHEDULE_ADD:
                        scheduleApp.addSchedule();
                        break;
                    case OperationType.SCHEDULE_DELETE:
                        scheduleApp.deleteSchedule();
                        break;
                    case OperationType.SCHEDULE_SHOW:
                        scheduleApp.showSchedule();
                        break;
                    case OperationType.SCHEDULE_MODIFY:
                        scheduleApp.modifySchedule();
                        break;
                    case OperationType.SCHEDULE_ACTIVATE:
                        scheduleApp.activateSchedule();
                        break;
                    case OperationType.SCHEDULE_DEACTIVATE:
                        scheduleApp.deActivateSchedule();
                        break;

                    case OperationType.DAY_SELECT:
                        scheduleApp.selectDay();
                        break;
                    case OperationType.DAY_ADD_EMPLOYEE:
                        scheduleApp.addTurnEmployee();
                        break;
                    case OperationType.DAY_REMOVE_EMPLOYEE:
                        scheduleApp.removeTurnEmployee();
                        break;

                    case OperationType.MY_SCHEDULE_SHOW:
                        scheduleApp.showMySchedule();
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
