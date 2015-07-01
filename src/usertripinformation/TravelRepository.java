package usertripinformation;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 29 , 2015 15:59
 */
public interface TravelRepository {

  void addUserTravel(User user, Trip userTrip) throws SQLException;

  List<User> getUsers() throws SQLException;

  public List<Trip> getUserTrips();

}
