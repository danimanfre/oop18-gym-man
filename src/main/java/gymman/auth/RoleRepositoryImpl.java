package gymman.auth;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import gymman.common.DuplicateEntityException;
import gymman.common.MemoryRepository;

public class RoleRepositoryImpl extends MemoryRepository<Role> implements RoleRepository {
	@Override
    public final void add(final Role role) throws DuplicateEntityException {
		Optional<Role> existing = this.items.stream()
			.filter(e -> e.getName().equals(role.getName()) && !e.getId().equals(role.getId()))
			.findFirst();

		if (existing.isPresent()) {
			throw new DuplicateEntityException(role.toString(), existing.get().toString());
		}

		super.add(role);
	}

	@Override
    public final Optional<Role> getByName(final String name) {
		return this.items.stream()
			.filter(e -> e.getName().equals(name))
			.findFirst();
	}

	@Override
    public final List<Role> searchByName(final String name) {
		return this.items.stream()
			.filter(e -> e.getName().contains(name))
			.collect(Collectors.toList());
    }
}