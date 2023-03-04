package gymman.employees;

import java.time.LocalDate;
import java.util.Locale;

import com.google.common.hash.HashCode;

import gymman.auth.User;
import lombok.Builder;
import lombok.Getter;

/**
 * The Employee entity
 */
public final class Employee extends User {
    @Getter private String firstname;
    @Getter private String lastname;
    @Getter private LocalDate birthdate;
    @Getter private String fiscalCode;
    @Getter private String address;
    @Getter private String phone;

    private Employee() {
        super();
    }

    public String getName() {
        return this.firstname + " " + this.lastname;
    }

    /**
     * Checks if the String provided is a formally valid username
     * @param username
     * @return
     */
    public static boolean isValidUsername(final String username) {
        return username != null
            && !username.trim().isEmpty();
    }

    /**
     * Checks if the String provided is a formally valid person name
     * @param name
     * @return
     */
    public static boolean isValidName(final String name) {
        return name != null
            && !name.trim().isEmpty()
            && name.matches("^[a-zA-Zèéòàùì' ]+$");
    }

    /**
     * Checks if the String provided is a formally valid phone number
     * @param phone
     * @return
     */
    public static boolean isValidPhone(final String phone) {
        return phone != null
            && phone.matches("^\\+{0,1}([0-9]+ *)+$");
    }

    /**
     * Checks if the String provided is a formally valid fiscal code
     * @param code
     * @return
     */
    public static boolean isValidFiscalCode(final String code) {
        return code != null
            && code.toUpperCase(Locale.ITALIAN).matches("^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$");
    }

    @Builder
    private static Employee of(
        final String id,
        final String username,
        final String password,
        final HashCode passwordHash,
        final String firstname,
        final String lastname,
        final LocalDate birthdate,
        final String fiscalCode,
        final String address,
        final String phone,
        final String role
    ) {
        final Employee employee = new Employee();

        if (id != null) {
            employee.id = id;
        }

        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("username");
        }

        if (password != null && !isValidPassword(password)) {
			throw new IllegalArgumentException("password");
		}

        if (!isValidName(firstname)) {
            throw new IllegalArgumentException("firstname");
        }

        if (!isValidName(lastname)) {
            throw new IllegalArgumentException("lastname");
        }

        if (phone != null && !isValidPhone(phone)) {
            throw new IllegalArgumentException("phone");
        }

        if (!isValidFiscalCode(fiscalCode)) {
            throw new IllegalArgumentException("fiscalCode");
        }

        if (role == null) {
            throw new IllegalArgumentException("role");
        }

        employee.username = username;
        employee.firstname = firstname;
        employee.lastname = lastname;
        employee.birthdate = birthdate;
        employee.fiscalCode = fiscalCode;
        employee.address = address;
        employee.role = role;

        if (phone != null) {
            employee.phone = phone;
        }

        if (password != null) {
            employee.setPassword(password);
        }

        if (passwordHash != null) {
            employee.password = HashCode.fromBytes(passwordHash.asBytes());
        }

        return employee;
    }
}
