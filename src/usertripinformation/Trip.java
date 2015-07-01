package usertripinformation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 29 , 2015 16:00
 */
public class Trip {
  public final String egn;
  public final Date dateOfArrived;
  public final Date departureDate;
  public final String city;

  public Trip(String egn, String dateOfArrived, String departureDate, String city) {
    this.egn = egn;
    this.dateOfArrived = createDate(dateOfArrived);
    this.departureDate = createDate(departureDate);
    this.city = city;
  }

  private Date createDate(String date) {
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date dateFormat = null;

    try {
      dateFormat = formatter.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    if (dateFormat != null) {
      return new Date(dateFormat.getTime());
    }

    return null;
  }

  @Override
  public String toString() {
    return String.format("Trip [egn=%s , date_of_arrived=%s, departure_date=%s ,  city=%s]",
            egn, dateOfArrived, departureDate, city);
  }

}
