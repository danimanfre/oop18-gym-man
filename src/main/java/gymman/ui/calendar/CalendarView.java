package gymman.ui.calendar;

import java.util.List;

/**
 * A calendar view with a given set of entries
 */
public interface CalendarView {
    void setEntries(List<CalendarEntry> entries);
    void refresh();
}
