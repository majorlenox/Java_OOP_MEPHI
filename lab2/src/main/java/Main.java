import java.io.IOException;

public class Main {
    public static void main(String[] args) {

       // Controller mainController = new Controller("./ManagementFolder");
        DialogCommandMaker dcm = new DialogCommandMaker("./target/ManagementFolder");
        int c = 1;
        while (c != 0){
            c = dcm.showMenuAndChoose();
            switch (c){
                default:
                    break;
                case 1:
                    dcm.enterAndSetNewPath();
                    break;
                case 3:
                    try {
                        dcm.createStudent();
                    }catch (IOException ioe){
                        System.out.println(ioe.getMessage());
                    }
                    break;
                case 4:

                    break;
            }

        }

    }
}
