import java.sql.*;

public class Database {

    static Connection con;

    public static java.sql.Connection createConnection() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=autocamper", "sa", "123456");
            System.out.println("Connected");
            return con;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
/*
    public static ObservableList<Autocamper> hentAutocamper(){
        Connection con = createConnection();
        CallableStatement myStmt = null;

        ObservableList<Autocamper> autocamperList = FXCollections.observableArrayList();
        try{
            myStmt = con.prepareCall("{call dbo.autocamper_liste()}");

            ResultSet rs = myStmt.executeQuery();

            while (rs.next()){
                autocamperList.add(new Autocamper
                        (Integer.parseInt(rs.getString("fldAutoCamperID")), rs.getString("fldAutoCamperType"), Float.parseFloat(rs.getString("fldPris"))));
            }

        }catch(Exception e){
            e.printStackTrace();
        }



        return autocamperList;
    }

 */
}


