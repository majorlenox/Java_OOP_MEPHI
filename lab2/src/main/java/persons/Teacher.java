package persons;

public class Teacher extends Person {

  private Subjects subject;
  private String workingHours;

  public Teacher() {}

  public Teacher(
      String fullName,
      int yearOfBirth,
      String telephoneNumber,
      Subjects subject,
      String workingHours) {
    super(fullName, yearOfBirth, telephoneNumber);
    this.subject = subject;
    this.workingHours = workingHours;
  }

  public Subjects getSubject() {
    return subject;
  }

  public void setSubject(Subjects subject) {
    this.subject = subject;
  }

  public String getWorkingHours() {
    return workingHours;
  }

  public void setWorkingHours(String workingHours) {
    this.workingHours = workingHours;
  }
}
