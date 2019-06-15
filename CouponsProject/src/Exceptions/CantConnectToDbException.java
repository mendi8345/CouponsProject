package Exceptions;

public class CantConnectToDbException extends Exception {
	private static String msg;

	public CantConnectToDbException() {
		super(msg);
	}

	@Override

	public String getMessage() {
		return "Cant connect to the DB";
	}
}
