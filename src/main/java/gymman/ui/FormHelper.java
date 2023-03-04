package gymman.ui;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class FormHelper {

    private FormHelper() {}

    public static boolean isFilledOut(final Node form) {
        return form.lookupAll("TextField").stream()
                .filter(TextField.class::isInstance)
                .filter(e -> !e.isDisabled())
                .filter(e -> !e.getId().equals("txfPassword"))
                .map(TextField.class::cast)
                .map(e -> !e.getText().trim().isEmpty())
                .reduce(true, (a, b) -> a = a && b);
    }

    public static void highlightWrongField(final Control cont) {
        cont.setStyle("-fx-border-color: red");
    }

    public static boolean isBlank(final TextField text) {
        return text.getText().trim().isEmpty();
    }

    public static String getTextTrimmed(final TextField text) {
        return text.getText().trim();
    }
}
