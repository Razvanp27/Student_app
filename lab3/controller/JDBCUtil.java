package lab3.controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class JDBCUtil {
    private String className = "org.postgresql.Driver";
    private String url;
    private String user ;
    private String password;
    private String dataBaseName;
    public JDBCUtil() {getPropertyValues();}

    public Connection getConnection() {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load the class");
            System.exit(-1);
        }

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to the database!");
                return conn;
            } else {
                System.out.println("Failed to make connection!");
                System.exit(-1);
            }
        } catch (SQLException e) {
            System.err.format("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getPropertyValues() {
        try{
            Properties properties = new Properties();
            /////////////////////////////////////////////////// verifica path-ul
            String propFileName = "/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/controller/application.properties";
            FileInputStream fis = new FileInputStream("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/controller/application.properties");
            if(fis != null){
                properties.load(fis);
            }else{
                throw new FileNotFoundException("properties files '" + propFileName + "' not found");
            }
            //get the properties values and print it out
            className = properties.getProperty("className");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            dataBaseName = properties.getProperty("dataBaseName");
            url = properties.getProperty("url");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    ;
}
