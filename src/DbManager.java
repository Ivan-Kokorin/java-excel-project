import java.sql.*;
import java.util.HashMap;

public class DbManager {
    private final String driverName = "com.mysql.cj.jdbc.Driver";
    Configuration configuration = new Configuration("config.xml");
    public final String url = configuration.getUrlDb();
    private final String login = configuration.getLoginDb();
    private final String password = configuration.getPasswordDb();
    private static DbManager dbManager;
    private static HashMap<String, String> dbObject = new HashMap<>();

    private DbManager() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.err.println("Драйвер для работы с базой данных отсутствует");
            e.printStackTrace();
        }
    }

    public static DbManager getDbManager() {
        if (dbManager == null) {
            dbManager = new DbManager();
            return dbManager;
        } else {
            return dbManager;
        }
    }

    public String findUrl(String currentProduct) {
        String urlProduct = null;
        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statementFind = connection.prepareStatement("SELECT `url` FROM `f_products` WHERE `name` = ?;");
            statementFind.setString(1, currentProduct);
            ResultSet resultSet = statementFind.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString(1) != null) {
                    urlProduct = resultSet.getString(1);
                }
                ;
            }
        } catch (SQLException e) {
            System.out.println("ОШИБКА работы с базой данных");
            e.printStackTrace();
        }
        return urlProduct;
    }

    public HashMap<String, String> createDbObject() {
        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statementFind = connection.prepareStatement("SELECT `url`,`name` FROM `f_products`;");
            ResultSet resultSet = statementFind.executeQuery();
            while (resultSet.next()) {
                String url = resultSet.getString(1);
                String name = resultSet.getString(2);
                dbObject.put(name, url);
            }

        } catch (SQLException e) {
            System.out.println("ОШИБКА работы с базой данных");
            e.printStackTrace();
        }
        return dbObject;
    }
}
