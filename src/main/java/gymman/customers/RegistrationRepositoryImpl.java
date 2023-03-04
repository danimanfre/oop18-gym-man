package gymman.customers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import gymman.common.DuplicateEntityException;
import gymman.common.MemoryRepository;

/**
 * The Class RegistrationRepositoryImpl represent the repository for registrations.
 */
public final class RegistrationRepositoryImpl extends MemoryRepository<Registration> implements RegistrationRepository {

    /**
     * Instantiates a new registration repository.
     */
    public RegistrationRepositoryImpl() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(final Registration registration) throws DuplicateEntityException {
        /* check if there is a numbered registration of the same type for this customer */
        final List<NumberedRegistration> numberedExisting = this.items.stream()
                .filter(e -> e instanceof NumberedRegistration && e.getIdClient().equals(registration.getIdClient()))
                .map(e -> (NumberedRegistration) e)
                .filter(e -> e.getType().equals(registration.getType()) && !e.getId().equals(registration.getId()))
                .collect(Collectors.toList());

        /* check if there is a term registration for this customer*/
        final List<Registration> termExisting = this.items.stream()
                .filter(e -> e instanceof TermRegistration && e.getIdClient().equals(registration.getIdClient())
                && !e.getId().equals(registration.getId()))
                .map(e -> (TermRegistration) e)
                .collect(Collectors.toList());

        /* check if the registrations of the same type
         * will still be active for the signing date of the new registration */
        List<Boolean> activeRegistration = Stream.of(false).collect(Collectors.toList());
        if (registration instanceof TermRegistration) {
            final TermRegistration termRegistration = TermRegistration.class.cast(registration);
            activeRegistration = termExisting.stream()
                    .filter(e -> e.getType().equals(registration.getType()))
                    .map(e -> e.isActive(termRegistration.getSigningDate()))
                    .collect(Collectors.toList());
        }
        if (!(registration instanceof NumberedRegistration) && activeRegistration.contains(true)
                || (registration instanceof NumberedRegistration) && !numberedExisting.isEmpty()) {
            throw new DuplicateEntityException(registration.toString(), termExisting.toString());
        }
        super.add(registration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Registration> getList() {
        return this.items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Registration> getByIdClient(final String idClient) {
        return this.items.stream()
                .filter(e -> e.getIdClient().equals(idClient)).collect(Collectors.toList());
    }

}
