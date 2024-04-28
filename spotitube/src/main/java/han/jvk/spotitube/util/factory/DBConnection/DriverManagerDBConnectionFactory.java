//package han.jvk.spotitube.util.factory.DBConnection;
//
//import jakarta.enterprise.context.ApplicationScoped;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
///**
// * The DriverManager based DAOFactory.
// */
//@ApplicationScoped
//class DriverManagerDBConnectionFactory extends DBConnectionFactory {
//    private String url;
//    private String username;
//    private String password;
//
//    DriverManagerDBConnectionFactory(String url, String username, String password) {
//        this.url = url;
//        this.username = username;
//        this.password = password;
//    }
//
//    @Override
//    public Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(url, username, password);
//    }
//}
