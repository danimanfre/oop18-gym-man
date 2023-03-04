package gymman.ui.tool;

import gymman.ui.AbstractPage;


/**
 * The Class ToolPage.
 */
public class ToolPage extends AbstractPage {

    /**
     * Instantiates a new tool page.
     */
    public ToolPage() {
        super(NewToolPage.class.getResource("ricercaattrezzi.fxml"));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "page_tool_list";
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return "Attrezzi";
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMenuEntry() {
        return true;
    }
}
