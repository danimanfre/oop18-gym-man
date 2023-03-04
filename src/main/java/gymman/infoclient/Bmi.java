/*
 *
 */
package gymman.infoclient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import gymman.common.BaseEntity;
import gymman.customers.InvalidValueException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class for make a Bmi,
 * take the weight and height value and calculate the BMI .
 */
public final class Bmi extends BaseEntity {

    /**
     * Gets the customerid.
     *
     * @return the customerid
     */
    @Getter  /**
   * Sets the customerid.
   *
   * @param customerid the new customerid
   */
  @Setter private String customerid;

    /**
     * Gets the weight.
     *
     * @return the weight
     */
    @Getter /**
  * Sets the weight.
  *
  * @param weight the new weight
  */
 @Setter private Double weight;

    /**
     * Gets the height.
     *
     * @return the height
     */
    @Getter /**
  * Sets the height.
  *
  * @param height the new height
  */
 @Setter private Double height;

    /**
     * Gets the date.
     *
     * @return the date
     */
    @Getter /**
  * Sets the date.
  *
  * @param date the new date
  */
 @Setter private LocalDateTime date;

    /**
     * Instantiates a new bmi.
     */
    private Bmi() {
        super();
    }

    /**
     * Build the Bmi.
     *
     * @param id the id
     * @param customerid the customerid
     * @param weight the weight
     * @param height the height
     * @param date the date
     * @return the bmi
     * @throws InvalidValueException the invalid value exception
     */

    @Builder
    public static Bmi of(
            final String id,
            final String customerid,
            final Double weight,
            final Double height,
            final LocalDateTime date
    ) throws InvalidValueException {
        final Bmi bmi = new Bmi();
        if (id != null) {
            bmi.id = id;
        }
        if (!isWeightValueValid(weight)) {
            throw new InvalidValueException("weight");
        }
        if (!isHeightValueValid(height)) {
            throw new InvalidValueException("height");
        }
        if (!checkValueWeight(weight)) {
            throw new InvalidValueException("weight");
        }
        if (!checkValueHeight(height)) {
            throw new InvalidValueException("height");
        }
        if (checkValueDate(date)) {
            throw new InvalidValueException("date");
        }

        bmi.customerid = customerid;
        bmi.weight = weight;
        bmi.height = height;
        bmi.date = date;
        return bmi;

    }


    /**
     * Check value date is Before at Now.
     *
     * @param date the date
     * @return true, if successful
     */
    private static boolean checkValueDate(final LocalDateTime date) {
        return date.isAfter(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
    }

    /**
     * Checks if is height value valid.
     *
     * @param heights the heights
     * @return true, if is height value valid
     */
    private static boolean isHeightValueValid(final Double heights) {
        return String.valueOf(heights).matches("[0-9]+(\\.[0-9]+){0,1}");
    }

    /**
     * Checks if is weight value valid.
     *
     * @param weights the weights
     * @return true, if is weight value valid
     */
    private static boolean isWeightValueValid(final Double weights) {
        return String.valueOf(weights).matches("[0-9]+(\\.[0-9]+){0,1}");
    }


    /**
     * Check that the height is valid.
     *
     * @param height the height
     * @return true, if successful
     */
    public static boolean checkValueHeight(final double height) {
        return height >= 0.50  && height <= 2.40;
    }

    /**
     * Check that the weight is valid.
     *
     * @param weight the weight
     * @return true, if successful
     */
    public static boolean checkValueWeight(final double weight) {
        return weight >= 20  && weight <= 300;
    }

    /**
     * Method for make a Bmi, is given by BMI Weight * 1.3 / height ^ 2.5.
     *
     * @return the bmicalc
     */
    public Double getBmicalc() {
        return (getWeight()  * 1.3) / Math.pow(getHeight(), 2.5);
    }
}
