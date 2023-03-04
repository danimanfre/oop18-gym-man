package gymman.ui.employees;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import gymman.employees.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Custom JavaFX component that displays Employee info
 */
public class EmployeeCard extends VBox {

    @FXML private Label txtName;
    @FXML private Label txtBirthdate;
    @FXML private Label txtFiscalCode;
    @FXML private Label txtPhone;
    @FXML private Label txtAddress;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;

    private final Employee employee;
    private Consumer<Employee> onEditCallback;
    private Consumer<Employee> onDeleteCallback;

    /**
     *
     * @param employee
     */
    public EmployeeCard(final Employee employee) {
        super();

        final FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeCard.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.employee = employee;
        this.txtName.setText(employee.getFirstname() + " " + employee.getLastname());

        if (employee.getBirthdate() != null) {
            this.txtBirthdate.setText(employee.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

        this.txtFiscalCode.setText(employee.getFiscalCode());
        this.txtPhone.setText(employee.getPhone());
        this.txtAddress.setText(employee.getAddress());
    }

    /**
     * Set callback to be invoked when the edit button is pressed
     * @param callback
     */
    public void onEdit(final Consumer<Employee> callback) {
        this.onEditCallback = callback;
    }

    /**
     * Set callback to be invoked when the delete button is pressed
     * @param callback
     */
    public void onDelete(final Consumer<Employee> callback) {
        this.onDeleteCallback = callback;
    }

    /**
     * Enable or disable the edit button
     * @param enabled
     */
    public void setEditEnabled(final boolean enabled) {
        this.btnEdit.setDisable(!enabled);
    }

    /**
     * Enable or disable the delete button
     * @param enabled
     */
    public void setDeleteEnabled(final boolean enabled) {
        this.btnDelete.setDisable(!enabled);
    }

    @FXML
    public void btnEditClicked() {
        this.onEditCallback.accept(this.employee);
    }

    @FXML
    public void btnDeleteClicked() {
        this.onDeleteCallback.accept(this.employee);
    }
}
