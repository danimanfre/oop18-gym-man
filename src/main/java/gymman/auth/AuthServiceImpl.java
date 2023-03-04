package gymman.auth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * AuthService implementation
 */
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepo;
	private final RoleRepository roleRepo;

	private Optional<User> user;
	private boolean loggedIn;
	private final Set<Permission> permissions = new HashSet<>();
	private final List<Consumer<User>> onLoginHandlers = new ArrayList<>();
	private final List<Consumer<User>> onLogoutHandlers = new ArrayList<>();

	/**
	 *
	 * @param userRepo
	 * @param roleRepo
	 */
	public AuthServiceImpl(final UserRepository userRepo, final RoleRepository roleRepo) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
	}

	@Override
    public final void login(final String username, final String password) {
		final Optional<User> user = this.userRepo.getUserByUsername(username);
		if (!user.isPresent()) {
			throw new LoginException(String.format("User '%s' not found", username));
		}

		if (!user.get().verifyPassword(password)) {
			throw new LoginException(String.format("Invalid password for user '%s'", username));
		}

		this.user = user;
		this.loggedIn = true;

		for (final Consumer<User> handler : this.onLoginHandlers) {
			handler.accept(user.get());
		}
	}

	@Override
    public final void logout() {
		if (!this.isLoggedIn()) {
			return;
		}
		final User tmp = this.user.get();
		this.user = Optional.empty();
		this.loggedIn = false;

		for (final Consumer<User> handler : this.onLogoutHandlers) {
			handler.accept(tmp);
		}
	}

	@Override
    public final boolean isLoggedIn() {
		return this.loggedIn;
	}

	@Override
    public final User getLoggedInUser() {
		if (!this.loggedIn) {
			throw new NotLoggedInException();
		}
		return this.user.get();
	}

	@Override
    public final Set<Permission> getUserPermissions() {
		if (!this.loggedIn) {
			return new HashSet<>();
		}

		final Optional<Role> role = this.roleRepo.getByName(this.user.get().getRole());

		if (!role.isPresent()) {
			return new HashSet<>();
		}

		return role.get().getPermissions();
	}

	@Override
    public final boolean userHasPermission(final Permission permission) {
		if (!this.isLoggedIn()) {
			return false;
		}

		return this.getUserPermissions().contains(permission);
	}

	@Override
    public final boolean userHasPermission(final String permissionId) {
		if (!this.isLoggedIn()) {
			return false;
		}

		return this.getUserPermissions().stream()
			.filter(e -> e.getName().equals(permissionId))
			.findFirst()
			.isPresent();
	}

	@Override
    public final void registerPermission(final Permission permission) {
		this.permissions.add(permission);
	}

	@Override
    public final List<Permission> getRegisteredPermissions() {
		return new ArrayList<>(this.permissions);
	}

	@Override
    public final void addOnLoginHandler(final Consumer<User> handler) {
		this.onLoginHandlers.add(handler);
	}

	@Override
    public final void addOnLogoutHandler(final Consumer<User> handler) {
		this.onLogoutHandlers.add(handler);
	}

    @Override
    public Optional<Permission> getPermissionByName(String name) {
        return this.permissions.stream().filter(p -> p.getName().equals(name)).findFirst();
    }

}
