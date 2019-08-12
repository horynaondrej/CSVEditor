
package sample;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class that writes a data to CSV file
 */
public class Writer {

    private String name;
    private String code;
    private boolean slashes;
    private Stage g;
    private ObservableList<ObservableList<StringProperty>> data;
    private String file_name = "Untitled";

    /**
     * Class constructor
     * @param name
     * @param data
     * @param g
     * @param code
     */
    public Writer(String name,
                  ObservableList<ObservableList<StringProperty>> data,
                  Stage g,
                  String code,
                  boolean slashes) {

        this.name = name;
        this.data = data;
        this.g = g;
        this.code = code;
        this.slashes = slashes;

    }

    public void set_file_name(String param) {

        this.file_name = param;

    }

    public void write_to_file(int number_of_columns) {

        File file = this.make_connection();
        try {
            CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), this.code), ';', '\"', "\n");
            Throwable throwable = null;
            try {
                //This creates a array (one row)
                //with strings as how many columns is in table
                String[] list = new String[number_of_columns];
                //go through data
                for (ObservableList i : this.data) {
                    for (int b = 0; b < number_of_columns; ++b) {
                        list[b] = ((StringProperty)i.get(b)).getValue();
                    }
                    writer.writeNext(list);
                }
                writer.close();
            }
            catch (Throwable list) {
                throwable = list;
                throw list;
            }
            finally {
                if (writer != null) {
                    if (throwable != null) {
                        try {
                            writer.close();
                        }
                        catch (Throwable list) {
                            throwable.addSuppressed(list);
                        }
                    } else {
                        writer.close();
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method create a dialog for save file
     * @return file
     */
    private File make_connection() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(this.file_name);
        File file = fileChooser.showSaveDialog(this.g);
        return file;

    }

}
