package models;

import java.sql.*;
import java.io.*;
import java.util.Calendar;
import models.file.*;

/**
 * Database
 */
public class Database {
    final String jdbcName = "com.mysql.cj.jdbc.Driver";
    final String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/dbX";
    final String dbUserName = "GroupX";
    final String dbPassword = "CSCI3170";

    final String[] tableNames = {"Category", "Manufacturer", "Part", "Salesperson", "Transaction"};

    private Connection conn = null;

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName(jdbcName);
        // DriverManager.registerDriver("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection(dbAddress, dbUserName, dbPassword);
    }

    /* Admin Operatiosn */
    public void createAllTables() throws SQLException {
        PreparedStatement[] statements = {
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS CATEGORY (cID INT(1) NOT NULL, cName CHAR(20) NOT NULL, PRIMARY KEY (cID))"),
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS MANUFACTURER (mID INT(2) NOT NULL, mName CHAR(20) NOT NULL, mAddress CHAR(50) NOT NULL, mPhoneNumber INT(8) NOT NULL, PRIMARY KEY (mID))"),
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS PART (pID INT(3) NOT NULL, pName CHAR(20) NOT NULL, pPrice INT(5) NOT NULL, mID INT(2) NOT NULL, cID INT(1) NOT NULL, pWarrantyPeriod INT(2) NOT NULL, pAvaliableQuantity INT(2) NOT NULL, PRIMARY KEY (pID), FOREIGN KEY (mID) REFERENCES MANUFACTURER (mID), FOREIGN KEY (cID) REFERENCES CATEGORY (cID))"), 
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS SALESPERSON (sID INT(2) NOT NULL, sName CHAR(20) NOT NULL, sAddress CHAR(50) NOT NULL, sPhoneNumber INT(8) NOT NULL, sExperience INT(1) NOT NULL, PRIMARY KEY (sID))"),
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS TRANSACTION (tID INT(4) NOT NULL, pID INT(3) NOT NULL, sID INT(2) NOT NULL, tDate DATE NOT NULL, PRIMARY KEY (tID), FOREIGN KEY (pID) REFERENCES PART (pID), FOREIGN KEY (sID) REFERENCES SALESPERSON (sID))")
        };
        for (int i = 0; i < statements.length; i++) {
            statements[i].execute();
            statements[i].close();
        }
    }

    public void deleteAllTables() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0");
        stmt.execute();
        for (int i = tableNames.length - 1; i >= 0; i--){
            stmt = conn.prepareStatement("DROP TABLE " + tableNames[i].toUpperCase());
            stmt.executeUpdate();
        }
        stmt = conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1");
        stmt.execute();
        stmt.close();
    }

    private void readToModelAndSaveToDB(String filePath, Class<?> type) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                FileModelInterface model = (FileModelInterface) type.newInstance();
                model.parseInput(line);
                model.saveTodb(conn);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("[Error] " + e);
        }
    }

    public void loadDataFromFiles(String folderPath) {
        readToModelAndSaveToDB(folderPath + "/category.txt", CategoryFileModel.class);
        readToModelAndSaveToDB(folderPath + "/manufacturer.txt", ManufacturerFileModel.class);
        readToModelAndSaveToDB(folderPath + "/part.txt", PartFileModel.class);
        readToModelAndSaveToDB(folderPath + "/salesperson.txt", SalespersonFileModel.class);
        readToModelAndSaveToDB(folderPath + "/transaction.txt", TransactionFileModel.class);
    }

    public void showTableContent(String tableName) throws SQLException {
        boolean is_read = false;
        for (String k : tableNames){
            if (k.toUpperCase().equals(tableName.toUpperCase())){
                is_read = true;
                try {
                    PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + k.toUpperCase());
                    ResultSet rs = statement.executeQuery();
                    System.out.println("Content of table " + tableName + ":");
                    printTable(rs);
                    rs.close();
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("[Error] Admin did not create the table " + tableName + ".");
                }
            }
        }
        if (!is_read){
            System.out.println("[Error] Table doesn't allow to exist in the database.");
        }
    }

    // For debugging: check if the tables exists
    // public void bruh(){
    //     try {
    //         PreparedStatement statement = conn.prepareStatement("SHOW TABLES");
    //         ResultSet rs = statement.executeQuery();
    //         while(rs.next()){
    //             System.out.println(rs.getString(1));
    //         }
    //     } catch (SQLException e) {
    //         System.out.println("[Error] Cannot read the table.");
    //     }
    // }

    /* Salesperson Operations */
    public void searchParts(int mode, String name, int sort_order) throws SQLException{
        try{
            String a, order;
            PreparedStatement stmt;
            ResultSet rs;
            if(mode == 1){
                a = "SELECT P.pID AS 'ID', P.pName AS 'Name', M.mName AS 'Manufacturer', C.cName AS 'Category', P.pAvaliableQuantity AS 'Quantity', P.pWarrantyPeriod AS 'Warranty', P.pPrice AS 'Price' FROM PART P, MANUFACTURER M, CATEGORY C WHERE P.pName LIKE '%" + name + "%' AND P.mID = M.mID AND P.cID = C.cID ORDER BY pPrice";
                order = sort_order == 1 ? a + " ASC" : a + " DESC";
                stmt = conn.prepareStatement(order);
                rs = stmt.executeQuery();
                printTable(rs);
                rs.close();
                stmt.close();
            } else if(mode == 2){
                a = "SELECT P.pID AS 'ID', P.pName AS 'Name', M.mName AS 'Manufacturer', C.cName AS 'Category', P.pAvaliableQuantity AS 'Quantity', P.pWarrantyPeriod AS 'Warranty', P.pPrice AS 'Price' FROM PART P, MANUFACTURER M, CATEGORY C WHERE M.mName LIKE '%" + name + "%' AND P.mID = M.mID AND P.cID = C.cID ORDER BY pPrice";
                order = sort_order == 1 ? a + " ASC" : a + " DESC";
                stmt = conn.prepareStatement(order);
                rs = stmt.executeQuery();
                printTable(rs);
                rs.close();
                stmt.close();
            }
        } catch(SQLException e){
            System.out.println("[Error]: Fail to make query.");
        }
    }

    public void makeTransactions(int part_id, int sp_id) throws SQLException{
        try{
            String order00 = "SELECT MAX(P.pID) FROM PART P";
            PreparedStatement stmt00 = conn.prepareStatement(order00);
            ResultSet rs00 = stmt00.executeQuery();
            rs00.next();
            int maxpID = Integer.parseInt(rs00.getString(1));
            rs00.close();
            stmt00.close();

            if(part_id < 1 || part_id > maxpID){
                System.out.println("[Error]: Invalid Part ID.");
                return;
            }

            String order01 = "SELECT MAX(S.sID) FROM SALESPERSON S";
            PreparedStatement stmt01 = conn.prepareStatement(order01);
            ResultSet rs01 = stmt01.executeQuery();
            rs01.next();
            int maxsID = Integer.parseInt(rs01.getString(1));
            rs01.close();
            stmt01.close();

            if(sp_id < 1 || sp_id > maxsID){
                System.out.println("[Error]: Invalid Salesperson ID.");
                return;
            }

            String order = "SELECT P.pName, P.pAvaliableQuantity FROM PART P WHERE P.pID = " + part_id;
            PreparedStatement stmt = conn.prepareStatement(order);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int remainQuan = Integer.parseInt(rs.getString(2));

            if(remainQuan <= 0){
                System.out.println("[Error]: Sold out.");
                rs.close();
                stmt.close();
            } else{
                String partName = rs.getString(1);
                rs.close();
                stmt.close();

                int newRemainQuan = remainQuan - 1;
                Statement stmt1 = conn.createStatement();
                stmt1.executeUpdate("UPDATE PART SET pAvaliableQuantity = " + newRemainQuan + " WHERE pID = " + part_id);

                Calendar calendar = Calendar.getInstance();
                String currentDateStr = DateConvert.calToStr(calendar);
                // System.out.println(currentDateStr);

                String order1 = "SELECT MAX(T.tID) FROM TRANSACTION T";
                PreparedStatement stmt2 = conn.prepareStatement(order1);
                ResultSet rs1 = stmt2.executeQuery();
                rs1.next();
                int newtID = Integer.parseInt(rs1.getString(1)) + 1;
                rs1.close();
                stmt2.close();

                String transaction = "INSERT INTO TRANSACTION (tID, pID, sID, tDate) VALUES ('" + newtID + "', '" + part_id + "', '" + sp_id + "', '" + currentDateStr + "');";
                PreparedStatement stmt3 = conn.prepareStatement(transaction);
                stmt3.executeUpdate();
                System.out.printf("Product: %s(id: %d) Remaining Quantity: %d\n", partName, part_id, newRemainQuan);
            }
        } catch(SQLException e){
            System.out.println("[Error]: Fail to make query.");
        }
    }

    /* Manager Operations */
    public void listAllSalesperson(int sort_order) throws SQLException {
        try{
            String a = "SELECT sID AS 'ID', sName AS 'Name', sPhoneNumber AS 'Mobile Phone', sExperience AS 'Years of Experience'"
                    + " FROM SALESPERSON ORDER BY sExperience ";
            String order = sort_order == 1 ? a + "ASC" : a + "DESC";
            PreparedStatement stmt = conn.prepareStatement(order);
            ResultSet rs = stmt.executeQuery();
            printTable(rs);
            rs.close();
            stmt.close();
        } catch (SQLException e){
            System.out.println("[Error]: Fail to make query.");
        }
    }

    public void transactionWithinNumOfExp(int lower_bound, int upper_bound) throws SQLException {
        try {
            String q = "SELECT S.sID AS 'ID', S.sName AS 'Name', S.sExperience AS 'Years of Experience', COUNT(S.sID) AS 'Number of Transaction' "
                    + "From SALESPERSON S LEFT JOIN TRANSACTION T ON S.sID = T.sID "
                    + "WHERE S.sExperience BETWEEN ? AND ? GROUP BY S.sID, S.sNAME, S.sExperience HAVING SUM(S.sID) >= 0 ORDER BY S.sID DESC";
            PreparedStatement stmt = conn.prepareStatement(q);
            stmt.setInt(1, lower_bound);
            stmt.setInt(2, upper_bound);
            ResultSet rs = stmt.executeQuery();
            printTable(rs);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("[Error]: Fail to make query.");
        }
    }

    public void showTotalSalesValueManufacturer() throws SQLException {
        try {
            String q = "SELECT M.mID AS 'Manufacturer ID', M.mName AS 'Manufacturer Name', SUM(P.pPrice) AS 'Total Sales Value' " 
                + "FROM MANUFACTURER M JOIN PART P ON M.mID = P.mID JOIN TRANSACTION T ON P.pID = T.pID " 
                + "GROUP BY M.mID, M.mName HAVING SUM(P.pPrice) >= 0 ORDER BY SUM(P.pPrice) DESC";
            PreparedStatement stmt = conn.prepareStatement(q);
            ResultSet rs = stmt.executeQuery();
            printTable(rs);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("[Error]: Fail to make query.");
        }
    }

    public void nMostPopularParts(int n) throws SQLException {
        try {
            String q = "SELECT P.pID AS 'Part ID', P.pName AS 'Part NAME', COUNT(T.tID) AS 'Total Number of Transaction' "
                    + "FROM PART P LEFT JOIN TRANSACTION T ON P.pID = T.pID "
                    + "GROUP BY P.pID, P.pName HAVING COUNT(T.tID) > 0 ORDER BY COUNT(T.tID) DESC LIMIT ?";
            PreparedStatement stmt = conn.prepareStatement(q);
            stmt.setInt(1, n);
            ResultSet rs = stmt.executeQuery();
            printTable(rs);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("[Error]: Fail to make query." + e);
        }
    }

    public void printTable (ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCnt = rsmd.getColumnCount();
        String columnTitle = "|";
        for (int i = 1 ; i <= columnCnt; i++){
            columnTitle = columnTitle + " " + rsmd.getColumnLabel(i) + " |";
        }
        System.out.println(columnTitle);
        while (rs.next()){
            System.out.printf("|");
            for (int i = 1; i <= columnCnt; i++){
                System.out.printf(" %s |", rs.getString(i));
            }
            System.out.printf("\n");
        }
    }

}

// CLI: java -classpath ./mysql-jdbc.jar:./ main
