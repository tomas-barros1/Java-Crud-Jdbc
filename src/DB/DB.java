package DB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    static Connection conn = null;

    public static Properties getPropriedades() {
        if(conn == null) {
            try(FileInputStream fis = new FileInputStream("db.config")) {
                Properties props = new Properties();
                props.load(fis);
                return props;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static Connection getConexao() {
        if (conn == null) {
            try {
                Properties props = getPropriedades();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return conn;
    }

    public static void fecharConexao() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
