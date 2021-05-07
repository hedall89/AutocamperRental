import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.DriverManager;


public class Controller {

    @FXML
    private Pane pnlKundeListe, pnlOrdreHistorik, pnlKørekort, pnlBookAutocamper, pnlVælgAutocamper;
    @FXML
    private Button btnBookAutocamper, btnOrdreHistorik, btnKundeListe, btnKøreKort, btnBekræftReservation;
    @FXML
    private Button btnVælgAutoCamper;
    @FXML
    private TableView<?> tblViewKundeListe;
    @FXML
    private TextField txtfldOrdreID, txtfldKørekortID, txtfldUdstedelsesDato, txtfldOrdrehistorikSøgeFelt, txtfldOrdreSøgefelt;
    @FXML
    private TextArea txtAreaOrdreBekræftelse;
    @FXML
    private DatePicker startDato, slutDato;
    @FXML
    private TextField txtnavn, txtaddresse, txtpostnummer, txtemail, txttlf;
    @FXML
    private TableView<Autocamper> tblViewAutocamper;
    @FXML
    private TableColumn<Autocamper, Integer> col_autocamperID;
    @FXML
    private TableColumn<Autocamper, String> col_autocamperType;
    @FXML
    private TableColumn<Autocamper, Float> col_pris;

    ObservableList<Autocamper> autocamperObservableList;

    ObservableList<Autocamper> autocamperList = FXCollections.observableArrayList();

    Connection con = null;
    CallableStatement myStmt = null;


    public void handleButtonAction(ActionEvent event) {
        {
            if (event.getSource() == btnBookAutocamper) {
                pnlBookAutocamper.toFront();
            } else if (event.getSource() == btnOrdreHistorik) {
                pnlOrdreHistorik.toFront();
            } else if (event.getSource() == btnKundeListe) {
                pnlKundeListe.toFront();
            } else if (event.getSource() == btnKøreKort) {
                pnlKørekort.toFront();
            } else if (event.getSource() == btnBekræftReservation) {
                pnlVælgAutocamper.toFront();
                //lavKunde();
                autocamperListe();
            } else if (event.getSource() == btnVælgAutoCamper) {
                pnlBookAutocamper.toFront();
                lavUdlejning();
            }
        }
    }


    void lavKunde() {
        System.out.println(txtnavn.getText() + " " + txtaddresse.getText() + " " + txtpostnummer.getText() + " " + txtemail.getText() + " " + txttlf.getText());

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=autocamper", "sa", "123456");
            System.out.println("Connected");
            //stored procedure
            myStmt = con.prepareCall("{call dbo.lav_Kunde(?,?,?,?,?)}");

            String navn = txtnavn.getText();
            String adresse = txtaddresse.getText();
            String postNummer = txtpostnummer.getText();
            String email = txtemail.getText();
            String tlf = txttlf.getText();

            //sætte de forskellige parameter i den stored procedure
            myStmt.registerOutParameter(1, Types.VARCHAR);
            myStmt.setString(1, navn);

            myStmt.registerOutParameter(2, Types.VARCHAR);
            myStmt.setString(2, adresse);

            myStmt.registerOutParameter(3, Types.INTEGER);
            myStmt.setString(3, postNummer);

            myStmt.registerOutParameter(4, Types.VARCHAR);
            myStmt.setString(4, email);

            myStmt.registerOutParameter(5, Types.VARCHAR);
            myStmt.setString(5, tlf);

            myStmt.execute();
            con.close();
            System.out.println("Disconnected");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void lavUdlejning() {
        System.out.println(startDato.getValue() + " " + slutDato.getValue());

    }


        void autocamperListe () {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=autocamper", "sa", "123456");
                System.out.println("Connected");
                myStmt = con.prepareCall("{call dbo.autocamper_liste()}");

                ResultSet rs = myStmt.executeQuery();

                while (rs.next()) {
                    autocamperList.add(new Autocamper
                            (Integer.parseInt(rs.getString("fldAutoCamperID")), rs.getString("fldAutoCamperType"), Float.parseFloat(rs.getString("fldPris"))));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            col_autocamperID.setCellValueFactory(new PropertyValueFactory<Autocamper, Integer>("autocamperID"));
            col_autocamperType.setCellValueFactory(new PropertyValueFactory<Autocamper, String>("autocamperType"));
            col_pris.setCellValueFactory(new PropertyValueFactory<Autocamper, Float>("pris"));

            autocamperObservableList = autocamperList;

            tblViewAutocamper.setItems(autocamperObservableList);
        }


}




