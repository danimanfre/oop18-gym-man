package gymman.customers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import gymman.common.BaseEntity;
import lombok.Getter;

/**
 * The Class RegistrationImpl implements the concept of registration.
 */
public abstract class Registration extends BaseEntity {

    /** The client id to associate registration. */
    @Getter protected String idClient;

    /** The subscription type of registration. */
    @Getter protected SubscriptionType type;

    /** The discount of registration. */
    @Getter protected double discount;

    /** The additional services list for registration. */
    @Getter protected List<AdditionalService> additionalService = new ArrayList<>();

    /**
     * Gets the price.
     *
     * @return the total price of registration including additional services
     */
    public abstract double getPrice();

    /**
     * Checks if duration is valid.
     *
     * @param duration the duration
     * @return true, if duration is a positive number
     */
    public static boolean isDurationValid(final int duration) {
        return duration > 0;
    }

    /**
     * Checks if discount is valid.
     *
     * @param discount the discount
     * @return true, if discount is valid
     */
    public static boolean isDiscountValid(final double discount) {
        return discount <= 100 || discount >= 0;
    }

    /**
     * Checks if is signing date valid.
     *
     * @param date the date
     * @return true, if signing date follows the current date
     */
    public static boolean isSigningDateValid(final LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    /**
     * Checks if is active.
     *
     * @param date the date
     * @return true, if the registration is still active
     */
    public abstract boolean isActive(final LocalDate date);
}
