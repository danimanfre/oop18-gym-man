package gymman.customers;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * The Class NumberedRegistration implements the concept of registration with entries.
 */
public final class NumberedRegistration extends Registration {

    @Getter private int maxEntries;

    @Getter private int entriesCount;

    /**
     * Instantiates a new registration.
     */
    private NumberedRegistration() {
        super();
    }

    /**
     * Build the numbered registration using Lombok.
     *
     * @param id the id of the registration
     * @param idClient the idClient of the registration
     * @param maxEntries the number of entry
     * @param entriesCount the count of entries
     * @param type the subscription type of the registration
     * @param discount the discount of the registration
     * @param additionalService the additionalService of the registration
     *
     * @return the new numbered registration
     */
    @Builder
    private static NumberedRegistration of(
        final String id,
        final String idClient,
        final SubscriptionType type,
        final int maxEntries,
        final int entriesCount,
        final double discount,
        final List<AdditionalService> additionalService
    ) {
        final NumberedRegistration numberedRegistration  = new NumberedRegistration();

        if (id != null) {
            numberedRegistration.id = id;
        }
        if (!isDurationValid(maxEntries)) {
            throw new InvalidValueException("durata");
        }
        if (!isDiscountValid(discount)) {
            throw new InvalidValueException("sconto");
        }
        numberedRegistration.idClient = idClient;
        numberedRegistration.type = type;
        numberedRegistration.maxEntries = maxEntries;
        numberedRegistration.entriesCount = 0;
        numberedRegistration.discount = discount;
        if (additionalService != null) {
            numberedRegistration.additionalService = additionalService;
        }

        return numberedRegistration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive(final LocalDate date) {
        return entriesCount < maxEntries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPrice() {
        return this.maxEntries
                * (this.additionalService.stream().mapToDouble(e -> e.getPrice()).sum() + this.type.getUnitPrice()
                        * (1 - this.getDiscount() / 100));
    }

    /** Method called every time customer use this subscription. */
    public void addCountEntries() {
        this.entriesCount++;
    }

    /**
     * To string.
     *
     * @return the string representation of the numbered registration.
     */
    @Override
    public String toString() {
        return this.getType().getName();
    }

}
