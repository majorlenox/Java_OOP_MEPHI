package management.commands;

public abstract class Command {

  protected String commandSpecifier;

  public String getCommandSpecifier() {
    return commandSpecifier;
  }
}
