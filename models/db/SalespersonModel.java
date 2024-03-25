package models.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalespersonModel {
    private int sID;
    private String sName;
    private String sAddress;
    private int sPhoneNumber;
    private int sExperience;

    public SalespersonModel(int sID, String sName, String sAddress, int sPhoneNumber, int sExperience){
        this.sID = sID;
        this.sName = sName;
        this.sAddress = sAddress;
        this.sPhoneNumber = sPhoneNumber;
        this.sExperience = sExperience;
    }

    public void insertTodb (Connection conn){
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO SALESPERSON (sID, sName, sAddress, sPhoneNumber, sExperience) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, Integer.toString(sID));
            statement.setString(2, sName);
            statement.setString(3, sAddress);
            statement.setString(4, Integer.toString(sPhoneNumber));
            statement.setString(5, Integer.toString(sExperience));
            statement.execute();
        } catch (SQLException e) {
            System.out.println("[Error] " + e);
        }
    }
}
