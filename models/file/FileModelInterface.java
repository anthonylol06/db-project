package models.file;
import java.sql.Connection;

public interface FileModelInterface {
    public void saveTodb(Connection conn);
    public void parseInput(String input);
}

