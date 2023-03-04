package gymman.ui.customers;

import gymman.ui.AbstractPage;

/**
 * The Class AddServicePage.
 */
public class AddServicePage extends AbstractPage {

    /**
     * Instantiates a new page to add services.
     */
    public AddServicePage() {
        super(AddServicePage.class.getResource("creazioneServizioAggiuntivo.fxml"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "page_additionalService_creation";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return "Crea un servizio aggiuntivo";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMenuEntry() {
        return false;
    }
}
