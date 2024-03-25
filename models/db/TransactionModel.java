package models.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import models.DateConvert;

public class TransactionModel {
    private int tID;
    private int pID;
    private int sID;
    private Calendar tDate;

    public TransactionModel (int tID, int pID, int sID, Calendar tDate){
        this.tID = tID;
        this.pID = pID;
        this.sID = sID;
        this.tDate = tDate;
    }

    public void insertTodb (Connection conn){
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO TRANSACTION (tID, pID, sID, tDate) VALUES (?, ?, ?, ?)");
            statement.setString(1, Integer.toString(tID));
            statement.setString(2, Integer.toString(pID));
            statement.setString(3, Integer.toString(sID));
            statement.setString(4, DateConvert.calToStr(tDate));
            statement.execute();
        } catch (SQLException e) {
            System.out.println("[Error] " + e);
        }
    }
}
