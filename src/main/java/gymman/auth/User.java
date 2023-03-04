package gymman.auth;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import gymman.common.BaseEntity;
import lombok.Getter;

/**
 * A User represents any user of the system
 */
public class User extends BaseEntity {
	@Getter public String username;
	@Getter public HashCode password;
	@Getter public String role;

	/**
	 * Creates a blank user. Can be used when the child class uses the builder pattern
	 */
	public User() {
	    super();
	}

	/**
	 * Creates a new user, without providing a password.
	 * The entity id will be automatically generated
	 *
	 * @param username
	 * @param role
	 */
	public User(final String username, final String role) {
	    super();
		this.username = username;
		this.role = role;
	}

	/**
	 * Creates a new user with the provided plaintext password.
	 * The entity id will be automatically generated
	 *
	 * @param username
	 * @param role
	 * @param password
	 */
	public User(final String username, final String role, final String password) {
		this(username, role);

		if (!isValidPassword(password)) {
			throw new IllegalArgumentException("La password deve contenere almeno 8 caratteri");
		}

		this.setPassword(password);
	}

	/**
	 * Creates an existing user, with data ideally coming from a database
	 *
	 * @param username
	 * @param role
	 * @param id
	 * @param passwordHash
	 */
	public User(final String username, final String role, final String id, final HashCode passwordHash) {
		this(username, role);
		this.id = id;
		this.password = HashCode.fromBytes(passwordHash.asBytes());
	}

	/**
	 * Check if the provided plaintext password is correct
	 *
	 * @param password Plaintext password
	 * @return true if the password matches, false otherwise
	 */
	public boolean verifyPassword(final String password) {
		return getHash(password).equals(this.password);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("User<id='%s' username='%s'>", this.id, this.username);
	}

	/**
	 * Sets the user password from plaintext
	 * @param password Plaintext password
	 */
	protected void setPassword(final String password) {
		this.password = getHash(password);
	}

	/**
	 * Utility method that checks if a password is formally valid, that is if a certain
	 * length is reached.
	 *
	 * @param password Plaintext password
	 * @return true if the password is valid, false otherwise
	 */
	public static boolean isValidPassword(final String password) {
		return password != null
			&& password.trim().length() > 0 // password should not be blank
			&& password.length() >= 8;
	}

	/**
	 * Utility method that hashes an input string
	 *
	 * @param password
	 * @return Hashed version of the input
	 */
	public static HashCode getHash(final String password) {
		return Hashing.sha256().hashString(password, Charsets.UTF_8);
	}
}
