package gymman.ui.employees;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import gymman.employees.CalendarDay;
import gymman.employees.CalendarService;
import gymman.employees.Employee;
import gymman.employees.EmployeeRepository;
import gymman.employees.WorkShift;
import gymman.ui.Controller;
import gymman.ui.calendar.CalendarEntry;
import gymman.ui.calendar.CalendarFactory;
import gymman.ui.calendar.WeekView;
import gymman.ui.navigation.NavigationService;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.layout.BorderPane;

/**
 * Controller for the calendar page
 */
public class ShiftCalendarController implements Controller {

    private final CalendarService calendarService;
    private final NavigationService navService;
    private final EmployeeRepository employeeRepo;

    @FXML private BorderPane content;

    public ShiftCalendarController(
        final CalendarService calendarService,
        final NavigationService navService,
        final EmployeeRepository employeeRepo
    ) {
        this.calendarService = calendarService;
        this.navService = navService;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public void onDisplay() {
        final LocalDate startDate = LocalDate.now();
        startDate.minusDays(startDate.getDayOfWeek().getValue() - 1);
        final List<CalendarEntry> entries = new ArrayList<>();

        for (final CalendarDay day : calendarService.getWeekCalendar(startDate)) {
            for (final WorkShift shift : day.getShifts()) {
                entries.addAll(workShiftToCalendarEntry(shift, day.getDate()));
            }
        }

        final WeekView calendar = CalendarFactory.week(entries);
        content.setCenter(calendar);
    }

    public void btnNewShiftClicked() {
        this.navService.getNavParams().remove("workshift");
        this.navService.navigate("page_workshift_edit");
    }

    private List<CalendarEntry> workShiftToCalendarEntry(final WorkShift shift, final LocalDate startDate) {
        final Employee employee = employeeRepo.get(shift.getEmployeeId()).get();

        return IntStream.range(shift.getTimeStart().getHour(), shift.getTimeEnd().getHour() + 1)
                .mapToObj(hour -> {
                    return new CalendarEntry(startDate,
                            LocalTime.of(hour, 0),
                            employee.getName());
                })
                .peek(e -> e.setCursor(Cursor.HAND))
                .peek(e -> e.setOnMouseClicked(o -> onCalendarEntryClick(shift)))
                .collect(Collectors.toList());
    }

    private void onCalendarEntryClick(final WorkShift shift) {
        this.navService.getNavParams().set("workshift", shift);
        this.navService.navigate("page_workshift_edit");
    }
}
