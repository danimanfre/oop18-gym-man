package gymman.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * In-memory implementation of a generic {@code Repository}
 */
public class MemoryRepository<T extends Entity> implements Repository<T> {

	protected List<T> items = new ArrayList<>();

	@Override
	public void add(final T entity) throws DuplicateEntityException {
		Optional<T> existing = this.get(entity.getId());
		if (existing.isPresent()) {
			this.items.remove(existing.get());
		}

		this.items.add(entity);
	}

	@Override
	public void remove(final T entity) {
		this.items.remove(entity);
	}

	@Override
	public boolean contains(final T entity) {
		return this.items.contains(entity);
	}

	@Override
	public boolean containsId(final String id) {
		return this.get(id).isPresent();
	}

	@Override
	public Optional<T> get(final String id) {
		return this.items.stream().filter(e -> e.getId().equals(id)).findFirst();
	}

	@Override
	public List<T> getAll() {
		return new ArrayList<>(this.items);
	}

	@Override
	public int getCount() {
		return this.items.size();
	}
}
