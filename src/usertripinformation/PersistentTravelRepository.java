package usertripinformation;

import com.sun.javaws.progress.PreloaderPostEventListener;

import java.sql.Date;
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
  public List<Trip> getUserTrips() throws SQLException {
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
    } finally {
      if (statementGetUserTrips != null) {
        statementGetUserTrips.close();
      }
      if (resultSet != null) {
        resultSet.close();
      }
    }
    return null;
  }


  /**
   * Find user by EGN.
   *
   * @param egn is the egn on the user to find.
   * @return fended user.
   * @throws SQLException
   */
  public User findUser(String egn) throws SQLException {
    PreparedStatement statementFindUser = null;
    ResultSet resultSet = null;
    try {
      statementFindUser = database.getConnection().prepareStatement("SELECT * FROM users WHERE user_egn = ?");

      statementFindUser.setString(1, egn);
      resultSet = statementFindUser.executeQuery();

      if (resultSet.next()) {
        User tempUser = convertRowToUser(resultSet);
        return tempUser;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (statementFindUser != null) {
        statementFindUser.close();
      }
      if (resultSet != null) {
        resultSet.close();
      }
    }
    return null;
  }

  /**
   * Find User Trips by city.
   *
   * @param city is the fended city.
   * @return list of trips which are in database.
   * @throws SQLException
   */
  public List<Trip> findTrip(String city) throws SQLException {

    List<Trip> listOfTrips = new ArrayList<Trip>();
    PreparedStatement statementFindTrip = null;
    ResultSet resultSet = null;

    try {
      statementFindTrip = database.getConnection().prepareStatement("SELECT * FROM trip WHERE city = ? ");

      statementFindTrip.setString(1, city);

      resultSet = statementFindTrip.executeQuery();

      while (resultSet.next()) {
        Trip tempTrip = convertRowToTrip(resultSet);
        listOfTrips.add(tempTrip);
      }
      return listOfTrips;
    } catch (SQLException exc) {
      exc.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (statementFindTrip != null) {
        statementFindTrip.close();
      }
      if (resultSet != null) {
        resultSet.close();
      }
    }
    return null;
  }

  /**
   * Find the User which are in the some city on same time.
   *
   * @param date    is the start date from where to start searching.
   * @param dateTwo is the last date , the date to stop searching.
   * @param trip    is the entered trip.
   * @return the list of users which are in the city.
   * @throws SQLException
   */
  public List<User> findUserInSameCity(Date date, Date dateTwo, Trip trip) throws SQLException {

    List<User> listOfUserInSomeCity = new ArrayList<User>();
    PreparedStatement statementFindSomeCity = null;
    ResultSet resultSet = null;

    try {
      statementFindSomeCity = database.getConnection().prepareStatement("SELECT *  FROM users INNER JOIN trip ON users.user_egn = trip.egn WHERE not trip.date_of_arrived > ? and not trip.departure_date < ? and trip.city = ? ORDER BY user_name");

      statementFindSomeCity.setDate(1, dateTwo);
      statementFindSomeCity.setDate(2, date);
      statementFindSomeCity.setString(3, trip.city);

      resultSet = statementFindSomeCity.executeQuery();

      while (resultSet.next()) {
        User tempUser = convertRowToUser(resultSet);
        listOfUserInSomeCity.add(tempUser);
      }
      return listOfUserInSomeCity;
    } catch (SQLException exc) {
      exc.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (statementFindSomeCity != null) {
        statementFindSomeCity.close();
      }
      if (resultSet != null) {
        resultSet.close();
      }
    }
    return null;
  }

  /**
   * Find the user by enter some characters.
   *
   * @param character is the character which enter to find the person.
   * @return list of users where find in to a database.
   * @throws SQLException
   */
  public List<User> findUserByEnterSomeCharacter(String character) throws SQLException {

    List<User> listOfUsers = new ArrayList<User>();
    PreparedStatement statementFindByChar = null;
    ResultSet resultSet = null;

    try {
      statementFindByChar = database.getConnection().prepareCall("SELECT * FROM users WHERE user_name LIKE '" + character + "%'");

      resultSet = statementFindByChar.executeQuery();

      while (resultSet.next()) {
        User tempUser = convertRowToUser(resultSet);
        listOfUsers.add(tempUser);
      }
      return listOfUsers;

    } catch (SQLException exc) {
      exc.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (statementFindByChar != null) {
        statementFindByChar.close();
      }
      if (resultSet != null) {
        resultSet.close();
      }
    }
    return null;
  }

  /**
   * Drop table from database.
   *
   * @param tableName is the name of the table which is to drop from database.
   * @throws SQLException
   */
  public void deleteTableFromDatabase(String tableName) throws SQLException {
    PreparedStatement statementDeleteTable = null;

    try {
      statementDeleteTable = database.getConnection().prepareStatement(String.format("DROP table %s", tableName));
      statementDeleteTable.executeUpdate();

    } catch (SQLException exc) {
      exc.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (statementDeleteTable != null) {
        statementDeleteTable.close();
      }
    }
  }

  /**
   * Get the count of visiting on the users in city.
   *
   * @return the list of users who are visit this city.
   * @throws SQLException
   */

  public List<String> getCountOfVisitedCities() throws SQLException {
    List<String> listOfVisited = new ArrayList<String>();
    PreparedStatement statementCountVisited = null;
    ResultSet resultSet = null;
    try {
      statementCountVisited = database.getConnection().prepareStatement("SELECT city FROM trip GROUP BY city ORDER BY COUNT(city) desc");

      resultSet = statementCountVisited.executeQuery();
      while (resultSet.next()) {
        String city = resultSet.getString("city");
        listOfVisited.add(city);
      }
      return listOfVisited;

    } catch (SQLException exc) {
      exc.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (statementCountVisited != null) {
        statementCountVisited.close();
      }
      if (resultSet != null) {
        resultSet.close();
      }
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
