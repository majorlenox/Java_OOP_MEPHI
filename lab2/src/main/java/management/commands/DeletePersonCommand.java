package management.commands;

import java.io.BufferedReader;
import java.io.IOException;

public class DeletePersonCommand extends Command {

    private final int id;

    public DeletePersonCommand(BufferedReader reader) throws Exception {
        commandSpecifier = "DP";
        id = Integer.parseInt(reader.readLine().split(" ")[1]);
        if (id <= 0) {throw new Exception("Incorrect id");}
    }

    public int getId() {
        return id;
    }

}
