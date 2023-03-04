package gymman.auth;

import java.util.Optional;

/**
 * Repository for User entities.
 */
public interface UserRepository {
	/**
	 * Get a User by ID
	 *
	 * @param id
	 * @return
	 */
	Optional<User> getUserById(final String id);

	/**
	 * Get a User by its username
	 *
	 * @param username
	 * @return Optional of User if found, empty Optional otherwise
	 */
	Optional<User> getUserByUsername(final String username);

	/**
	 * Checks if the provided username is present in the repository
	 *
	 * @param username
	 * @return true if found, false otherwise
	 */
	boolean hasUsername(final String username);
}
