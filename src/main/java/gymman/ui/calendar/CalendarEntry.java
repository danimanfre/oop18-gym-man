package gymman.ui.calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import lombok.Getter;

/**
 * Custom JavaFX component for displaying a calendar entry
 */
public class CalendarEntry extends VBox {
    @Getter private LocalDate date;
    @Getter private LocalTime time;

    /**
     *
     * @param date
     * @param time
     * @param label
     */
    public CalendarEntry(final LocalDate date, final LocalTime time, final String label) {
        super();

        this.date = date;
        this.time = time;
        getChildren().add(new Label(label));

        final BackgroundFill background = new BackgroundFill(ColorGenerator.colorFrom(label.hashCode()),
                CornerRadii.EMPTY, Insets.EMPTY);
        setBackground(new Background(background));
    }
}
