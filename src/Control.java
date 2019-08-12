package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Ondřej Horyna on 2019-07-26.
 */
public class Control {

    /**
     * Main app variables
     */
    private Stage stage;
    private TableView<ObservableList<StringProperty>> table;
    private ObservableList<ObservableList<StringProperty>> data = FXCollections.observableArrayList();
    private VBox root;
    private VBox content;
    private HBox footer;
    private int number_of_columns;
    private char separator;
    private boolean quote;
    private String coding;
    private ArrayList<String> column_names = new ArrayList();
    private String file_name;

    private MenuBar menuBar;
    private Menu file;
    private MenuItem open;
    private MenuItem save;
    private MenuItem close;
    private Menu help;
    private MenuItem about;

    private Button delete;
    private Button add;

    /**
     * Class constructor
     */
    public Control(Stage stage) {

        this.stage = stage;
        set_main_marks();
        initialize_controls();
        initialize_menu();
        initialize_gui();

    }

    /**
     * This method set up a separator that divides the data
     * and code the file is formatted
     */
    public void set_main_marks() {

        this.separator = ';';
        this.coding = "UTF-8";

    }

    /**
     * This method initialize a application forms
     */
    private void initialize_gui() {

        this.root = new VBox();

        this.table = new TableView();

        this.content = new VBox();

        this.content.setPadding(new Insets(0.0, 5.0, 0.0, 5.0));
        VBox.setVgrow(this.table, Priority.ALWAYS);
        this.content.getChildren().add(this.table);

        this.footer = new HBox();

        this.footer.setSpacing(5.0);
        this.footer.setPadding(new Insets(0.0, 5.0, 5.0, 5.0));
        this.footer.setAlignment(Pos.CENTER_LEFT);

        this.footer.getChildren().addAll(add, delete);
        VBox.setVgrow(this.content, Priority.ALWAYS);
        this.root.setSpacing(5.0);
        this.root.getChildren().addAll(menuBar, this.content, this.footer);

    }

    /**
     * This method returns main box
     * @return vbox This getter returns tha main app window
     */
    VBox get_root() {

        return root;

    }

    /**
     * This method initialize two buttons - add and delete line
     */
    private void initialize_controls() {

        this.delete = new Button("Smazat řádek");
        this.delete.setOnAction(event -> remove_line());

        this.add = new Button("Přidat řádek");
        this.add.setOnAction(event -> add_line());

    }

    /**
     * This method remove selected line
     */
    private void remove_line() {

        ObservableList<ObservableList<StringProperty>> sel = this.table.getSelectionModel().getSelectedItems();
            for (ObservableList<StringProperty> m : sel) {
                this.data.remove(m);
            }

    }

    /**
     * This method add a new line into table
     */
    private void add_line() {

        if (this.number_of_columns > 0) {
            ObservableList<StringProperty> list = FXCollections.observableArrayList();
            for (int i = 0; i < this.number_of_columns; ++i) {
                list.add(new SimpleStringProperty(""));
            }
            this.data.add(list);
        }
    }


    /**
     * This method initialize menu
     */
    private void initialize_menu() {

        this.menuBar = new MenuBar();
        this.file = new Menu("Soubor");
        this.open = new MenuItem("Otevřít");
        this.open.setOnAction(e -> dialog_window(true));
        this.save = new MenuItem("Uložit jako");
        this.save.setOnAction(e -> dialog_window(false));
        this.close = new MenuItem("Zavřít");
        this.close.setOnAction(event -> this.table.getColumns().clear());

        this.file.getItems().addAll(
                this.open,
                this.save,
                this.close
        );

        this.help = new Menu("Nápověda");
        this.about = new MenuItem("O programu");
        this.about.setOnAction(e -> info());
        this.help.getItems().add(this.about);

        this.menuBar.getMenus().addAll(
                this.file,
                this.help);

    }

    /**
     * This method provides a dialog, where is written about app
     */
    private void info() {

        Info dialog = new Info(this.stage.getScene().getWindow());
        dialog.showAndWait();

    }

    /**
     * This method provides a dialog for open or save file. It asks user
     * for devider and file code format
     * @param type There goes a boolen parameter and it turns the function
     *             from open dialog to save dialog
     */
    private void dialog_window(boolean type) {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Parametry pro čtení souboru");
        dialog.setHeaderText("Zadejte prosím parametry pro otevření CSV souboru.\n"+
                "Pro oddělovač zadejte pouze jediný znak");

        ButtonType loginButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,150,10,10));

        TextField marker = new TextField();
        marker.setText(Character.toString(this.separator));

        //create a combobox, there user could choose a file format
        ComboBox<String> coding = new ComboBox<>();
        coding.getItems().add("UTF-8");
        coding.getItems().add("ISO-8859-2");
        coding.getItems().add("WINDOWS-1250");
        coding.setValue("UTF-8");

        CheckBox ch = new CheckBox();
        ch.setSelected(true);

        grid.add(new Label("Oddělovač:"), 0,0);
        grid.add(marker,1,0);
        grid.add(new Label("Kódování:"), 0,1);
        grid.add(coding,1,1);

        if (type == false) {
            grid.add(new Label("Přidat uvozovky:"), 0, 2);
            grid.add(ch, 1, 2);
        } else {
            grid.add(new Label("Obsahuje uvozovky:"), 0, 2);
            grid.add(ch, 1, 2);
        }

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> marker.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(marker.getText(), coding.getValue());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
            //System.out.println("Marker=" + usernamePassword.getKey() + ", Coding=" + usernamePassword.getValue());
            this.separator = marker.getText().charAt(0);
            this.coding = coding.getValue();
            this.quote = ch.isSelected();
            if (type) {
                this.open_file();
            } else {
                this.save_file();
            }
        });
    }

    /**
     * This method opens a file with dialog
     */
    private void open_file() {

        try {
            //create new class to read from a file
            Reader csv = this.create_file();
            csv.file_reading();

            //gets the file name
            this.file_name = csv.get_file_name();

            //delete a previous data from table
            this.data.clear();

            //add a new data
            this.data.addAll(csv.get_data());
            this.number_of_columns = csv.number_of_columns();
            this.column_names.clear();
            this.fill_array_with_created_columns_names();
            this.table.getColumns().clear();
            this.table = this.create_table();
        } catch (Exception i) {
            System.out.println("Nepodařilo se otevřít CSV soubor." + i.getMessage());
        }
        try {
            this.content.getChildren().clear();
            this.content.setPadding(new Insets(0.0, 5.0, 0.0, 5.0));
            VBox.setVgrow(this.table, Priority.ALWAYS);
            this.content.getChildren().add(this.table);
        } catch (Exception f) {
            System.out.println("Přehrání tabulky se nezdařilo " + f.getMessage() + ".");
        }
    }

    /**
     * This method create new object of Reader class; it reads a file
     * @return Reader class object
     */
    private Reader create_file() {

        return new Reader(this.stage, this.separator, this.coding, this.quote);

    }

    /**
     * Fill an array with created names of columns
     */
    private void fill_array_with_created_columns_names() {

        for (int i = 0; i < this.number_of_columns; ++i) {
            String title = "Sloupec " + (i + 1);
            this.column_names.add(title);
        }

    }

    /**
     * This method creates a new table
     * @return table
     */
    private TableView<ObservableList<StringProperty>> create_table() {

        TableView<ObservableList<StringProperty>> tab = new TableView<>();
        tab.setEditable(true);
        tab.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tab.setItems(data);
        for (int i = 0; i < this.number_of_columns; ++i) {
            tab.getColumns().addAll(this.create_column(this.column_names.get(i), i));
        }
        tab.setEditable(true);
        tab.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tab;

    }

    /**
     * This method creates a column for tableview
     * @param name
     * @param index
     * @return
     */
    private TableColumn<ObservableList<StringProperty>, String> create_column(String name, int index) {

        TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>(name);
        column.setCellValueFactory(cellDataFeatures -> {
                    ObservableList<StringProperty> values = cellDataFeatures.getValue();
                    for (int i = values.size(); i <= index; ++i) {
                        values.add(i, new SimpleStringProperty(""));
                    }
                    return cellDataFeatures.getValue().get(index);
                }
        );
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setMinWidth(120.0);
        return column;

    }

    /**
     * This method writes data to the file
     */
    private void save_file() {

        Writer writer = new Writer("",
                this.data,
                this.stage,
                this.coding,
                this.quote);
        writer.set_file_name(this.file_name);
        writer.write_to_file(this.number_of_columns);

    }
    
}
