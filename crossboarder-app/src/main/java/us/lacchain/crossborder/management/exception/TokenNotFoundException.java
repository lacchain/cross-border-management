package us.lacchain.crossborder.management.exception;


public class TokenNotFoundException extends Exception {

	public TokenNotFoundException() {
		super();
	}

	public TokenNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenNotFoundException(String message) {
		super(message);
	}

	public TokenNotFoundException(Throwable cause) {
		super(cause);
	}
}