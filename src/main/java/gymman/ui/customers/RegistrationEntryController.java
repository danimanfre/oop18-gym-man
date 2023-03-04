package gymman.ui.customers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import gymman.customers.Customer;
import gymman.customers.CustomerRepository;
import gymman.customers.NumberedRegistration;
import gymman.customers.Registration;
import gymman.customers.RegistrationRepository;
import gymman.ui.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * The Class RegistrationEntryController.
 */
public class RegistrationEntryController implements Controller {

    /** The combo box filled with numbered registrations. */
    @FXML
    private ComboBox<NumberedRegistration> cmbRegistration;

    /** The button to confirm the entry. */
    @FXML
    private Button btnConfirm;

    /** The button to look for client's registration. */
    @FXML
    private Button btnSearch;

    /** The text field for insert user name. */
    @FXML
    private TextField txfSearch;

    /** The label for details of registration. */
    @FXML
    private Label lbDetails;

    /** The registration repository. */
    private final RegistrationRepository registrationRepo;

    /** The customer repository. */
    private final CustomerRepository customerRepo;

    /**
     * Instantiates a new registration entry controller.
     *
     * @param registrationRepo the registration repository
     * @param customerRepo the customer repository
     */
    public RegistrationEntryController(final RegistrationRepository registrationRepo, final CustomerRepository customerRepo) {
        this.registrationRepo = registrationRepo;
        this.customerRepo = customerRepo;
    }

    /**
     * Method to research the customer for user name.
     * If a customer is selected, will be shown his numbered subscription in the combo box.
     */
    @FXML
    private void onSearch() {
        this.cmbRegistration.setItems(null);
        this.lbDetails.setText("");
        final Optional<Customer> customerSelected = customerRepo.getByUserName(this.txfSearch.getText());
        if (customerSelected.isPresent()) {
            final List<Registration> registrations = registrationRepo.getByIdClient(customerSelected.get().getId());
            this.cmbRegistration.show();
            this.cmbRegistration.setItems(FXCollections.observableArrayList(registrations.
                    stream()
                    .filter(e -> e instanceof NumberedRegistration)
                    .map(e -> (NumberedRegistration) e)
                    .collect(Collectors.toList())));
        } else {
            new Alert(AlertType.ERROR, "Username non valido", ButtonType.CLOSE).show();
        }
    }

    /**
     * Method to confirm the entry for the customer's subscription selected.
     * After the confirmation, the information about the entry left are shown.
     */
    @FXML
    private void onConfirm() {
        final Alert alert = new Alert(AlertType.CONFIRMATION, "Confermare di volere scalare un ingresso?",
                ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.YES)) {
            final NumberedRegistration registration = this.cmbRegistration.getSelectionModel().getSelectedItem();
            if (registration.isActive(null)) {
                registration.addCountEntries();
                this.lbDetails.setText("Sono stati effettuati "
                                + registration.getEntriesCount() + "/"
                                + registration.getMaxEntries());
            } else {
                new Alert(AlertType.WARNING, "Gli ingressi per questo abbonamento sono terminati",
                        ButtonType.CLOSE).show();
            }
        }
    }

    /**
     * Method that enable and disable the button to confirm if a subscription is not selected
      */
    @FXML
    private void onValuesChange() {
        this.btnConfirm.setDisable(this.cmbRegistration.getValue() == null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisplay() {
        this.btnConfirm.setDisable(true);
        this.cmbRegistration.setItems(null);
        this.txfSearch.setText("");
        this.lbDetails.setText("");
    }



}
