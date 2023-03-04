package gymman.employees;

import java.util.List;
import java.util.Optional;

import gymman.auth.UserRepository;
import gymman.common.Repository;

/**
 * Repository for the Employee entity
 */
public interface EmployeeRepository extends Repository<Employee>, UserRepository {
    List<Employee> searchByName(String name);
    Optional<Employee> getEmployeeByUsername(String username);
}
