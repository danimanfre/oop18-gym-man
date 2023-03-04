package gymman.ui.calendar;

import javafx.scene.paint.Color;

/**
 * Utility class that generates colors based on some hash
 */
public final class ColorGenerator {
    private ColorGenerator() {}

    /**
     * Get a color by hash
     * @param hash
     * @return
     */
    public static Color colorFrom(final int hash) {
        return Color.hsb(hash % 360, 0.40, 0.80);
    }
}