package gymman.ui.customers;

import gymman.ui.AbstractPage;

/**
 * The Class CustomerPage.
 */
public class CustomerPage extends AbstractPage {

    /**
     * Instantiates a new customer page.
     */
    public CustomerPage() {
        super(CustomerPage.class.getResource("elencoClienti.fxml"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "page_customers_list";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return "Clienti";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMenuEntry() {
        return true;
    }
}
