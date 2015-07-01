package usertripinformation;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 29 , 2015 15:59
 */
public class User {
  public final int age;
  public final String name;
  public final String email;
  public final String egn;

  public User(String name, String egn, int age, String email) {
    this.age = age;
    this.name = name;
    this.email = email;
    this.egn = egn;
  }

  @Override
  public String toString() {
    return String.format("User [user_age=%s, user_name=%s, user_email=%s,  user_egn=%s]",
            age, name, email, egn);
  }

}
