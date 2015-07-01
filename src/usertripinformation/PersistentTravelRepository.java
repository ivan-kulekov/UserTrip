package usertripinformation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 29 , 2015 16:01
 */
public class PersistentTravelRepository {

  private DatabaseConf database;

  public PersistentTravelRepository() {
    try {
      database = new DatabaseConf();
      database.getConnection();
    } catch (SQLException exc) {
      exc.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Add new travel to the each user and execute the query.
   *
   * @param user     is the user witch set the travel.
   * @param userTrip is the trip on the user.
   */
  public void addUserTravel(User user, Trip userTrip) throws SQLException {

    PreparedStatement statementAddUserTravel = null;
    PreparedStatement statementAddTripInformation = null;
    try {

      statementAddUserTravel = database.getConnection().prepareStatement("INSERT INTO users values(?, ?, ?, ?)");

      statementAddUserTravel.setInt(1, user.age);
      statementAddUserTravel.setString(2, user.name);
      statementAddUserTravel.setString(3, user.email);
      statementAddUserTravel.setString(4, user.egn);

      statementAddUserTravel.executeUpdate();

      statementAddTripInformation = database.getConnection().prepareStatement("INSERT INTO trip(egn, date_of_arrived, departure_date, city) values(?, ?, ?, ?)");

      statementAddTripInformation.setString(1, userTrip.egn);
      statementAddTripInformation.setDate(2, userTrip.dateOfArrived);
      statementAddTripInformation.setDate(3, userTrip.departureDate);
      statementAddTripInformation.setString(4, userTrip.city);

      statementAddTripInformation.executeUpdate();
    } catch (SQLException exc) {
      exc.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (statementAddTripInformation != null) {
        statementAddTripInformation.close();
      }
      if (statementAddUserTravel != null) {
        statementAddUserTravel.close();
      }
    }
  }

  /**
   * Update User information where user egn is the entered egn.
   *
   * @param user is the user who is updated.
   * @throws SQLException
   */
  public void updateUser(User user) throws SQLException {
    PreparedStatement statementUpdate = null;

    try {
      statementUpdate = database.getConnection().prepareStatement("UPDATE users SET user_age = ?, user_name = ?, user_email = ? WHERE user_egn = ? ");

      statementUpdate.setInt(1, user.age);
      statementUpdate.setString(2, user.name);
      statementUpdate.setString(3, user.email);
      statementUpdate.setString(4, user.egn);


      statementUpdate.executeUpdate();
    } catch (SQLException exc) {
      exc.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      try {
        if (statementUpdate != null) {
          statementUpdate.close();
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }

  }

  /**
   * Get all entered users in to the database.
   *
   * @return the list of users.
   * @throws SQLException
   */
  public List<User> getUsers() throws SQLException {
    List<User> listOfUsers = new ArrayList<User>();

    PreparedStatement statementGetUsers = null;
    ResultSet resultSet = null;

    try {
      statementGetUsers = database.getConnection().prepareStatement("SELECT * FROM users");

      resultSet = statementGetUsers.executeQuery();

      while (resultSet.next()) {
        User tmpUser = convertRowToUser(resultSet);
        listOfUsers.add(tmpUser);
      }
      return listOfUsers;

    } catch (SQLException exc) {
      exc.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (statementGetUsers != null) {
        statementGetUsers.close();
      }
    }
    return null;
  }

  /**
   * Get all trips in database.
   *
   * @return the trips which are in to a database.
   */
  public List<Trip> getUserTrips() {
    List<Trip> listOfTrips = new ArrayList<Trip>();

    PreparedStatement statementGetUserTrips = null;
    ResultSet resultSet = null;
    try {
      statementGetUserTrips = database.getConnection().prepareStatement("SELECT * FROM trip");

      resultSet = statementGetUserTrips.executeQuery();

      while (resultSet.next()) {
        Trip tempTrips = convertRowToTrip(resultSet);
        listOfTrips.add(tempTrips);
      }
      return listOfTrips;
    } catch (SQLException exc) {
      exc.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }


  /**
   * Make the User and returned back.
   *
   * @param result is the resulted value.
   * @return the newest user.
   * @throws SQLException
   */
  private User convertRowToUser(ResultSet result) throws SQLException {

    String userName = result.getString("user_name");
    String userEgn = result.getString("user_egn");
    int userAge = result.getInt("user_age");
    String userEmail = result.getString("user_email");

    User tmpUser = new User(userName, userEgn, userAge, userEmail);

    return tmpUser;

  }

  /**
   * Make the Trip and returned back.
   *
   * @param result is the resulted value.
   * @return the newest user.
   * @throws SQLException
   */
  private Trip convertRowToTrip(ResultSet result) throws SQLException {

    String userEgn = result.getString("egn");
    String dateOfArrived = result.getString("date_of_arrived");
    String departureDate = result.getString("departure_date");
    String city = result.getString("city");

    Trip tmpTrip = new Trip(userEgn, dateOfArrived, departureDate, city);

    return tmpTrip;

  }

}
