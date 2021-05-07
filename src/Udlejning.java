public class Udlejning {
    int ordreID;
    String startDato, slutDato;
    int autocamperID, kundeID;


    public Udlejning(int ordreID, String startDato, String slutDato, int autocamperID, int kundeID) {
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.ordreID = ordreID;
        this.autocamperID = autocamperID;
        this.kundeID = kundeID;
    }

    public String getStartDato() {
        return startDato;
    }

    public void setStartDato(String startDato) {
        this.startDato = startDato;
    }

    public String getSlutDato() {
        return slutDato;
    }

    public void setSlutDato(String slutDato) {
        this.slutDato = slutDato;
    }

    public int getAutocamperID() {
        return autocamperID;
    }

    public void setAutocamperID(int autocamperID) {
        this.autocamperID = autocamperID;
    }

    public int getKundeID() {
        return kundeID;
    }

    public void setKundeID(int kundeID) {
        this.kundeID = kundeID;
    }

    public int getOrdreID() {
        return ordreID;
    }

    public void setOrdreID(int ordreID) {
        this.ordreID = ordreID;
    }

}
