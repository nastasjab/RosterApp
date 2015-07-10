package front;

import front.classifier.OperationType;
import service.domain.*;
import service.exception.*;
import service.classifier.UserType;
import front.exception.*;

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
        System.out.println("You are welcome to the ScheduleApp application!");
    }

    public void showSuccess() {
        System.out.println("Success!");
    }

    public OperationType showUserMenu(User user) throws IllegalOperationException {
        System.out.println("=======================================================================");
        System.out.println("Please select operation:");
        if (user!=null && user.getType().equals(UserType.ADMIN) && user.isAuthenticated()){
            System.out.println("cl) list current category | pa) add product");
            System.out.println("ca) add category          | pd) delete product");
            System.out.println("cd) delete category       | pe) edit product");
            System.out.println("cs) select category       | ");
            System.out.println("------------------------------------------------");
            System.out.println("uc) create user           | li) login");
            System.out.println("ul) list users            | lo) logoff");
            System.out.println("ud) delete user           | ls) user status");
            System.out.println("------------------------------------------------");
            System.out.println("ol) list orders           | os) show order");
            System.out.println("oc) change order status");
            System.out.println("------------------------------------------------");

        } else  if (user!=null && user.getType().equals(UserType.USER) && user.isAuthenticated()){
            System.out.println("ta) add product to cart      | lo) logoff ");
            System.out.println("tr) remove product from cart | ls) user status");
            System.out.println("ts) show cart                |---------------------------");
            System.out.println("tc) clear cart               | cl) list current category ");
            System.out.println("op) place order              | cs) select category ");
            System.out.println("---------------------------------------------------------");
            System.out.println("ol) list my orders           | os) show my order");
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

    public User fillUserData() throws PasswordsNotEqualException, InvalidUserTypeException, InvalidEmailAddressException {
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

        if (users==null || users.isEmpty()) {
            System.out.println(EMPTY);
            return;
        }

        System.out.printf("%-3s %-20s %-10s %-20s %-20s %-15s %s\n",
                "ID", "User login", "Type", "First name", "Last Name", "Phone", "Email" );
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

}