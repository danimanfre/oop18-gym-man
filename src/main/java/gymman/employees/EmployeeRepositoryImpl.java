package gymman.employees;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import gymman.auth.User;
import gymman.common.DuplicateEntityException;
import gymman.common.MemoryRepository;
import gymman.common.SearchUtils;

/**
 * In-memory storage implementation of the EmployeeRepository
 */
public class EmployeeRepositoryImpl extends MemoryRepository<Employee> implements EmployeeRepository {

    @Override
    public void add(final Employee employee) throws DuplicateEntityException {
        final Optional<Employee> existing = this.items.stream()
            .filter(e -> (e.getFiscalCode().equals(employee.getFiscalCode())
                || e.getUsername().equals(employee.getUsername()))
                && !e.getId().equals(employee.getId())
            )
			.findFirst();

		if (existing.isPresent()) {
			throw new DuplicateEntityException(employee.toString(), existing.get().toString());
		}

		super.add(employee);
    }

    @Override
    public List<Employee> searchByName(final String name) {
        return this.items.stream()
            .filter(e -> SearchUtils.containsAllWordsCaseInsensitive(e.getFirstname() + " " + e.getLastname(), name))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Employee> getEmployeeByUsername(final String username) {
        return this.items.stream()
            .filter(e -> e.getUsername().equals(username))
            .findFirst();
    }

	@Override
	public Optional<User> getUserByUsername(final String username) {
		final Optional<Employee> employee = this.getEmployeeByUsername(username);

		if (!employee.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(employee.get());
	}

	@Override
	public boolean hasUsername(final String username) {
		return this.getUserByUsername(username).isPresent();
	}

	@Override
	public Optional<User> getUserById(final String id) {
		final Optional<Employee> employee = this.get(id);

		if (!employee.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(employee.get());
	}
}
