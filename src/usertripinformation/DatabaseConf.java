package usertripinformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 29 , 2015 15:57
 */
public class DatabaseConf {

  private Connection connection;

  String dburl = "jdbc:postgresql://localhost/user_travel_information";
  String userName = "postgres";
  String userPassword = "ivan";

  public Connection getConnection() throws SQLException, ClassNotFoundException {

    try {

      Class.forName("org.postgresql.Driver");

    } catch (ClassNotFoundException e) {

      System.out.println("Where is your PostgreSQL JDBC Driver? "
              + "Include in your library path!");
      e.printStackTrace();


    }

    System.out.println("PostgreSQL JDBC Driver Connected!");

    try {
      connection = DriverManager.getConnection(
              dburl, userName, userPassword);

    } catch (SQLException e) {
      e.printStackTrace();


    }

    if (connection != null) {
      System.out.println("You made it, take control your database now!");
    } else {
      System.out.println("Failed to make connection!");
    }
    return connection;
  }
}
