package client.classifier;


import client.exception.IllegalOperationException;

import java.util.HashMap;

public class OperationType {

    public static final String LOGIN="LOGIN";
    public static final String LOGOFF="LOGOFF";
    public static final String SHOW_STATUS="SHOW_STATUS";

    public static final String USER_CREATE="USER_CREATE";
    public static final String USER_DELETE="USER_DELETE";
    public static final String USER_LIST="USER_LIST";

    public static final String SHIFT_TIMING_LIST="SHIFT_TIMING_LIST";
    public static final String SHIFT_TIMING_ADD="SHIFT_TIMING_ADD";
    public static final String SHIFT_TIMING_DELETE="SHIFT_TIMING_DELETE";

    public static final String SHIFT_PATTERN_LIST="SHIFT_PATTERN_LIST";
    public static final String SHIFT_PATTERN_ADD="SHIFT_PATTERN_ADD";
    public static final String SHIFT_PATTERN_DELETE="SHIFT_PATTERN_DELETE";

    public static final String USER_PATTERN_LIST="USER_PATTERN_LIST";
    public static final String USER_PATTERN_ADD="USER_PATTERN_ADD";

    public static final String SCHEDULE_LIST="SCHEDULE_LIST";
    public static final String SCHEDULE_ADD="SCHEDULE_ADD";
    public static final String SCHEDULE_DELETE="SCHEDULE_DELETE";
    public static final String SCHEDULE_SHOW="SCHEDULE_SHOW";
    public static final String SCHEDULE_MODIFY="SCHEDULE_MODIFY";
    public static final String SCHEDULE_ACTIVATE="SCHEDULE_ACTIVATE";
    public static final String SCHEDULE_DEACTIVATE="SCHEDULE_DEACTIVATE";

    public static final String DAY_SELECT="DAY_SELECT";
    public static final String DAY_ADD_EMPLOYEE="DAY_ADD_EMPLOYEE";
    public static final String DAY_REMOVE_EMPLOYEE="DAY_REMOVE_EMPLOYEE";

    public static final String MY_SCHEDULE_SHOW="MY_SCHEDULE_SHOW";

    public static final String EXIT="EXIT";

    private final HashMap<String,String> operations;
    private String code;

    public OperationType(String code) throws IllegalOperationException {
        operations = new HashMap<>();
        operations.put("li", LOGIN);
        operations.put("lo", LOGOFF);
        operations.put("ls", SHOW_STATUS);

        operations.put("uc", USER_CREATE);
        operations.put("ul", USER_LIST);
        operations.put("ud", USER_DELETE);

        operations.put("tl", SHIFT_TIMING_LIST);
        operations.put("ta", SHIFT_TIMING_ADD);
        operations.put("td", SHIFT_TIMING_DELETE);

        operations.put("pl", SHIFT_PATTERN_LIST);
        operations.put("pa", SHIFT_PATTERN_ADD);
        operations.put("pd", SHIFT_PATTERN_DELETE);

        operations.put("rl", USER_PATTERN_LIST);
        operations.put("ra", USER_PATTERN_ADD);

        operations.put("sl", SCHEDULE_LIST);
        operations.put("sa", SCHEDULE_ADD);
        operations.put("sd", SCHEDULE_DELETE);
        operations.put("ss", SCHEDULE_SHOW);
        operations.put("sm", SCHEDULE_MODIFY);
        operations.put("tx", SCHEDULE_ACTIVATE);
        operations.put("to", SCHEDULE_DEACTIVATE);

        operations.put("ds", DAY_SELECT);
        operations.put("da", DAY_ADD_EMPLOYEE);
        operations.put("dd", DAY_REMOVE_EMPLOYEE);

        operations.put("my", MY_SCHEDULE_SHOW);

        operations.put("x", EXIT);
        setCode(code);
    }

    public String getName(){
        return operations.get(code);
    }

    private void setCode(String code) throws IllegalOperationException {
        if (!operations.containsKey(code))
            throw new IllegalOperationException();
        this.code = code;
    }

    public boolean isAdmin() {
        // TODO actualize
        return code.startsWith("u") ||
                code.startsWith("t") ||
                code.startsWith("p") ||
                code.startsWith("s") ||
                code.startsWith("oc")
                ;
    }

}
