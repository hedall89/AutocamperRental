public class Autocamper {

    int autocamperID;
    String autocamperType;
    float pris;

    public Autocamper(int autocamperID, String autocamperType, float pris) {
        this.autocamperID = autocamperID;
        this.autocamperType = autocamperType;
        this.pris = pris;
    }

    public int getAutocamperID() {
        return autocamperID;
    }

    public void setAutocamperID(int autocamperID) {
        this.autocamperID = autocamperID;
    }

    public String getAutocamperType() {
        return autocamperType;
    }

    public void setAutocamperType(String autocamperType) {
        this.autocamperType = autocamperType;
    }

    public float getPris() {
        return pris;
    }

    public void setPris(float pris) {
        this.pris = pris;
    }
}

