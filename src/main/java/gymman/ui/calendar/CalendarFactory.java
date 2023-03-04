package gymman.ui.calendar;

import java.util.List;

/**
 * Factory for creating all kinds of calendars
 */
public final class CalendarFactory {
    private CalendarFactory() {}

    public static WeekView week(final List<CalendarEntry> entries) {
        final WeekView week = new WeekView();
        week.setEntries(entries);
        return week;
    }
}
