package persons;

public abstract class Person {

  private String fullName;
  private int yearOfBirth;
  private String telephoneNumber;
  private long id = 0; // gets the Id only after writing to the file (or if Person readed from file)
  public Person() {}

  public Person(String fullName, int yearOfBirth, String telephoneNumber) {
    this.fullName = fullName;
    this.yearOfBirth = yearOfBirth;
    this.telephoneNumber = telephoneNumber;
  }

  public String getTelephoneNumber() {
    return telephoneNumber;
  }

  public void setTelephoneNumber(String telephoneNumber) {
    this.telephoneNumber = telephoneNumber;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public int getYearOfBirth() {
    return yearOfBirth;
  }

  public void setYearOfBirth(int yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
  }

  public long getID() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public enum Subjects {
    ALGEBRA,
    BIOLOGY,
    PAINTING,
    CHEMISTRY,
    GEOGRAPHY,
    GEOMETRY,
    HISTORY,
    LITERATURE,
    MATHEMATICS,
    MUSIC,
    PHYSICAL_EDUCATION,
    PHYSICS,
    TECHNOLOGY
  }
}
