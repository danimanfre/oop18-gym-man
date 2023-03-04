package gymman.ui.tool;

import java.util.Locale;
import java.util.Optional;

import gymman.common.DuplicateEntityException;
import gymman.customers.InvalidValueException;
import gymman.tool.Tool;
import gymman.tool.ToolRepository;
import gymman.ui.Controller;
import gymman.ui.navigation.NavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * The Class NewToolController.
 */
public class NewToolController implements Controller {


    /** The btncrea. */
    @FXML private Button btncrea;

    /** The txtidentificativo. */
    @FXML private TextField txtname, txtmanutenzione, txtidentificativo;

    /** The txtdescrizione. */
    @FXML private TextArea txtdescrizione;

    /** The tool repository impl. */
    private final ToolRepository  toolRepositoryImpl;

    /** The nav. */
    private final NavigationService nav;

    /** The tool. */
    private Optional<Tool> tool = Optional.empty();


    /**
     * Instantiates a new new tool controller.
     *
     * @param tool the tool
     * @param nav the nav
     */
    public NewToolController(
             final ToolRepository tool,
             final NavigationService nav
            ) {
        this.toolRepositoryImpl = tool;
        this.nav = nav;
    }

    /**
     * Initialize ui.
     */
    @FXML
    private void initialize() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisplay() {
        this.tool = Optional.empty();

        final Object toolObj = this.nav.getNavParams().get("tool");
        if (toolObj != null && Tool.class.isInstance(toolObj)) {
            final Tool tool = Tool.class.cast(toolObj);
            this.tool = Optional.of(tool);
            this.txtname.setText(tool.getName());
            this.txtdescrizione.setText(tool.getDesc());
            this.txtmanutenzione.setText(tool.getMaintenance().toString());
            this.txtidentificativo.setText(tool.getNumseriale());
        } else {
            clearAll();
        }

    }

    /**
     * Creates tool.
     *
     * @throws InvalidValueException the invalid value exception
     */
    @FXML public void create() throws InvalidValueException  {
    if (this.isFilledOut()) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Creare l'attrezzo?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                final Tool.ToolBuilder toolBuilder = Tool.builder()
                          .name(this.txtname.getText().toLowerCase(Locale.getDefault()).trim())
                          .desc(this.txtdescrizione.getText().trim())
                          .numseriale(this.txtidentificativo.getText().toUpperCase(Locale.getDefault()).trim())
                          .maintenance(Integer.valueOf(this.txtmanutenzione.getText()));
                   if (this.tool.isPresent()) {
                       toolBuilder.id(tool.get().getId());
                   }
               try {
                   clearAll();
                    this.toolRepositoryImpl.add(toolBuilder.build());
                    this.nav.backOr("page_tool_list");

                } catch (DuplicateEntityException e) {
                    alert = new Alert(AlertType.WARNING, "Esiste già un Attrezzo con questo Nome - Seriale", ButtonType.OK);
                    alert.showAndWait();
                } catch (NumberFormatException e) {
                    alert = new Alert(AlertType.WARNING, "Valore Manutenzione Errato", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Checks if is filled out.
     *
     * @return true, if is filled out
     */
    public boolean isFilledOut() {
        clearSetStyleAll();
        Boolean test = false;
        String error = "Errore nei i campi\n";
        if (this.txtname.getText().trim().length() == 0) {
            this.highlight(this.txtname);
            error += "- Nome \n";
            test = true;
        }
        if (!this.txtmanutenzione.getText().trim().matches("[0-9]+(\\[0-9]+){0,1}")
            || Integer.valueOf(this.txtmanutenzione.getText()) > 12
            || Integer.valueOf(this.txtmanutenzione.getText()) < 0
                ) {
            this.highlight(this.txtmanutenzione);
            error += "- Periodo manutenzione \n";
            test = true;
        }
        if (this.txtidentificativo.getText().trim().length() == 0) {
            this.highlight(this.txtidentificativo);
            error += "- Seriale \n";
            test = true;
        }
        if (test) {
            final Alert alert = new Alert(AlertType.WARNING, error, ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Clear all.
     */
    public void clearAll() {
        this.txtname.clear();
        this.txtdescrizione.clear();
        this.txtmanutenzione.clear();
        this.txtidentificativo.clear();
        initialize();
    }

    /**
     * Clear set style all.
     */
    public void clearSetStyleAll() {
        this.txtname.setStyle("");
        this.txtdescrizione.setStyle("");
        this.txtmanutenzione.setStyle("");
        this.txtidentificativo.setStyle("");
    }

    /**
     * Highlight.
     *
     */
    private void highlight(final Control cont) {
        cont.setStyle("-fx-border-color: red");
    }




}
