package us.lacchain.crossborder.management.exception;


public class DLTAddressExistsException extends Exception {

	public DLTAddressExistsException() {
		super();
	}

	public DLTAddressExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public DLTAddressExistsException(String message) {
		super(message);
	}

	public DLTAddressExistsException(Throwable cause) {
		super(cause);
	}
}