package front;

import front.classifier.OperationType;
import service.classifier.UserType;
import service.domain.*;
import service.exception.*;
import service.service.user.*;
import front.exception.*;

import java.io.IOException;
import java.util.List;

public class ScheduleApp implements IScheduleApp {

	private final IUserService userService;
	private User currentUser;
	private final ConsoleOperations console;


	public ScheduleApp() {
		console = new ConsoleOperations();
		userService = new UserServiceImpl();
		console.showWelcome();
	}

	@Override
	public void listTemplate() {

	}

	@Override
	public void addTemplate() {

	}

	@Override
	public void deleteTemplate() {

	}

	@Override
	public void listSchedules() {

	}

	@Override
	public void addSchedule() {

	}

	@Override
	public void showSchedule() {

	}

	@Override
	public void deleteSchedule() {

	}

	@Override
	public void modifySchedule() {

	}

	@Override
	public void activateSchedule() {

	}

	@Override
	public void deActivateSchedule() {

	}

	@Override
	public void selectDay() {

	}

	@Override
	public void addTurnEmployee() {

	}

	@Override
	public void removeTurnEmployee() {

	}

	@Override
	public void showMySchedule() {

	}

	@Override
	public void login() throws InvalidPasswordException, IOException, UserNotExistException, InvalidUserTypeException {
		User user = userService.authenticateUser(console.makeUserLoginLogin(), console.makeUserLoginPassword());
		if (user !=null ) {
			currentUser = user;
			console.showSuccess();
		}
	}

	@Override
	public void logoff() {
		currentUser=null;
		console.showSuccess();
	}

	@Override
	public void showStatus() {
		console.showStatus(currentUser);
	}

	@Override
	public void createUser() throws PasswordsNotEqualException, InvalidUserTypeException, IOException, UserExistException, InvalidEmailAddressException {
		User user = console.fillUserData();
		userService.createUser(user);
		console.showSuccess();
	}

	@Override
	public void listUser() throws IOException {
		List<User> users = userService.readUserList();
		console.showUsers(users);
	}

	@Override
	public void deleteUser() throws IOException, UserNotExistException {
		User user = console.getUserToDelete();
		userService.deleteUser(user);
		console.showSuccess();
	}

	@Override
	public OperationType offerOperations()
			throws IllegalOperationException, OperationAdminException {
		OperationType operationType =  console.showUserMenu(currentUser);



		if ((currentUser==null || currentUser.getType().equals(UserType.USER) || !currentUser.isAuthenticated())
				&& operationType.isAdmin())
			throw new OperationAdminException();

		return operationType;
	}
}