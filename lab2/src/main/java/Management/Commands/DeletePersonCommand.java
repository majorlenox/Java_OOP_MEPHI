package Management.Commands;

import java.io.BufferedReader;
import java.io.IOException;

public class DeletePersonCommand extends Command {

    private int id;

    public DeletePersonCommand(BufferedReader reader) throws IOException {
        commandSpecifier = "DP";
        id = Integer.parseInt(reader.readLine().split(" ")[1]);
    }

    public int getId() {
        return id;
    }

    public void doCommand(){

    }

}