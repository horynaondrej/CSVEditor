
package sample;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * App class that read a CSV file
 */
public class Reader {

    private ObservableList<ObservableList<StringProperty>> data;
    private Stage g;
    private char separator;
    private String coding;
    private String[] line;
    private String file_name;
    private boolean quote;

    /**
     * Class constructor
     * @param g
     * @param key
     * @param code
     */
    public Reader(Stage g, char key, String code, boolean quote) {

        this.data = FXCollections.observableArrayList();
        this.g = g;
        this.separator = key;
        this.coding = code;
        this.quote = quote;

    }

    /**
     * This method read a cvs file
     */
    public void file_reading() {

        try {
            File file = this.make_connection();
            this.file_name = file.getName();
            CSVReader reader = new CSVReader(
                    new InputStreamReader(
                            new FileInputStream(file.getAbsoluteFile()),
                            this.coding),
                    this.separator,
                    '\"',
                    this.quote
            );
            while ((this.line = reader.readNext()) != null) {
                this.data.add(this.transform_data(this.line));
            }
            reader.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * This methos returns file_name of the file
     */
    public String get_file_name() {

        return (this.file_name != null) ? this.file_name : null;

    }

    /**
     * This method create a dialog for open file
     * @return file
     */
    private File make_connection() {

        FileChooser fileChooser = new FileChooser();
        //open only csv files
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(this.g);
        return file;

    }

    /**
     * This method cut the lines to columns
     * @param line
     * @return
     */
    private ObservableList<StringProperty> transform_data(String[] line) {

        ObservableList data = FXCollections.observableArrayList();
        ObservableList list = FXCollections.observableArrayList();
        for (String i : line) {
            list.add(new SimpleStringProperty(i));
        }
        data.addAll(list);
        return data;

    }

    /**
     * This method returns number of columns
     * @return
     */
    public int number_of_columns() {

        int row = 0;
        for (ObservableList i : this.data) {
            if (i.size() <= row) continue;
            row = i.size();
        }
        return row;

    }

    /**
     * This method returns data
     * @return
     */
    public ObservableList<ObservableList<StringProperty>> get_data() {

        return data;

    }
}
