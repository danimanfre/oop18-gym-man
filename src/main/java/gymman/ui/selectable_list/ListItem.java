package gymman.ui.selectable_list;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * ListItem component for SelectableList
 * @param <T>
 */
public class ListItem<T> extends AnchorPane {

    /**
     * Selection state enum
     */
    public enum State {
        DESELECTED, SELECTED
    }

    private final Label label;
    private final Pane btnSelect;
    private final Pane btnRemove;

    private State state;
    private final T item;
    private SelectionChangeHandler<T> onSelectHandler;
    private SelectionChangeHandler<T> onRemoveHandler;

    public ListItem(final T item, final State state) {
        super();

        this.item = item;
        this.state = state;

        getStyleClass().add("item");

        this.label = new Label(item.toString());

        this.label.setMaxWidth(360);
        this.label.setEllipsisString("...");
        final Tooltip tooltip = new Tooltip(item.toString());
        this.label.setTooltip(tooltip);
        setTopAnchor(this.label, 0.0);
        setBottomAnchor(this.label, 0.0);
        setLeftAnchor(this.label, 0.0);

        final Label plusIcon = new Label("+");
        plusIcon.setStyle("-fx-font-weight: bold");

        final Label minusIcon = new Label("-");
        minusIcon.setStyle("-fx-font-weight: bold");

        this.btnSelect = new StackPane(plusIcon);
        this.btnSelect.getStyleClass().add("item__button");
        this.btnSelect.getStyleClass().add("item__button--select");
        this.btnSelect.setPrefWidth(20.0);
        this.btnSelect.setPrefHeight(20.0);
        this.btnSelect.setOnMouseClicked(e -> this.onItemSelected());
        setTopAnchor(this.btnSelect, 0.0);
        setBottomAnchor(this.btnSelect, 0.0);
        setRightAnchor(this.btnSelect, 0.0);

        this.btnRemove = new StackPane(minusIcon);
        this.btnRemove.getStyleClass().add("item__button");
        this.btnRemove.getStyleClass().add("item__button--remove");
        this.btnRemove.setPrefWidth(20.0);
        this.btnRemove.setPrefHeight(20.0);
        this.btnRemove.setOnMouseClicked(e -> this.onItemRemoved());
        setTopAnchor(this.btnRemove, 0.0);
        setBottomAnchor(this.btnRemove, 0.0);
        setRightAnchor(this.btnRemove, 0.0);

        getChildren().add(this.label);
        getChildren().add(this.btnSelect);
        getChildren().add(this.btnRemove);

        switch (state) {
            case DESELECTED:
                this.remove();
                break;
            case SELECTED:
                this.select();
                break;
        }
    }

    /**
     * Returns the current selection state
     * @return State
     */
    public State getCurrentState() {
        return this.state;
    }

    /**
     * Get the underlying item
     * @return
     */
    public T getItem() {
        return this.item;
    }

    /**
     * Change the appearance to "selected"
     */
    public void select() {
        this.btnRemove.setMouseTransparent(false);
        this.btnSelect.setMouseTransparent(true);
        this.getStyleClass().add("item--selected");
    }

    /**
     * Change the appearance to "deselected"
     */
    public void remove() {
        this.btnRemove.setMouseTransparent(true);
        this.btnSelect.setMouseTransparent(false);
        this.getStyleClass().remove("item--selected");
    }

    /**
     * Set the callback for when the item gets selected
     * @param handler
     */
    public void onSelect(final SelectionChangeHandler<T> handler) {
        this.onSelectHandler = handler;
    }

    /**
     * Set the callback for when the item gets deselected
     * @param handler
     */
    public void onRemove(final SelectionChangeHandler<T> handler) {
        this.onRemoveHandler = handler;
    }

    private void onItemSelected() {
        this.onSelectHandler.handle(this);
        this.select();
    }

    private void onItemRemoved() {
        this.onRemoveHandler.handle(this);
        this.remove();
    }
}
