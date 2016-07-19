package root.dao.mysql.impl.helper;

import org.apache.ibatis.jdbc.ScriptRunner;
import root.dao.mysql.util.ResourceManager;
import root.model.Entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static root.connection_pool.util.DBParameters.*;
import static root.connection_pool.util.ResourceManager.DATABASE;
import static root.dao.mysql.util.ResourceManager.DB;


public class DBRestorer {

    public static void restoreAll(Connection connection) throws SQLException {
        System.out.println("1");
        String url = DATABASE.getString(DB_URL);
        String user =  DATABASE.getString(DB_USER);
        String password =  DATABASE.getString(DB_PASSWORD);
        Connection connection1 = DriverManager.getConnection(url, user, password);

        Statement stmt = null;
        ScriptRunner sr = new ScriptRunner(connection1);
        try {
           sr.setLogWriter(null);
           Reader reader = new BufferedReader(
                    new FileReader("D:\\project\\by\\epam\\8.3.DAO\\tests\\source\\insert_script.sql"));
            sr.runScript(reader);
        } catch (Exception e) {
            System.err.println("Failed to Execute"
                    + " The error is ");
        } finally {
            connection1.close();
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

    public static void main(String[] args) throws SQLException {

        String url = DATABASE.getString(DB_URL);
        String user =  DATABASE.getString(DB_USER);
        String password =  DATABASE.getString(DB_PASSWORD);
        Connection connection1 = DriverManager.getConnection(url, user, password);
        connection1.setAutoCommit(false);
        try{
            truncateAll(connection1);
        } finally {
            connection1.close();
        }

    }
}

