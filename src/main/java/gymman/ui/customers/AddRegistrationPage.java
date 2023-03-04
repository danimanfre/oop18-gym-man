package gymman.ui.customers;

import gymman.ui.AbstractPage;

/**
 * The Class AddRegistrationPage.
 */
public class AddRegistrationPage extends AbstractPage {

    /**
     * Instantiates a new page to add registrations.
     */
    public AddRegistrationPage() {
        super(AddRegistrationPage.class.getResource("creazioneIscrizione.fxml"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "page_registration_creation";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return "Crea iscrizione";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMenuEntry() {
        return false;
    }
}
