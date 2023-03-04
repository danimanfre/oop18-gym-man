package gymman.ui.customers;
import java.math.BigDecimal;
import java.util.Optional;
import gymman.common.DuplicateEntityException;
import gymman.customers.AdditionalService;
import gymman.customers.AdditionalServiceRepository;
import gymman.customers.Customer;
import gymman.customers.CustomerRepository;
import gymman.customers.InvalidValueException;
import gymman.customers.NumberedRegistration;
import gymman.customers.Registration;
import gymman.customers.RegistrationRepository;
import gymman.customers.SubscriptionType;
import gymman.customers.SubscriptionTypeRepository;
import gymman.customers.TermRegistration;
import gymman.ui.Controller;
import gymman.ui.navigation.NavigationService;
import gymman.ui.selectable_list.SelectableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import static gymman.ui.FormHelper.getTextTrimmed;
import static gymman.ui.FormHelper.isBlank;

/**
 * The Class CreateRegistrationController implements
 * the controller that create a new registration or modify
 * an existing registration.
 */
public final class CreateRegistrationController implements Controller {

    /** The customer repository. */
    private final CustomerRepository customerRepo;

    /** The subscription repository. */
    private final SubscriptionTypeRepository subscriptionRepo;

    /** The registration repository. */
    private final RegistrationRepository registrationRepo;

    /** The service repository. */
    private final AdditionalServiceRepository serviceRepo;

    /** The navigation service. */
    private final NavigationService navService;

    /** The selected customer. */
    private Optional<Customer> customer;

    /** The registration to modify if it is present. */
    private Optional<Registration> registration = Optional.empty();

    /** The button create. */
    @FXML
    private Button btnCreate;

    /** The label for customer. */
    @FXML
    private Label lbCustomer;

    /** The label for birth date. */
    @FXML
    private Label lbBirthDate;

    /** The text field for duration. */
    @FXML
    private TextField txfDuration;

    /** The label for price. */
    @FXML
    private TextField labelForPrice;

    /** The text field for discount. */
    @FXML
    private TextField txfDiscount;

    /** The text field for entries. */
    @FXML
    private TextField txfEntries;

    /** The date picker for start day. */
    @FXML
    private DatePicker dtpStartDay;

    /** The combo box for subscription. */
    @FXML
    private ComboBox<SubscriptionType> cmbSubscription;

    /** The select table list for additional service. */
    @FXML
    private SelectableList<AdditionalService> lstAdditionalService;

    /* The radio button for term registration. */
    @FXML
    private RadioButton termRadioButton;

    /* The radio button for numbered registration. */
    @FXML
    private RadioButton numberedRadioButton;


    /**
     * Instantiates a new registration controller.
     *
     * @param customerRepo the customer repository
     * @param subscriptionRepo the subscription repository
     * @param registrationRepo the registration repository
     * @param serviceRepo the service repository
     * @param navService the navigation service
     */
    public CreateRegistrationController(
        final CustomerRepository customerRepo,
        final SubscriptionTypeRepository subscriptionRepo,
        final RegistrationRepository registrationRepo,
        final AdditionalServiceRepository serviceRepo,
        final NavigationService navService
    ) {
        this.customerRepo = customerRepo;
        this.subscriptionRepo = subscriptionRepo;
        this.registrationRepo = registrationRepo;
        this.serviceRepo = serviceRepo;
        this.navService = navService;
    }

    /**
     * Method to restrict text field for duration
     *  and text field for entries with only integer
     *  number in input and text field for discount
     *   with only numerical values between 0 and 100 in input.
     */
    public void initialize() {
        this.txfDuration.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            final String newText = change.getControlNewText();
            if (newText.matches("[0-9]*") || newText.length() > 2) {
                return change;
            }

            return null;
        }));

        this.txfEntries.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            final String newText = change.getControlNewText();
            if (newText.matches("[0-9]*") || newText.length() > 3) {
                return change;
            }

            return null;
        }));

        this.txfDiscount.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            final String newText = change.getControlNewText();
            if (newText.matches("^\\d{0,2}+[\\.]{0,1}\\d{0,2}$")) {
                return change;
            }

            return null;
        }));
    }


    /**
     * Method to create the new registration if only each field
     * are compiled correctly.
     * This method can also modify an existing registration.
     * On the value selected in the radio button, will be created
     * a term registration or numbered registration.
     *
     */
    public void create() {
        this.deselectAllfields();
        if (isBlank(this.txfDiscount)) {
            this.txfDiscount.setText("0");
        }
        if (this.termRadioButton.isSelected()) {
            try {
                final TermRegistration.TermRegistrationBuilder termRegistrationBuilder = TermRegistration.builder()
                        .type(this.cmbSubscription.getValue())
                        .idClient(customer.get().getId())
                        .signingDate(this.dtpStartDay.getValue())
                        .discount(Double.valueOf(getTextTrimmed(this.txfDiscount)))
                        .duration(Integer.valueOf(getTextTrimmed(this.txfDuration)))
                        .additionalService(this.lstAdditionalService.getSelectedItems());
                if (this.registration.isPresent()) {
                    termRegistrationBuilder.id(this.registration.get().getId());
                }
                final Registration newRegistration = termRegistrationBuilder.build();
                final Alert confirmPrice = new Alert(AlertType.INFORMATION, "Costo abbonamento" + " "
                                        + new BigDecimal(newRegistration.getPrice()).setScale(2, BigDecimal.ROUND_UP)
                                        .doubleValue() + "€" + "\nConfermare?", ButtonType.YES, ButtonType.NO);
                confirmPrice.showAndWait();
                if (confirmPrice.getResult().equals(ButtonType.YES)) {
                    this.registrationRepo.add(newRegistration);
                    this.navService.back();
                }
                this.clearForm();
                this.deselectAllfields();
            } catch (DuplicateEntityException e) {
                new Alert(AlertType.WARNING, "Registrazione già eseguita", ButtonType.OK).showAndWait();
            } catch (InvalidValueException e) {
                new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
            }
        } else {
            try {
        		final NumberedRegistration.NumberedRegistrationBuilder numberedRegistrationBuilder = NumberedRegistration.builder()
        				.type(this.cmbSubscription.getValue())
    					.idClient(customer.get().getId())
    					.discount(Double.valueOf(getTextTrimmed(this.txfDiscount)))
    					.maxEntries(Integer.valueOf(getTextTrimmed(this.txfEntries)))
                        .additionalService(this.lstAdditionalService.getSelectedItems());
    			if (this.registration.isPresent()) {
    				numberedRegistrationBuilder.id(this.registration.get().getId());
    			}
    			final Registration newRegistration = numberedRegistrationBuilder.build();
    			final Alert confirmPrice = new Alert(AlertType.INFORMATION, "Costo abbonamento" + " "
                        + new BigDecimal(newRegistration.getPrice()).setScale(2, BigDecimal.ROUND_UP)
                        .doubleValue() + "€" + "\nConfermare?", ButtonType.YES, ButtonType.NO);
    			confirmPrice.showAndWait();
    			if (confirmPrice.getResult().equals(ButtonType.YES)) {
    				this.registrationRepo.add(newRegistration);
    				this.navService.back();
    			}
    			this.clearForm();
    			this.deselectAllfields();
        		} catch (DuplicateEntityException e) {
        			new Alert(AlertType.WARNING, "Registrazione già eseguita", ButtonType.OK).showAndWait();
        		} catch (InvalidValueException e) {
        			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        		}
        	}
    	}


    /**
     * Method to clear the form.
     */
    private void clearForm() {
        this.txfDuration.clear();
        this.txfEntries.clear();
        this.dtpStartDay.setValue(null);
        this.cmbSubscription.setValue(null);
        this.lstAdditionalService.clear();
        this.lstAdditionalService.setItems(this.serviceRepo.getAll());
   }

    /**
     * Method to clear the fields of form.
     */
    private void deselectAllfields() {
        this.txfDiscount.setStyle("");
        this.txfDuration.setStyle("");
        this.dtpStartDay.setStyle("");
        this.cmbSubscription.setStyle("");
    }

    /**
     * Checks if is filled out.
     *
     * @return true, if is filled out
     */
    private boolean isFilledOut() {
        return !isBlank(this.txfDiscount)
        		&& this.cmbSubscription.getValue() != null
        		&& (this.dtpStartDay.getValue() != null
                && !isBlank(this.txfDuration)
                || !isBlank(this.txfEntries));

    }

    /**
     * Method that enable and disable the button to create if all fields
     *  are compiled.
     */
    @FXML
    private void onTextChange() {
        this.btnCreate.setDisable(!isFilledOut());
    }

    /**
     * Method that enable and disable fields if the radio button
     * selected change.
     */
    @FXML
    private void onSelectedChange() {
    	if (this.termRadioButton.isSelected()) {
    		this.txfEntries.setDisable(true);
    		this.txfEntries.clear();
    		this.txfDuration.setDisable(false);
    		this.dtpStartDay.setDisable(false);
    	} else {
    		this.txfDuration.setDisable(true);
    		this.dtpStartDay.setDisable(true);
    		this.txfDuration.clear();
    		this.dtpStartDay.setValue(null);
    		this.txfEntries.setDisable(false);
    	}
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisplay() {
        this.registration = Optional.empty();
        this.clearForm();
        this.deselectAllfields();
        this.lstAdditionalService.setItems(this.serviceRepo.getList());
        this.cmbSubscription.
            setItems(FXCollections.observableArrayList(this.subscriptionRepo.getAll()));

        this.setCustomerInfo();

        this.setRegistrationInfoToModify();

        this.onSelectedChange();

        this.navService.getNavParams().set("customer", customer.get());
    }

    /**
     * Method to compile all fields with the customer's information.
     */
    private void setCustomerInfo() {
        final Object customerObj = this.navService.getNavParams().get("customer");
        if (customerObj != null && Customer.class.isInstance(customerObj)) {
            final Customer customer = Customer.class.cast(customerObj);
            this.customer = Optional.of(customer);
            this.lbCustomer.setText(customer.getFirstname() + " " + customer.getLastname());
            this.lbBirthDate.setText(customer.getBirthdate().toString());
        }
    }


    /**
     * Method to compile all fields with the information of registration to modify
     * if it is present.
     */
    private void setRegistrationInfoToModify() {
        final Object registrationObj = this.navService.getNavParams().get("registration");
        if (registrationObj != null && Registration.class.isInstance(registrationObj)) {
        	Registration registration = Registration.class.cast(registrationObj);
            this.registration = Optional.of(Registration.class.cast(registrationObj));
        	this.lbCustomer.setText(this.customerRepo.get(registration.getIdClient()).get().getFirstname()
                    + " " + this.customerRepo.get(registration.getIdClient()).get().getLastname());
        	this.lbBirthDate.setText(this.customerRepo.get(registration.getIdClient()).get().getBirthdate().toString());
        	this.txfDiscount.setText(String.valueOf(registration.getDiscount()));
            this.cmbSubscription.setValue(registration.getType());
            this.lstAdditionalService.setItems(this.serviceRepo.getAll());
            this.lstAdditionalService.setSelected(registration.getAdditionalService());

        	if (TermRegistration.class.isInstance(registrationObj)) {
        		registration = TermRegistration.class.cast(registrationObj);

                this.termRadioButton.setSelected(true);
                this.txfDuration.setText(String.valueOf(((TermRegistration) registration).getDuration()));
                this.txfDiscount.setText(String.valueOf(registration.getDiscount()));
                this.dtpStartDay.setValue(((TermRegistration) registration).getSigningDate());

        	} else if (NumberedRegistration.class.isInstance(registrationObj)) {
        	    registration = NumberedRegistration.class.cast(registrationObj);
        	    this.registration = Optional.of(Registration.class.cast(registrationObj));

        		this.numberedRadioButton.setSelected(true);
                this.txfEntries.setText(String.valueOf(((NumberedRegistration) registration).getMaxEntries()));
        	}
        }
    }
}
