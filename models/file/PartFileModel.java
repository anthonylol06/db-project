package models.file;
import java.sql.*;
import models.db.PartModel;

public class PartFileModel implements FileModelInterface{
    private int pID;
    private String pName;
    private int pPrice;
    private int mID;
    private int cID;
    private int pWarrantyPeriod;
    private int pAvaliableQuantity;

    public void parseInput (String input){
        String[] splitted = input.split("\t");
        this.pID = Integer.parseInt(splitted[0]);
        this.pName = splitted[1];
        this.pPrice = Integer.parseInt(splitted[2]);
        this.mID = Integer.parseInt(splitted[3]);
        this.cID = Integer.parseInt(splitted[4]);
        this.pWarrantyPeriod = Integer.parseInt(splitted[5]);
        this.pAvaliableQuantity = Integer.parseInt(splitted[6]);
    }

    public void saveTodb(Connection conn) {
        PartModel model = new PartModel(pID, pName, pPrice, mID, cID, pWarrantyPeriod, pAvaliableQuantity);
        model.insertTodb(conn);
    }
}
