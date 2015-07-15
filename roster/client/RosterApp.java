package client;

import client.classifier.OperationType;
import server.classifier.UserType;
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
	private User loggedUser;
	private final ConsoleOperations console;


	public RosterApp() throws ShiftTimingExistException, AdminAccessRequiredException, ShiftPatternExistException, InvalidPasswordException, InvalidUserTypeException, UserNotExistException, IOException {
		console = new ConsoleOperations();
		userService = new UserServiceImpl();
		shiftTimingService = new ShiftTimingService();
		shiftPatternService = new ShiftPatternService();
		userPatternService = new UserPatternService();
		rosterService = new RosterService();
		console.showWelcome();

		//TODO remove testing data

		loggedUser = userService.authenticateUser("admin", "admin");

		shiftTimingService.initialize();
		shiftPatternService.initialize();
		userPatternService.initialize();


		ShiftTiming shiftTiming = new ShiftTiming("General timing schema");
		shiftTiming.addShift(new Shift("0:00", "8:00", 1));
		shiftTiming.addShift(new Shift("8:00", "16:00", 3));
		shiftTiming.addShift(new Shift("16:00", "24:00", 2));
		shiftTimingService.addShiftTiming(loggedUser, shiftTiming);

		ShiftPattern shiftPattern = new ShiftPattern();
		shiftPattern.setTitle("General pattern schema");
		shiftPattern.addDayDefinition("1");
		shiftPattern.addDayDefinition("2");
		shiftPattern.addDayDefinition("3");
		shiftPattern.addDayDefinition("R");
		shiftPattern.addDayDefinition("R");
		shiftPatternService.addShiftPattern(loggedUser, shiftPattern);
		shiftPattern = new ShiftPattern();
		shiftPattern.setTitle("Short pattern schema");
		shiftPattern.addDayDefinition("1");
		shiftPattern.addDayDefinition("2");
		shiftPattern.addDayDefinition("R");
		shiftPatternService.addShiftPattern(loggedUser, shiftPattern);

		UserPattern userPattern = new UserPattern();
		userPattern.setUserId(2);
		userPattern.setShiftPatternId(1);
		userPattern.setStartDay(new Date());
		userPatternService.addUserPattern(loggedUser, userPattern);
		userPattern = new UserPattern();
		userPattern.setUserId(3);
		userPattern.setShiftPatternId(2);
		Date date = new Date();
		date.setMonth(7);
		userPattern.setStartDay(date);
		userPatternService.addUserPattern(loggedUser, userPattern);

		loggedUser=null;
	}


	@Override
	public void listShiftTimings() throws AdminAccessRequiredException {
		List<ShiftTiming> shiftTimings =  shiftTimingService.readShiftTimingList();
		console.showShiftTimings(shiftTimings);
	}

	@Override
	public void addShiftTiming() throws ShiftTimingExistException, InvalidNumberException, AdminAccessRequiredException {
		ShiftTiming shiftTiming = console.getShiftTimingToAdd();
		shiftTimingService.addShiftTiming(loggedUser, shiftTiming);
		console.showSuccess();
	}

	@Override
	public void deleteShiftTiming() throws ShiftTimingNotExistException, InvalidNumberException, AdminAccessRequiredException {
		long id = console.getShiftTimingToDelete();
		shiftTimingService.deleteShiftTiming(loggedUser, id);
		console.showSuccess();
	}

	@Override
	public void listShiftPatterns() throws AdminAccessRequiredException {
		List<ShiftPattern> shiftPatterns =  shiftPatternService.readShiftPatternList();
		console.showShiftPatterns(shiftPatterns);
	}

	@Override
	public void addShiftPattern() throws ShiftPatternExistException, InvalidNumberException, AdminAccessRequiredException {
		ShiftPattern shiftPattern = console.getShiftPatternToAdd();
		shiftPatternService.addShiftPattern(loggedUser, shiftPattern);
		console.showSuccess();
	}

	@Override
	public void deleteShiftPattern() throws ShiftPatternNotExistException, InvalidNumberException, AdminAccessRequiredException {
		long id = console.getShiftPatternToDelete();
		shiftPatternService.deleteShiftPattern(loggedUser, id);
		console.showSuccess();
	}

	@Override
	public void listUserPatterns() throws AdminAccessRequiredException {
		List<UserPattern> userPatterns =  userPatternService.readUserPatternList();
		console.showUserPatterns(userPatterns);
	}

	@Override
	public void addUserPattern() throws InvalidNumberException, AdminAccessRequiredException, InvalidDateException {
		UserPattern userPattern= console.getUserPatternToAdd();
		userPatternService.addUserPattern(loggedUser, userPattern);
		console.showSuccess();
	}

	@Override
	public void generateRosterForAll() throws InvalidNumberException, IOException, AdminAccessRequiredException {
		RosterRequest request = console.getRosterRequest();
		Roster roster = rosterService.generateRoster(loggedUser, request);
		console.showRoster(roster);
	}

	@Override
	public void generateRosterForInd() throws InvalidNumberException, IOException, AdminAccessRequiredException {
		RosterRequest request = console.getRosterRequest();
		request.setUserId(loggedUser.getId());
		Roster roster = rosterService.generateRoster(loggedUser, request);
		console.showRoster(roster);
	}


	@Override
	public void login() throws InvalidPasswordException, IOException, UserNotExistException, InvalidUserTypeException {
		User user = userService.authenticateUser(console.makeUserLoginLogin(), console.makeUserLoginPassword());
		if (user !=null ) {
			loggedUser = user;
			console.showSuccess();
		}
	}

	@Override
	public void logoff() {
		loggedUser=null;
		console.showSuccess();
	}

	@Override
	public void showStatus() {
		console.showStatus(loggedUser);
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
		OperationType operationType =  console.showUserMenu(loggedUser);


		if ((loggedUser==null || loggedUser.getType().equals(UserType.USER) || !loggedUser.isAuthenticated())
				&& operationType.isAdmin())
			throw new OperationAdminException();

		return operationType;
	}
}