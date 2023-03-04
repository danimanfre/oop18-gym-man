package gymman.customers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


import lombok.Builder;
import lombok.Getter;

/**
 * The Class TermRegistration implements the concept of term registration.
 */
public final class TermRegistration extends Registration {

    @Getter private LocalDate signingDate;

    @Getter private int duration;

    /**
     * Instantiates a new registration.
     */
    private TermRegistration() {
        super();
    }

    /**
     * Build the term registration using Lombok.
     *
     * @param id the id of the registration
     * @param idClient the idClient of the registration
     * @param type the subscription type of the registration
     * @param signingDate the date when the registration starts
     * @param duration the duration of the registration
     * @param discount the discount of the registration
     * @param additionalService the additionalService of the registration
     *
     * @return the new term registration
     */
    @Builder
    private static TermRegistration of(
        final String id,
        final String idClient,
        final SubscriptionType type,
        final LocalDate signingDate,
        final int duration,
        final double discount,
        final List<AdditionalService> additionalService
    ) {
        final TermRegistration termRegistration  = new TermRegistration();

        if (id != null) {
            termRegistration.id = id;
        }
        if (!isSigningDateValid(signingDate)) {
            throw new InvalidValueException("giorno di inizio");
        }
        if (!isDurationValid(duration)) {
            throw new InvalidValueException("durata");
        }
        if (!isDiscountValid(discount)) {
            throw new InvalidValueException("sconto");
        }
        termRegistration.idClient = idClient;
        termRegistration.type = type;
        termRegistration.signingDate = signingDate;
        termRegistration.duration = duration;
        termRegistration.discount = discount;
        if (additionalService != null) {
            termRegistration.additionalService = additionalService;
        }

        return termRegistration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive(final LocalDate date) {
        final Calendar data = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
        final Calendar today = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
        data.setTime(Date.from(this.getSigningDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        today.setTime(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        data.add(Calendar.MONTH, duration);
        return data.after(today);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPrice() {
        return this.duration
                * (this.additionalService.stream().mapToDouble(e -> e.getPrice()).sum() + this.type.getUnitPrice()
                        * (1 - this.getDiscount() / 100));
    }

}
