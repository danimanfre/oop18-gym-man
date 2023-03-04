package gymman.ui.customers;


import gymman.ui.AbstractPage;

/**
 * The Class AdditionalServicePage.
 */
public class AdditionalServicePage extends AbstractPage {

    /**
     * Instantiates a new additional service page.
     */
    public AdditionalServicePage() {
        super(AdditionalServicePage.class.getResource("elencoServiziAggiuntivi.fxml"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "page_additionalService_list";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return "Servizi aggiuntivi";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMenuEntry() {
        return true;
    }

}
