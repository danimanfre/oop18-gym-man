package gymman.ui.customers;

import gymman.ui.AbstractPage;

/**
 * The Class RegistrationPage.
 */
public class RegistrationPage extends AbstractPage {

    /**
     * Instantiates a new registration page.
     */
    public RegistrationPage() {
        super(RegistrationPage.class.getResource("elencoIscrizioni.fxml"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "page_registrations_list";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return "Iscrizioni";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMenuEntry() {
        return false;
    }
}
