package root.dao.mysql.impl.helper;

import org.apache.ibatis.jdbc.ScriptRunner;
import root.dao.mysql.util.ResourceManager;
import root.model.Entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static root.dao.mysql.util.ResourceManager.DB;


public class DBRestorer {

    public static void restoreAll(Connection connection) {

        Statement stmt = null;
        try {
           ScriptRunner sr = new ScriptRunner(connection);
           Reader reader = new BufferedReader(
                    new FileReader("D:\\project\\by\\epam\\8.3.DAO\\tests\\source\\insert_script.sql"));
           sr.runScript(reader);

        } catch (Exception e) {
            System.err.println("Failed to Execute"
                    + " The error is " + e.getMessage());
        }
    }

    public static void truncateAll(Connection connection) throws SQLException {
        List<Class> modelClasses = ReflectionHelper.findAllSublasses(Entity.class);
        Statement statement = connection.createStatement();
        for (Class t : modelClasses){
            String deleteQuery = "DELETE FROM ";
            String simple = t.getSimpleName();
            String tableName =  ResourceManager.STRUCTURE.getString("clazz." + simple);
            String fullName = DB.getString("database.name") + "." + tableName;
            deleteQuery = deleteQuery + tableName;
            if (!simple.equals("Role")) {
                statement.executeUpdate(deleteQuery);
            }
            connection.commit();
        }

    }


}

