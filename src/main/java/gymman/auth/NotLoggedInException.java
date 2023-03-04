package gymman.auth;

public class NotLoggedInException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 8268407054780958287L;

	public NotLoggedInException() {
		super();
	}

	public NotLoggedInException(final String message) {
		super(message);
	}
}
