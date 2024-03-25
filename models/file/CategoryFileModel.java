package models.file;
import java.sql.*;
import models.db.CategoryModel;

public class CategoryFileModel implements FileModelInterface{
    private int cID;
    private String cName;

    public void parseInput (String input){
        String[] splitted = input.split("\t");
        this.cID = Integer.parseInt(splitted[0]);
        this.cName = splitted[1]; 
    }

    public void saveTodb(Connection conn) {
        CategoryModel model = new CategoryModel(cID, cName);
        model.insertTodb(conn);
    }
}
