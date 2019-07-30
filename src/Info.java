
package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Info extends Stage {

    /**
     * Class constructor
     * @param window
     */
    public Info(Window window) {
        this.setTitle("O programu");
        this.setWidth(350.0);
        this.setHeight(250.0);
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(window);
        this.setScene(this.about_dialog());
    }

    private Scene about_dialog() {
        VBox window = new VBox();
        window.setAlignment(Pos.CENTER);
        window.setSpacing(5.0);
        window.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        TextArea text = new TextArea();
        text.setEditable(false);
        text.setWrapText(true);
        text.setText("Tento program byl vytvořen pro uživatele, kteří potřebují " +
                "v krátkém čase upravit nebo opravit CSV soubory.\n\r\n\rNejprve " +
                "zadejte dělící znak a potvrďtě jej tlačítkem POTVRDIT. Po jeho " +
                "nahrání pokračujte volbou z menu -> Soubor -> Otevřít, kde vyberte " +
                "požadovaný CSV soubor.\n\r\n\rTento soubor je možné editovat, a to tak, " +
                "že kliknete do požadované buňky dvojklikem. Zobrazí se znak pro úpravu " +
                "textu a následně je možné text poupravit nebo celý přepsat. Nezapomeňte " +
                "tuto úpravu potvrdit klávesou ENTER. \n\r\n\rJestliže je potřeba smazat " +
                "určitý řádek, klikněte na něj. Následně se označí tmavě modrou barvou. " +
                "Jestliže je takto označený, je možné jej smazat tlačítkem SMAZAT ŘÁDEK. " +
                "V tomto případě věnujte smazání zvýšenou pozornost, program zatím neumí " +
                "krok zpět.\n\r\n\rDále je možné řádky přidávat a to kliknutím na tlačítko " +
                "PŘIDAT ŘÁDEK.");
        Button ok_button = new Button("OK");
        ok_button.setOnAction(e -> {
                    Stage stage = (Stage)window.getScene().getWindow();
                    stage.close();
                }
        );
        VBox.setVgrow(text, Priority.ALWAYS);
        window.getChildren().addAll(text, ok_button);
        return new Scene(window);

    }
}