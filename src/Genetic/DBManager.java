package Genetic;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//COSAS OBLIGATORIAS PARA QUE H2 FUNQUE. Busque en la documentacion y eso decia.... no me importa que hace cada metodo adentro.
public class DBManager {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_BASE_URL = "jdbc:h2:tcp://localhost//{DIR}";
    private static final String DB_NAME = "/proyecto";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";

    public static Connection connect() {
        Connection c = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        try {
            String url = "jdbc:h2:tcp://localhost//{DIR}/ejemplo";
            url = url.replace("{DIR}", getBaseDirectory());
            c = DriverManager.getConnection(url, DB_USERNAME, DB_PASSWORD);
            c.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return c;
    }

    private static String getBaseDirectory() {
        File currDir = new File("h2/data_base/");
        return currDir.getAbsolutePath();
    }

    public static String getBaseLocation() {
        String url = "jdbc:h2:tcp://localhost//db";
        //url = url.replace("{DIR}", getBaseDirectory());
        return url;
    }
}
