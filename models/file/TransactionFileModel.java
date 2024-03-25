package models.file;
import java.sql.*;
import java.util.Calendar;
import models.DateConvert;
import models.db.TransactionModel;

public class TransactionFileModel implements FileModelInterface{
    private int tID;
    private int pID;
    private int sID;
    private Calendar tDate;

    public void parseInput (String input){
        String[] splitted = input.split("\t");
        this.tID = Integer.parseInt(splitted[0]);
        this.pID = Integer.parseInt(splitted[1]);
        this.sID = Integer.parseInt(splitted[2]);
        this.tDate = DateConvert.strToCal(splitted[3]);
    }

    public void saveTodb(Connection conn) {
        TransactionModel model = new TransactionModel(tID, pID, sID, tDate);
        model.insertTodb(conn);
    }
    
}
