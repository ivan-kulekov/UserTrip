package usertripinformation;

import java.sql.SQLException;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 29 , 2015 16:21
 */
public class Demo {


  public static void main(String[] args) throws SQLException {
    PersistentTravelRepository repository = new PersistentTravelRepository();
//    User user = new User("Ivan", "2328936511", 22, "ivan@abv.bg");
//
//    Trip trip = new Trip("2328936511", "2015-04-26", "2015-04-27", "Veliko Turnovo");
//    repository.addUserTravel(user, trip);


    System.out.println(repository.findTrip("Veliko Turnovo"));
  }
}
