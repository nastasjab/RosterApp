package service.classifier;

import service.exception.InvalidUserTypeException;

public class UserType {
	public final  static String ADMIN="a";
	public final  static String USER ="u";
	private String type;

	public String getType() {
		return type;
	}

	public String getTypeDescription() {
		if (type.equals(ADMIN))
			return "Admin";
		else
			return "User";
	}

	public UserType(String type) throws InvalidUserTypeException {
		if (!type.equals(ADMIN) && !type.equals(USER))
			throw new InvalidUserTypeException();
		this.type = type;
	}

	public UserType() throws InvalidUserTypeException {
		this.type = USER;
	}
}