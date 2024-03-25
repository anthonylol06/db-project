package models.file;
import java.sql.*;
import models.db.ManufacturerModel;

public class ManufacturerFileModel implements FileModelInterface{
    private int mID;
    private String mName;
    private String mAddress;
    private int mPhoneNumber;

    public void parseInput (String input) {
        String[] splitted = input.split("\t");
        this.mID = Integer.parseInt(splitted[0]);
        this.mName = splitted[1];
        this.mAddress = splitted[2];
        this.mPhoneNumber = Integer.parseInt(splitted[3]);
    }

    public void saveTodb(Connection conn) {
        ManufacturerModel model = new ManufacturerModel(mID, mName, mAddress, mPhoneNumber);
        model.insertTodb(conn);
    }
}
