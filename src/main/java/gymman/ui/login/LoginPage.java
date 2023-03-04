package gymman.ui.login;

import gymman.ui.AbstractPage;

/**
 * Login Page
 */
public class LoginPage extends AbstractPage {
    public LoginPage() {
        super(LoginPage.class.getResource("Login.fxml"));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "page_login";
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return "Login";
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMenuEntry() {
        return true;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canNavigateBackTo() {
        return true;
    }
}
