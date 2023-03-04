package gymman.employees;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import gymman.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

/**
 * Represents an employee's work shift
 */
public final class WorkShift extends BaseEntity {
    @Getter private String employeeId;
    @Getter private Set<DayOfWeek> weekDays;
    @Getter private Optional<LocalDate> date;
    @Getter private LocalTime timeStart;
    @Getter private LocalTime timeEnd;
    @Getter private String comment;

    private WorkShift() {
        super();
    }

    /**
     * Work shift is recurring
     * @return true if recurring, false otherwise
     */
    public boolean isRecurring() {
        return this.weekDays.size() > 0;
    }

    @Builder
    private static WorkShift of(
        final String id,
        @NonNull final String employeeId,
        @Singular final Set<DayOfWeek> weekDays,
        final LocalDate date,
        @NonNull final LocalTime timeStart,
        @NonNull final LocalTime timeEnd,
        final String comment
    ) {
        WorkShift shift = new WorkShift();

        if (id != null) {
            shift.id = id;
        }

        shift.employeeId = employeeId;
        shift.weekDays = new HashSet<>();
        shift.date = Optional.empty();
        shift.comment = "";

        if (!weekDays.isEmpty() && date != null) {
            throw new InvalidTimeException("Cannot set both weekDay and date");
        }

        if (weekDays.isEmpty() && date == null) {
            throw new InvalidTimeException("One of weekDay or date must be set");
        }

        if (!weekDays.isEmpty()) {
            shift.weekDays = weekDays;
        }

        if (date != null) {
            shift.date = Optional.of(date);
        }

        if (timeEnd.isBefore(timeStart)) {
            throw new InvalidTimeException("End time cannot be before Start time");
        }

        shift.timeStart = timeStart;
        shift.timeEnd = timeEnd;

        if (comment != null) {
            shift.comment = comment;
        }

        return shift;
    }
}
