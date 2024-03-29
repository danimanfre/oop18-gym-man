package gymman.ui.infoclient;

import gymman.ui.AbstractPage;


/**
 * The Class InfoClientPage.
 */
public class InfoClientPage extends AbstractPage {

    /**
     * Instantiates a new info client page.
     */
    public InfoClientPage() {
        super(InfoClientPage.class.getResource("infoclient.fxml"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "page_customer_info";
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return "Info Cliente";
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMenuEntry() {
        return false;
    }
}
