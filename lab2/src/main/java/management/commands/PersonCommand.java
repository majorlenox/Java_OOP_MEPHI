package management.commands;

public abstract class PersonCommand extends Command {

    protected String fullName;
    protected int yearOfBirth;
    protected String telephoneNumber;

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

}
