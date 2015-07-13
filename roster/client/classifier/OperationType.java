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

    public static final String ROSTER_GENERATE_ALL="ROSTER_GENERATE_ALL";
    public static final String ROSTER_GENERATE_IND="ROSTER_GENERATE_IND";

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

        operations.put("nl", USER_PATTERN_LIST);
        operations.put("na", USER_PATTERN_ADD);

        operations.put("ra", ROSTER_GENERATE_ALL);
        operations.put("ri", ROSTER_GENERATE_IND);

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
