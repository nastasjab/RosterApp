package server.service.user;

import server.classifier.UserType;
import server.exception.*;
import server.service.GenericObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User extends GenericObject {

	private UserType type;
	private String login;
	private String password;
	private String name;
	private String surname;
	private String email;
	private String phone;

	public User() {
		super();
	}

	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	protected boolean setAuthenticated() {
		return isAuthenticated;
	}

	private boolean isAuthenticated;

	public UserType getUserType() {
		return type;
	}

	public void setUserType(UserType type) {
		this.type = type;
	}

	public String getType() {
		return type.getType();
	}

	public void setType(String type) throws InvalidUserTypeException {
		this.type = new UserType(type);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPlainPassword(String plainPassword) {
		this.password = encodePassword(plainPassword);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private String encodePassword(String plainPassword) {
		String generatedPassword;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainPassword.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte aByte : bytes) {
				sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			generatedPassword = plainPassword;
		}
		return generatedPassword;
	}

	protected void verifyPassword(String plainPassword) throws InvalidPasswordException {
		if(encodePassword(plainPassword).equals(this.password))
			isAuthenticated=true;
		else {
			isAuthenticated=false;
			throw new InvalidPasswordException();
		}
	}

}