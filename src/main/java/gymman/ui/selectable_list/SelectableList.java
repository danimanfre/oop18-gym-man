package gymman.ui.selectable_list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Custom JavaFX component that displays a list of items where multiple selections can be made
 */
public class SelectableList<T> extends AnchorPane {

    @FXML private VBox lstSelected;
    @FXML private VBox lstAvailable;

    private final List<T> items = new ArrayList<>();
    private final List<T> selected = new ArrayList<>();

    public SelectableList() {
        super();

        final FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectableList.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setItems(final List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        this.update();
    }

    public void setSelected(final List<T> items) {
        for (final T item : items) {
            this.selectItem(item);
        }
    }

    /**
     * Return a list of selected items
     * @return list of selected items
     */
    public List<T> getSelectedItems() {
        return new ArrayList<>(this.selected);
    }

    /**
     * Remove all items from the list
     */
    public void clear() {
        this.items.clear();
        this.selected.clear();
        this.update();
    }

    private void update() {
        this.lstAvailable.getChildren().setAll(
            this.buildItems(this.items, ListItem.State.DESELECTED)
        );

        this.lstSelected.getChildren().setAll(
            this.buildItems(this.selected, ListItem.State.SELECTED)
        );
    }

    private void selectItem(final T item) {
        this.items.remove(item);
        this.selected.add(item);
        this.update();
    }

    private void removeItem(final T item) {
        this.selected.remove(item);
        this.items.add(item);
        this.update();
    }

    private List<ListItem<T>> buildItems(final List<T> items, final ListItem.State state) {
        return items.stream()
            .map(e -> new ListItem<>(e, state))
            .map(i -> {
                i.onSelect((item) -> this.selectItem(item.getItem()));
                i.onRemove((item) -> this.removeItem(item.getItem()));
                return i;
            })
            .collect(Collectors.toList());
    }
}
