package gymman.ui.calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 * CalendarView implementation that displays a week worth of days
 */
public final class WeekView extends ScrollPane implements CalendarView {

    private List<CalendarEntry> entries = new ArrayList<>();
    private final static int NUM_COLS = 8;
    private final static int NUM_ROWS = 25;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E d/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

    public WeekView() {
        super();
        getStylesheets().add(WeekView.class.getResource("WeekView.css").toExternalForm());
        getStyleClass().add(".calendar-week-view");
        setFitToHeight(true);
        setFitToWidth(true);
    }

    @Override
    public void setEntries(List<CalendarEntry> entries) {
        this.entries = entries;
        this.refresh();
    }

    @Override
    public void refresh() {
        getChildren().clear();
        setContent(buildGrid());
    }

    private GridPane buildGrid() {
        final GridPane grid = new GridPane();
        final Map<Coords, HBox> cells = new HashMap<>();

        grid.setPrefSize(GridPane.REMAINING, GridPane.REMAINING);

        for (int i = 0; i <= NUM_COLS; i++) {
            final ColumnConstraints constraint = new ColumnConstraints();
            constraint.setPercentWidth(100 / NUM_COLS);
            constraint.setMinWidth(100);
            grid.getColumnConstraints().add(constraint);
        }
        for (int i = 0; i <= NUM_ROWS; i++) {
            final RowConstraints constraint = new RowConstraints();
            constraint.setMinHeight(50);
            constraint.setPrefHeight(100);
            grid.getRowConstraints().add(constraint);
        }

        final LocalDate startDate = getStartDate();
        final LocalTime startTime = LocalTime.of(0, 0);

        for (int i = 1; i < NUM_COLS; i++) {
            grid.add(buildDateCell(startDate.plusDays(i - 1), dateFormatter, Pos.BOTTOM_CENTER), i, 0);
        }

        for (int i = 1; i < NUM_ROWS; i++) {
            grid.add(buildDateCell(startTime.plusHours(i - 1), timeFormatter, Pos.CENTER_RIGHT), 0, i);
        }

        for (int x = 1; x < NUM_COLS; x++) {
            for (int y = 1; y < NUM_ROWS; y++) {
                final HBox cell = emptyCell();
                cells.put(new Coords(x, y), cell);
                grid.add(cell, x, y);
            }
        }

        for (final CalendarEntry entry : this.entries) {
            entry.setPadding(new Insets(4));
            entry.setMinWidth(40);
            cells.get(new Coords(entry.getDate().getDayOfWeek().getValue(), entry.getTime().getHour()))
                    .getChildren()
                    .add(entry);
        }

        return grid;
    }

    private LocalDate getStartDate() {
        final Optional<LocalDate> fromEntries = this.entries.stream()
                .map(e -> e.getDate())
                .min((a,b) -> a.compareTo(b));

        if (!fromEntries.isPresent()) {
            // return monday of the current week
            final LocalDate now = LocalDate.now();
            return now.minusDays(now.getDayOfWeek().getValue() - 1);
        }

        return fromEntries.get();
    }

    private VBox buildDateCell(final TemporalAccessor date, final DateTimeFormatter formatter, final Pos labelPosition) {
        final VBox box = new VBox();
        box.getStyleClass().add("calendar-cell");
        box.setAlignment(labelPosition);
        box.getChildren().add(new Label(formatter.format(date)));
        return box;
    }

    private HBox emptyCell() {
        final HBox box = new HBox();
        box.getStyleClass().add("calendar-cell");
        return box;
    }

    private class Coords {
        public Integer x;
        public Integer y;

        public Coords(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return 17 + (31 * x) + (31 * y);
        }

        @Override
        public boolean equals(Object other) {
            if (!Coords.class.isInstance(other)) {
                return false;
            }
            final Coords otherCoords = Coords.class.cast(other);
            return x == otherCoords.x && y == otherCoords.y;
        }
    }
}
