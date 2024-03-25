package models.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PartModel {
    private int pID;
    private String pName;
    private int pPrice;
    private int mID;
    private int cID;
    private int pWarrantyPeriod;
    private int pAvaliableQuantity;

    public PartModel (int pID, String pName, int pPrice, int mID, int cID, int pWarrantyPeriod, int pAvaliableQuantity){
        this.pID = pID;
        this.pName = pName;
        this.pPrice = pPrice;
        this.mID = mID;
        this.cID = cID;
        this.pWarrantyPeriod = pWarrantyPeriod;
        this.pAvaliableQuantity = pAvaliableQuantity;
    }

    public void insertTodb (Connection conn){
        try {
            PreparedStatement statement = 
                conn.prepareStatement("INSERT INTO PART (pID, pName, pPrice, mID, cID, pWarrantyPeriod, pAvaliableQuantity) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, Integer.toString(pID));
            statement.setString(2, pName);
            statement.setString(3, Integer.toString(pPrice));
            statement.setString(4, Integer.toString(mID));
            statement.setString(5, Integer.toString(cID));
            statement.setString(6, Integer.toString(pWarrantyPeriod));
            statement.setString(7, Integer.toString(pAvaliableQuantity));
            statement.execute();
        } catch (SQLException e) {
            System.out.println("[Error] " + e);
        }
    }
}
