package models.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryModel{
    private int cID;
    private String cName;

    public CategoryModel (int cID, String cName){
        this.cID = cID;
        this.cName = cName;
    }

    public void insertTodb (Connection conn){
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO CATEGORY (cID, cName) VALUES (?, ?)");
            statement.setString(1, Integer.toString(cID));
            statement.setString(2, cName);
            // System.out.println(statement);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("[Error] " + e);
        }
    }
}