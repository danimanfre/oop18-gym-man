package gymman.ui.navigation;

import java.util.Optional;
import java.util.Set;
import gymman.ui.Page;

/**
 * The navigation service handles the navigation between all the {@code Page}s
 */
public interface NavigationService extends Navigator {
    /**
     * Register a page
     * @param page
     */
    void registerPage(Page page);

    /**
     * Get all registered pages
     * @return a Set of pages
     */
    Set<Page> getPages();

    /**
     * Find a Page by its ID
     * @param id
     * @return Optional eventually containing the Page if found
     */
    Optional<Page> findById(String id);

    /**
     * Get the currently displayed Page
     * @return currently displayed Page
     */
    Page getCurrentPage();

    /**
     * Add a callback that will be invoked any time the Page changes
     * @param handler
     */
    void addOnPageChangeHandler(PageChangeHandler handler);

    /**
     * Gets the Page history size. Mainly for testing purposes
     * @return history size
     */
    int getHistorySize();

    /**
     * Get the navigation parameters container
     * @return a NavigationParams instance
     */
    NavigationParams getNavParams();
}
