package Management;

import java.util.AbstractQueue;

public class Controller extends Thread{

    String pathToManagementFolder;
    AbstractQueue<String> commandsForPeopleService;

    @Override
    public void run(){

    }
    public Controller(String pathToManagementFolder){
        this.pathToManagementFolder = pathToManagementFolder;
    }

    public void updateAndAdd(){

    }

}

