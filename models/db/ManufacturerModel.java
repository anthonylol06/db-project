package models.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManufacturerModel {
    private int mID;
    private String mName;
    private String mAddress;
    private int mPhoneNumber;

    public ManufacturerModel (int mID, String mName, String mAddress, int mPhoneNumber) {
        this.mID = mID;
        this.mName = mName;
        this.mAddress = mAddress;
        this.mPhoneNumber = mPhoneNumber;
    }

    public void insertTodb (Connection conn){
        try {
            PreparedStatement statement = 
                conn.prepareStatement("INSERT INTO MANUFACTURER (mID, mName, mAddress, mPhoneNumber) VALUES (?, ?, ?, ?)");
            statement.setString(1, Integer.toString(mID));
            statement.setString(2, mName);
            statement.setString(3, mAddress);
            statement.setString(4, Integer.toString(mPhoneNumber));
            statement.execute();
        } catch (SQLException e) {
            System.out.println("[Error] " + e);
        }
    }
}
