package gymman.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * Factory for embedding buttons in table cells
 */
public class ButtonsCellFactory<T> {

    private int padding;
    private int spacing;
    private final List<ButtonFactory> buttonFactories = new LinkedList<>();

    /**
     * Make a {@code ButtonsCellFactory} with a default zero padding and spacing
     */
    public ButtonsCellFactory() {
        this(0, 0);
    }

    /**
     *
     * @param padding
     * @param spacing
     */
    public ButtonsCellFactory(final int padding, final int spacing) {
        this.padding = padding;
        this.spacing = spacing;
    }

    /**
     * Add a button, enabled by default
     * @param label
     * @param onClick
     */
    public void addButton(final String label, final Consumer<T> onClick) {
        this.addButton(label, true, onClick);
    }

    /**
     * Add a button
     * @param label
     * @param enabled
     * @param onClick
     */
    public void addButton(final String label, final boolean enabled, final Consumer<T> onClick) {
        this.buttonFactories.add(new ButtonFactory(label, enabled, onClick));
    }

    public Callback<TableColumn<T, Void>, TableCell<T, Void>> getFactory() {
        return col -> new TableCell<T, Void>() {
            @Override
            public void updateItem(final Void item, final boolean empty) {
                if (empty) {
                    this.setGraphic(null);
                } else {
                    final T tableItem = this.getTableView().getItems().get(this.getIndex());
                    this.setGraphic(getButtonsForItem(tableItem));
                }
            }
        };
    }

    private HBox getButtonsForItem(final T item) {
        final HBox buttons = new HBox();
        buttons.setPadding(new Insets(padding));
        buttons.setSpacing(spacing);

        buttons.getChildren().setAll(
            this.buttonFactories.stream()
                .map(e -> e.getButtonForItem(item))
                .collect(Collectors.toList())
        );

        return buttons;
    }

    private class ButtonFactory {
        private final String label;
        private final boolean enabled;
        private final Consumer<T> onClick;

        public ButtonFactory(final String label, final boolean enabled, final Consumer<T> onClick) {
            this.label = label;
            this.enabled = enabled;
            this.onClick = onClick;
        }

        public Button getButtonForItem(final T item) {
            final Button btn = new Button(this.label);
            btn.setDisable(!enabled);
            btn.setOnMouseClicked(event -> this.onClick.accept(item));
            return btn;
        }
    }

}
