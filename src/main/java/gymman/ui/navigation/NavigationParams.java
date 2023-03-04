package gymman.ui.navigation;

import java.util.HashMap;
import java.util.Map;

/**
 * Key-value container for parameters to be passed around from one {@code Page} to the other
 */
public class NavigationParams {
    private Map<String, Object> items = new HashMap<>();

    public NavigationParams() {}

    /**
     * Set a key-value pair
     * @param key
     * @param value
     */
    public void set(final String key, final Object value) {
        this.items.put(key, value);
    }

    /**
     * Get value for a given key. This also removes it from the container
     * @param key
     * @return the value
     */
    public Object get(final String key) {
        return this.items.remove(key);
    }

    /**
     * Get value for a given key WITHOUT removing it from the container
     * @param key
     * @return the value
     */
    public Object getWithoutRemoving(final String key) {
        return this.items.get(key);
    }

    /**
     * Remove a key-value pair by key
     * @param key
     */
    public void remove(final String key) {
        this.items.remove(key);
    }
}
