package models.file;
import java.sql.*;
import models.db.SalespersonModel;

public class SalespersonFileModel implements FileModelInterface{
    private int sID;
    private String sName;
    private String sAddress;
    private int sPhoneNumber;
    private int sExperience;

    public void parseInput(String input){
        String[] splitted = input.split("\t");
        this.sID = Integer.parseInt(splitted[0]);
        this.sName = splitted[1];
        this.sAddress = splitted[2];
        this.sPhoneNumber = Integer.parseInt(splitted[3]);
        this.sExperience = Integer.parseInt(splitted[4]);
    }

    public void saveTodb(Connection conn) {
        SalespersonModel model = new SalespersonModel(sID, sName, sAddress, sPhoneNumber, sExperience);
        model.insertTodb(conn);
    }

}
