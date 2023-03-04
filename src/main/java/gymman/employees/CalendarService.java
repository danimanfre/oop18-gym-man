package gymman.employees;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Manager for the calendar. Handles getting the formatted calendar and filtering.
 */
public class CalendarService {
    private final WorkShiftRepository shiftRepo;

    /**
     * CalendarService constructor
     *
     * @param shiftRepo ShiftRepository dependency
     */
    public CalendarService(final WorkShiftRepository shiftRepo) {
        this.shiftRepo = shiftRepo;
    }

    /**
     * Returns a calendar made of {@code days} days from the {@code start} date
     * @param start
     * @param days
     * @return the calendar
     */
    public List<CalendarDay> getCalendar(final LocalDate start, final int days) {
        return this.getCalendarFiltered(start, days, (w) -> true);
    }

    /**
     * Returns 7 days from the {@code start} date
     * @param start
     * @return the calendar
     */
    public List<CalendarDay> getWeekCalendar(final LocalDate start) {
        return this.getCalendarFiltered(start, 7, (w) -> true);
    }

    /**
     * Returns the calendar for {@code employee} made of {@code days} days from the {@code start} date
     * @param employee
     * @param start
     * @param days
     * @return the calendar
     */
    public List<CalendarDay> getCalendarForEmployee(final Employee employee, final LocalDate start, final int days) {
        return this.getCalendarFiltered(start, days,
                w -> w.getEmployeeId().equals(employee.getId()));
    }

    /**
     * Returns a calendar made of {@code days} days from the {@code start} date
     * where each {@code WorkShift} can be filtered by {@code filter}
     * @param start
     * @param days
     * @param filter
     * @return the calendar
     */
    private List<CalendarDay> getCalendarFiltered(final LocalDate start, final int days, final Predicate<? super WorkShift> filter) {
        return IntStream.range(0, days)
            .mapToObj(e -> start.plusDays(e))
            .map(d ->
                CalendarDay.builder()
                    .date(d)
                    .shifts(
                        shiftRepo.getByDate(d).stream()
                            .filter(filter)
                            .collect(Collectors.toList())
                    )
                    .build()
            )
            .collect(Collectors.toList());
    }
}
