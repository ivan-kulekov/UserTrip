package usertripinformation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 29 , 2015 15:59
 */
public interface TravelRepository {

  void addUserTravel(User user, Trip userTrip) throws SQLException;

  List<User> getUsers() throws SQLException;

  List<Trip> getUserTrips();

  User findUser(String egn);

  List<Trip> findTrip(String city);

  List<User> findUserInSameCity(Date date, Date dateTwo, Trip trip);

  List<User> findUserByEnterSomeCharacter(String character);

  void deleteTableFromDatabase(String tableName);

  List<String> getCountOfVisitedCities();

}
