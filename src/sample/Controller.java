package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import java.sql.SQLException;

public class Controller {

    @FXML
    private Pane pnlKundeListe, pnlOrdreHistorik, pnlKørekort, pnlBookAutocamper, pnlVælgAutocamper;
    @FXML
    private Button btnBookAutocamper, btnOrdreHistorik, btnKundeListe, btnKøreKort, btnBekræftReservation;
    @FXML
    private TableView<?> tblViewKundeListe, tblViewAutocamper;
    @FXML
    private TextField txtfldOrdreID, txtfldKørekortID, txtfldUdstedelsesDato, txtfldOrdrehistorikSøgeFelt, txtfldOrdreSøgefelt;
    @FXML
    private TextArea txtAreaOrdreBekræftelse;




    public void handleButtonAction(ActionEvent event) {
        {
            if (event.getSource() == btnBookAutocamper) {
                pnlBookAutocamper.toFront();
            } else if (event.getSource() == btnOrdreHistorik) {
                pnlOrdreHistorik.toFront();
            }else if (event.getSource() == btnKundeListe){
                pnlKundeListe.toFront();
            }else if (event.getSource() == btnKøreKort){
                pnlKørekort.toFront();
            }else if (event.getSource()== btnBekræftReservation){
                pnlVælgAutocamper.toFront();
            }
    }
}
}




