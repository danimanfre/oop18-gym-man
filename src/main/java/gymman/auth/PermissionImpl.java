package gymman.auth;

/**
 * Default implementation of Permission
 */
public class PermissionImpl implements Permission {
	private final String name;
	private final String description;

	/**
	 *
	 * @param name
	 */
	public PermissionImpl(final String name) {
        this(name, "");
    }

	/**
	 *
	 * @param name
	 * @param description
	 */
	public PermissionImpl(final String name, final String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public boolean equals(final Object other) {
		if (other == this) {
			return true;
		}
		if (!(other instanceof PermissionImpl)) {
			return false;
		}
		return this.name.equals(((PermissionImpl) other).getName());
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s: %s", this.name, this.description);
	}
}
