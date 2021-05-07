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
import java.time.LocalDate;


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

    @FXML
    private TableView<Udlejning> tblViewOrdreList;

    @FXML
    private TableColumn<Udlejning, Integer> col_ordreID;

    @FXML
    private TableColumn<Udlejning, String> col_startDato;

    @FXML
    private TableColumn<Udlejning, String> col_slutDato;

    @FXML
    private TableColumn<Udlejning, Integer> col_ordrerAutocamperID;

    @FXML
    private TableColumn<Udlejning, Integer> col_KundeID;

    ObservableList<Autocamper> autocamperObservableList;
    ObservableList<Autocamper> autocamperList = FXCollections.observableArrayList();

    ObservableList<Udlejning> udlejningsList = FXCollections.observableArrayList();
    ObservableList<Udlejning> udlejningsObservableList;

    Connection con = null;
    CallableStatement myStmt = null;
    String navn;


    public void handleButtonAction(ActionEvent event) {
        {
            if (event.getSource() == btnBookAutocamper) {
                pnlBookAutocamper.toFront();
            } else if (event.getSource() == btnOrdreHistorik) {
                pnlOrdreHistorik.toFront();
                ordreHistorik();
            } else if (event.getSource() == btnKundeListe) {
                pnlKundeListe.toFront();
            } else if (event.getSource() == btnKøreKort) {
                pnlKørekort.toFront();
            } else if (event.getSource() == btnBekræftReservation) {
                pnlVælgAutocamper.toFront();
                lavKunde();
                autocamperListe();
            } else if (event.getSource() == btnVælgAutoCamper) {
                pnlBookAutocamper.toFront();
                lavOrdre();
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

            navn = txtnavn.getText();
            String adresse = txtaddresse.getText();
            String postNummer = txtpostnummer.getText();
            String email = txtemail.getText();
            String tlf = txttlf.getText();

            //sætter de forskellige parameter i den stored procedure
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

    void lavOrdre() {

         int kundeID = 1;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=autocamper", "sa", "123456");
            System.out.println("Connected");

            //kundeID
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select fldKundeID from tblKunder where fldNavn = '" + navn + "'");

            //stored procedure
            myStmt = con.prepareCall("{call dbo.lav_Udlejning(?,?,?,?)}");


            LocalDate startDatoValue = startDato.getValue();
            LocalDate slutDatoValue = slutDato.getValue();
            Autocamper Auto = tblViewAutocamper.getSelectionModel().getSelectedItem();
            System.out.println("valgte autocamper" + Auto.autocamperID);
            int autocamperID = Auto.autocamperID;

           while (rs.next()){
               kundeID = Integer.parseInt(rs.getString("fldKundeID"));
            }


            //sætte de forskellige parameter i den stored procedure
            myStmt.registerOutParameter(1, Types.DATE);
            myStmt.setString(1, String.valueOf(startDatoValue));

            myStmt.registerOutParameter(2, Types.DATE);
            myStmt.setString(2, String.valueOf(slutDatoValue));

            myStmt.registerOutParameter(3, Types.INTEGER);
            myStmt.setInt(3, autocamperID);

            myStmt.registerOutParameter(4, Types.INTEGER);
            myStmt.setInt(4, kundeID);

            myStmt.execute();
            con.close();
            System.out.println("Disconnected");

            //clear textfields
            txtaddresse.clear();
            txtemail.clear();
            txtnavn.clear();
            txtpostnummer.clear();
            txttlf.clear();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


        void autocamperListe () {
        //fjerner alt data inden det nye data bliver sat ind
        autocamperList.clear();

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

                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            col_autocamperID.setCellValueFactory(new PropertyValueFactory<Autocamper, Integer>("autocamperID"));
            col_autocamperType.setCellValueFactory(new PropertyValueFactory<Autocamper, String>("autocamperType"));
            col_pris.setCellValueFactory(new PropertyValueFactory<Autocamper, Float>("pris"));

            autocamperObservableList = autocamperList;

            tblViewAutocamper.setItems(autocamperObservableList);
        }

        void ordreHistorik() {
        udlejningsList.clear();

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=autocamper", "sa", "123456");
                System.out.println("Connected");
                myStmt = con.prepareCall("{call dbo.udlejning_liste()}");

                ResultSet rs = myStmt.executeQuery();

                while (rs.next()) {
                    udlejningsList.add(new Udlejning
                            (Integer.parseInt(rs.getString("fldOrdreID")), rs.getString("fldStartDato"),
                                    rs.getString("fldSlutDato"),Integer.parseInt(rs.getString("fldAutoCamperID")),
                                    Integer.parseInt(rs.getString("fldKundeID"))));
                }

                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            col_ordreID.setCellValueFactory(new PropertyValueFactory<Udlejning, Integer>("ordreID"));
            col_startDato.setCellValueFactory(new PropertyValueFactory<Udlejning, String>("startDato"));
            col_slutDato.setCellValueFactory(new PropertyValueFactory<Udlejning, String>("slutDato"));
            col_ordrerAutocamperID.setCellValueFactory(new PropertyValueFactory<Udlejning, Integer>("autocamperID"));
            col_KundeID.setCellValueFactory(new PropertyValueFactory<Udlejning, Integer>("kundeID"));

            udlejningsObservableList = udlejningsList;

            tblViewOrdreList.setItems(udlejningsObservableList);

        }

}




