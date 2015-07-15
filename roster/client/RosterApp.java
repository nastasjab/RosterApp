package client;

import client.classifier.OperationType;
import server.exception.*;
import server.service.roster.IRosterService;
import server.service.roster.Roster;
import server.service.roster.RosterRequest;
import server.service.roster.RosterService;
import server.service.shift.*;
import server.service.user.*;
import client.exception.*;
import service.exception.AdminAccessRequiredException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class RosterApp implements IRosterApp {

	private final IUserService userService;
	private final IShiftTimingService shiftTimingService;
	private final IShiftPatternService shiftPatternService;
	private final IUserPatternService userPatternService;
	private final IRosterService rosterService;
	private User currentUser;
	private final ConsoleOperations console;


	public RosterApp() throws ShiftTimingExistException, AdminAccessRequiredException, ShiftPatternExistException {
		console = new ConsoleOperations();
		userService = new UserServiceImpl();
		shiftTimingService = new ShiftTimingService();
		shiftPatternService = new ShiftPatternService();
		userPatternService = new UserPatternService();
		rosterService = new RosterService();
		console.showWelcome();

		shiftTimingService.initialize();
		shiftPatternService.initialize();
		userPatternService.initialize();

		ShiftTiming shiftTiming = new ShiftTiming("shiftTiming");
		shiftTiming.addShift(new Shift("8:00","13:00",3));
		shiftTiming.addShift(new Shift("13:00", "18:00", 2));
		shiftTimingService.addShiftTiming(currentUser, shiftTiming);

		ShiftPattern shiftPattern = new ShiftPattern();
		shiftPattern.setTitle("shift pattern");
		shiftPattern.addDayDefinition("1");
		shiftPattern.addDayDefinition("2");
		shiftPattern.addDayDefinition("R");
		shiftPatternService.addShiftPattern(currentUser, shiftPattern);

		UserPattern userPattern = new UserPattern();
		userPattern.setUserId(2);
		userPattern.setShiftPatternId(1);
		userPattern.setStartDay(new Date());
		userPatternService.addUserPattern(currentUser, userPattern);
	}


	@Override
	public void listShiftTimings() throws AdminAccessRequiredException {
		List<ShiftTiming> shiftTimings =  shiftTimingService.readShiftTimingList(currentUser);
		console.showShiftTimings(shiftTimings);
	}

	@Override
	public void addShiftTiming() throws ShiftTimingExistException, InvalidNumberException, AdminAccessRequiredException {
		ShiftTiming shiftTiming = console.getShiftTimingToAdd();
		shiftTimingService.addShiftTiming(currentUser, shiftTiming);
		console.showSuccess();
	}

	@Override
	public void deleteShiftTiming() throws ShiftTimingNotExistException, InvalidNumberException, AdminAccessRequiredException {
		long id = console.getShiftTimingToDelete();
		shiftTimingService.deleteShiftTiming(currentUser, id);
		console.showSuccess();
	}

	@Override
	public void listShiftPatterns() throws AdminAccessRequiredException {
		List<ShiftPattern> shiftPatterns =  shiftPatternService.readShiftPatternList(currentUser);
		console.showShiftPatterns(shiftPatterns);
	}

	@Override
	public void addShiftPattern() throws ShiftPatternExistException, InvalidNumberException, AdminAccessRequiredException {
		ShiftPattern shiftPattern = console.getShiftPatternToAdd();
		shiftPatternService.addShiftPattern(currentUser, shiftPattern);
		console.showSuccess();
	}

	@Override
	public void deleteShiftPattern() throws ShiftPatternNotExistException, InvalidNumberException, AdminAccessRequiredException {
		long id = console.getShiftPatternToDelete();
		shiftPatternService.deleteShiftPattern(currentUser, id);
		console.showSuccess();
	}

	@Override
	public void listUserPatterns() throws AdminAccessRequiredException {
		List<UserPattern> userPatterns =  userPatternService.readUserPatternList(currentUser);
		console.showUserPatterns(userPatterns);
	}

	@Override
	public void addUserPattern() throws InvalidNumberException, AdminAccessRequiredException, InvalidDateException {
		UserPattern userPattern= console.getUserPatternToAdd();
		userPatternService.addUserPattern(currentUser, userPattern);
		console.showSuccess();
	}

	@Override
	public void generateRosterForAll() throws InvalidNumberException, IOException, AdminAccessRequiredException {
		RosterRequest request = console.getRosterRequest();
		Roster roster = rosterService.generateRoster(currentUser, request);
		console.showRoster(roster);
	}

	@Override
	public void generateRosterForInd() throws InvalidNumberException, IOException, AdminAccessRequiredException {
		RosterRequest request = console.getRosterRequest();
		request.setUserId(currentUser.getId());
		Roster roster = rosterService.generateRoster(currentUser, request);
		console.showRoster(roster);

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
	public void addUser() throws PasswordsNotEqualException, InvalidUserTypeException, IOException, UserExistException, InvalidEmailAddressException {
		User user = console.getUserToAdd();
		userService.addUser(user);
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


// TODO uncomment
		/*if ((currentUser==null || currentUser.getType().equals(UserType.USER) || !currentUser.isAuthenticated())
				&& operationType.isAdmin())
			throw new OperationAdminException();
*/
		return operationType;
	}
}