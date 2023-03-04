package gymman.ui.customers;

import gymman.ui.AbstractPage;

/**
 * The Class RegistrationEntryPage.
 */
public class RegistrationEntryPage extends AbstractPage {

    /**
     * Instantiates a new page for the registration entry.
     */
    public RegistrationEntryPage() {
        super(RegistrationEntryPage.class.getResource("ingressoCliente.fxml"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "page_registration_entry";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return "Ingresso";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMenuEntry() {
        return true;
    }

}
