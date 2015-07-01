package usertripinformation;

import org.junit.Before;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 29 , 2015 16:04
 */
public class PersistentTravelRepositoryTest {

  private DatabaseConf database;
  private PersistentTravelRepository repository;

  @Before
  public void setUp() {
    database = new DatabaseConf();
    repository = new PersistentTravelRepository();
  }


  @Test
  public void addNewUserInformationToDatabase() throws SQLException {

    clearTableDate();
    User user = new User("Ivan", "2328936511", 22, "ivan@abv.bg");

    Trip trip = new Trip("2328936511", "2015-04-26", "2015-04-27", "Veliko Turnovo");
    repository.addUserTravel(user, trip);

  }


  @Test
  public void updateUserToDatabase() throws SQLException {
    clearTableDate();
    User ivan = new User("Ivan", "1134312124", 23, "ivan@abv.bg");
    Trip trip = new Trip("1134312124", "2015-04-26", "2015-04-27", "Veliko Turnovo");
    repository.addUserTravel(ivan , trip);
    User gosho = new User("Gosho", "1234312124", 24, "gosho@abv.bg");

    repository.updateUser(gosho);
  }

  /**
   * Clean information from tables , but not delete them.
   *
   * @throws SQLException
   */
  public void clearTableDate() throws SQLException {

    PreparedStatement statementClearUsers = null;
    PreparedStatement statementClearTrips = null;
    try {
      statementClearUsers = database.getConnection().prepareStatement("DELETE FROM users");
      statementClearTrips = database.getConnection().prepareStatement("DELETE FROM trip");
      statementClearUsers.executeUpdate();
      statementClearTrips.executeUpdate();

    } catch (SQLException exc) {
      exc.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (statementClearTrips != null) {
        statementClearTrips.close();
      }
      if (statementClearUsers != null) {
        statementClearUsers.close();
      }
    }
  }


}
