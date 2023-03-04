package gymman.ui.customers;

import gymman.ui.AbstractPage;

/**
 * The Class AddCustomerPage.
 */
public class AddCustomerPage extends AbstractPage {

    /**
     * Instantiates a new page to add customers.
     */
    public AddCustomerPage() {
        super(AddCustomerPage.class.getResource("creazioneCliente.fxml"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "page_customer_creation";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return "Crea cliente";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMenuEntry() {
        return false;
    }
}
