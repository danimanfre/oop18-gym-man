package gymman.ui.infoclient;

import gymman.ui.AbstractPage;

/**
 * The Class BMIPage.
 */
public class BMIPage extends AbstractPage {

    /**
     * Instantiates a new BMI page.
     */
    public BMIPage() {
        super(BMIPage.class.getResource("BMI.fxml"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "page_bmi";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return "BMI Cliente";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMenuEntry() {
        return false;
    }
}
