package client;

import client.classifier.OperationType;
import server.exception.*;
import server.classifier.UserType;
import client.exception.*;
import server.service.shift.ShiftPattern;
import server.service.shift.Shift;
import server.service.shift.ShiftTiming;
import server.service.user.User;
import server.service.user.UserPattern;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

class ConsoleOperations {
    private final Scanner scanner;
    private static final Pattern rfc2822 = Pattern.compile(
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
    );
    private final String EMPTY = "<empty>";

    public ConsoleOperations() {
        this.scanner = new Scanner(System.in);
    }
    public void showWelcome() {
        System.out.println("You are welcome to the RosterApp application!");
    }

    public void showSuccess() {
        System.out.println("Success!");
    }

    public OperationType showUserMenu(User user) throws IllegalOperationException {
        System.out.println("=======================================================================");
        System.out.println("Please select operation:");
        if (user!=null && user.getType().equals(UserType.ADMIN) && user.isAuthenticated()){
            System.out.println("uc) create user           | li) login");
            System.out.println("ul) list users            | lo) logoff");
            System.out.println("ud) delete user           | ls) user status");
            System.out.println("------------------------------------------------");
            System.out.println("tl) list shift timings    | pl) list shift patterns ");
            System.out.println("ta) add shift timing      | pa) add shift pattern");
            System.out.println("td) delete shift timing   | pd) delete shift pattern");
            System.out.println("------------------------------------------------");
            System.out.println("rl) list user patterns    | ra) add user pattern ");
            System.out.println("------------------------------------------------");

        } else  if (user!=null && user.getType().equals(UserType.USER) && user.isAuthenticated()){
            System.out.println("my) show my schedule      | lo) logoff ");
            System.out.println("---------------------------------------------------------");
        } else {
            System.out.println("li) login ");
        }

        System.out.println("x) exit");
        System.out.println("Your choice: ");

        OperationType operationType = new OperationType(getNotEmptyString());
        System.out.println("=======================================================================");

        return operationType;
    }

    private String getNotEmptyString(){
        String input="";
        while(input.equals("")) {
            input = scanner.nextLine();
        }
        return input;
    }

    private int getInt() throws InvalidNumberException {
        String input=getNotEmptyString();
        try {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
    }

    private long getLong() throws InvalidNumberException {
        String input=getNotEmptyString();
        try {
            return Long.parseLong(input);
        }
        catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
    }

    private double getDouble() throws InvalidNumberException {
        String input=getNotEmptyString();
        try {
            return Double.parseDouble(input);
        }
        catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
    }

    private Date getDate() throws InvalidDateException {
        return getDate(getNotEmptyString());
    }

    private Date getDate(String date) throws InvalidDateException {
        SimpleDateFormat df = new SimpleDateFormat("yy.MM.dd");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            throw new InvalidDateException();
        }
    }

    private boolean checkEmptyList(List<?> list){
        if (list==null || list.isEmpty()) {
            System.out.println(EMPTY);
            return true;
        }
        return false;
    }

    public User getUserToAdd() throws PasswordsNotEqualException, InvalidUserTypeException, InvalidEmailAddressException {
        User user = new User();

        System.out.println("#USER CREATE#");
        System.out.println("Enter user login:");
        user.setLogin( getNotEmptyString());

        System.out.println("Enter user password:");
        String password = getNotEmptyString();
        System.out.println("Confirm user password:");
        String passwordConfirm = getNotEmptyString();
        if (!password.equals(passwordConfirm))
            throw new PasswordsNotEqualException();
        user.setPlainPassword(password);

        System.out.println("Enter user type (a-admin, g-guest):");
        user.setType( getNotEmptyString());

        System.out.println("Enter first name:");
        user.setName(getNotEmptyString());

        System.out.println("Enter last name:");
        user.setSurname(getNotEmptyString());

        System.out.println("Enter phone:");
        user.setPhone(getNotEmptyString());

        System.out.println("Enter email:");
        user.setEmail(getNotEmptyString());

        if (!rfc2822.matcher(user.getEmail()).matches()) {
            throw new InvalidEmailAddressException();
        }

        return user;
    }

    public void showUsers(List<User> users) {
        System.out.println("#USER LIST#");

        if (checkEmptyList(users))
            return;

        System.out.printf("%-3s %-20s %-10s %-20s %-20s %-15s %s\n",
                "ID", "User login", "Type", "First name", "Last Name", "Phone", "Email");
        System.out.format("-----------------------------------------------------------------------------------------------------------------------\n");

            for (User user : users){
                System.out.printf("%-3d %-20s %-10s %-20s %-20s %-15s %s\n",
                        user.getId(),
                        user.getLogin(),
                        user.getUserType().getTypeDescription(),
                        user.getName(),
                        user.getSurname(),
                        user.getPhone(),
                        user.getEmail()
                        );
            }
    }

    public User getUserToDelete() {
        User user = new User();

        System.out.println("#USER DELETE#");

        System.out.println("Enter user login:");
        user.setLogin(getNotEmptyString());

        return user;
    }

    public String makeUserLoginLogin() {
        System.out.println("#USER LOGIN#");

        System.out.println("Enter user login:");
        return getNotEmptyString();
    }

    public String makeUserLoginPassword() {
        System.out.println("Enter user password:");
        return getNotEmptyString();
    }

    public void showStatus(User currentUser) {
        if (currentUser==null)
            System.out.println("No logged user!");
        else if (currentUser.isAuthenticated()) {
            System.out.format("User %s is logged in.\n", currentUser.getLogin());
        }

    }

    public ShiftTiming getShiftTimingToAdd() throws InvalidNumberException {
        ShiftTiming shiftTiming = new ShiftTiming();

        System.out.println("#SHIFT TIMING CREATE#");
        System.out.println("Enter shift timing schema title:");
        shiftTiming.setTitle(getNotEmptyString());

        String answer;
        do {
            Shift shift = new Shift();
            System.out.println("Enter shift start time (hh:mm):");
            // TODO check time format
            shift.setTimeFrom(getNotEmptyString());

            System.out.println("Enter shift end time (hh:mm):");
            // TODO check time format
            shift.setTimeTo(getNotEmptyString());

            System.out.println("Enter minimal employee count for shift:");
            shift.setMinEmployeeCount(getInt());

            shiftTiming.addShift(shift);

            System.out.println("Add next shift (y/n)?: ");
            answer = getNotEmptyString();
        }
        while (answer.equalsIgnoreCase("y"));

        return shiftTiming;
    }

    public long getShiftTimingToDelete() throws InvalidNumberException {
        System.out.println("#SHIFT TIMING TO DELETE#");

        System.out.println("Enter shift timing id to deleteShiftTiming:");
        return getLong();
    }

    public void showShiftTimings(List<ShiftTiming> shiftTimings) {
        System.out.println("#SHIFT TIMING LIST#");

        if (checkEmptyList(shiftTimings))
            return;

        for (ShiftTiming shiftTiming: shiftTimings) {
            System.out.println(shiftTiming.toString());
            System.out.println("-------------------------------------------");

        }
    }

    public void showShiftPatterns(List<ShiftPattern> shiftPatterns) {
        System.out.println("#SHIFT PATTERN LIST#");

        if (checkEmptyList(shiftPatterns))
            return;

        for (ShiftPattern shiftPattern: shiftPatterns) {
            System.out.println(shiftPattern.toString());
            System.out.println("-------------------------------------------");
        }
    }

    public ShiftPattern getShiftPatternToAdd() {
        ShiftPattern shiftPattern = new ShiftPattern();

        System.out.println("#SHIFT PATTERN CREATE#");
        System.out.println("Enter shift pattern schema title:");
        shiftPattern.setTitle(getNotEmptyString());

        String answer;
        int i=1;
        do {
            System.out.format("Enter shift number (1,2,3...) or day rest day(R) for the day#%d: ", i);
            // TODO check input format: 1,2,3,R
            shiftPattern.addDayDefinition(getNotEmptyString());

            System.out.println("Add another day to pattern (y/n)?: ");
            answer = getNotEmptyString();

            i++;
        }
        while (answer.equalsIgnoreCase("y"));

        return shiftPattern;
    }

    public long getShiftPatternToDelete() throws InvalidNumberException {
        System.out.println("#SHIFT PATTERN TO DELETE#");

        System.out.println("Enter shift timing id to deleteShiftTiming:");
        return getLong();
    }

    public UserPattern getUserPatternToAdd() throws InvalidNumberException, InvalidDateException {
        UserPattern userPattern = new UserPattern();

        System.out.println("#USER PATTERN CREATE#");
        System.out.println("Enter user id:");
        userPattern.setUserId(getLong());

        System.out.println("Enter pattern id:");
        userPattern.setShiftPatternId(getLong());

        System.out.println("Enter start day (YY.MM.DD):");
        userPattern.setStartDay(getDate());

        System.out.println("Enter end day (YY.MM.DD or 'u' for open-ended):");
        String date = getNotEmptyString();
        if (!date.equalsIgnoreCase("u"))
            userPattern.setEndDay(getDate(date));

        return userPattern;
    }


    public void showUserPatterns(List<UserPattern> userPatterns) {
        System.out.println("#USER PATTERN LIST#");

        if (checkEmptyList(userPatterns))
            return;

        System.out.format("%7s %7s %10s %10s\n", "User", "Pattern", "Start", "End");
        System.out.println("-------------------------------------------");
        for (UserPattern userPattern: userPatterns) {
            System.out.println(userPattern.toString());
        }
    }
}