import java.io.IOException;

public class Main {
    public static void main(String[] args) {

       // Controller mainController = new Controller("./ManagementFolder/");
        DialogCommandMaker dcm = new DialogCommandMaker("./target/ManagementFolder/");
        int c = 1;
        while (c != 0){
            System.out.println("Current path: " + dcm.getPathToManagementFolder());
            c = dcm.showMenuAndChoose(DialogCommandMaker.availableCommands);
            switch (c){
                default:
                    break;
                case 1:
                    dcm.enterAndSetNewPath();
                    break;
                case 2:
                    try {
                        dcm.endCommand();
                    }catch (IOException ioe){
                        System.out.println(ioe.getMessage());
                    }
                    break;
                case 3:
                    int c3 = dcm.showMenuAndChoose(DialogCommandMaker.createCommands);
                    switch (c3){
                        default:
                            break;
                        case 1:
                            try {
                                dcm.createStudent();
                            }catch (IOException ioe){
                                System.out.println(ioe.getMessage());
                            }
                            break;
                        case 2:
                            try {
                                dcm.createTeacher();
                            }catch (IOException ioe){
                                System.out.println(ioe.getMessage());
                            }
                            break;
                    }
                    break;
                case 4:
                    int c4 = dcm.showMenuAndChoose(DialogCommandMaker.modifyCommands);
                    switch(c4){
                        default:
                            break;
                        case 1:
                            try {
                                dcm.modifyStudent(dcm.inputId());
                            }catch (IOException ioe){
                                System.out.println(ioe.getMessage());
                            }
                            break;
                        case 2:
                            try {
                                dcm.modifyTeacher(dcm.inputId());
                            }catch (IOException ioe){
                                System.out.println(ioe.getMessage());
                            }
                            break;
                    }
                    break;
                case 5:
                    try {
                        dcm.deletePerson(dcm.inputId());
                    } catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    }
            }

        }

    }
}
