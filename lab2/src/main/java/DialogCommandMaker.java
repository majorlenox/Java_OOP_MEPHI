import Persons.Person;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DialogCommandMaker {

    String pathToManagementFolder;
    static public String[] availableCommands = {"DialogCommandMaker:\nChoose which command you want to create",
            "Quit from DialogCommandMaker\n---Commands---------------", "Set new pathToManagementFolder", "End",
            "Create Person", "Modify Person", "Delete Person"};
    static public String[] createCommands = {"Create Person:","Quit", "Create Student", "Create Teacher"};
    static public String[] modifyCommands = {"Modify Person:", "Quit", "Modify Student", "Modify Teacher"};
    static public String[] modifyStudentCommands = {"Modify Student-----------------\nChoose what you want to change:",
            "Quit/Save file", "Full name", "Year of birth", "Telephone number", "remove Subjects", "add/modify Grades"};
    static public String[] modifyTeacherCommands = {"Modify Teacher-----------------\nChoose what you want to change:",
            "Quit/Save file", "Full name", "Year of birth", "Telephone number", "Subject", "Working hours"};

    public DialogCommandMaker(String pathToManagementFolder) {
        this.pathToManagementFolder = pathToManagementFolder;
    }

    public int showMenuAndChoose(String[] commands) {
        showCommands(commands);
        return chooseAction(commands.length - 2);
    }

    public void enterAndSetNewPath() {
        System.out.println("Enter new path to ManagementFolder");
        Scanner sc = new Scanner(System.in);
        String pathToManagementFolder = sc.nextLine();
        Path path = Paths.get(pathToManagementFolder);
        if (!Files.exists(path)) {
            System.out.println("This folder doesn't exist! Create? Y/N");
            String ans = sc.nextLine();
            if ((ans.equals("Y")) || (ans.equals("y"))) {
                if (!(new File(pathToManagementFolder).mkdirs())) {
                    System.out.println("Can't create new folder");
                } else {
                    System.out.println("The folder was created successfully");
                    this.pathToManagementFolder = pathToManagementFolder;
                    System.out.println("The path was successfully changed");
                }
            }
        } else {
            this.pathToManagementFolder = pathToManagementFolder;
            System.out.println("The path was successfully changed");
        }
    }

    public void createStudent() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the student's full name");
        String fullName = sc.nextLine();
        System.out.println("Enter the student's year of birth");
        int yearOfBirth = 0;
        while (true) {
            try {
                yearOfBirth = sc.nextInt();
                break;
            } catch (InputMismatchException ime) {
                System.out.println("Incorrect year! Please follow example: 1998");
                sc.nextLine();
            }
        }
        sc.nextLine();
        System.out.println("Enter the student's phone number");
        String telephoneNumber = sc.nextLine();
        HashSet<Person.Subjects> subjects = new HashSet<>();
        while (subjects.isEmpty()) {
            System.out.println("Enter which subjects the student is studying(in one line)");
            System.out.println("Available subjects:");
            for (Person.Subjects sub : Person.Subjects.values()) {
                System.out.print(sub.name() + " ");
            }
            System.out.println();
            String inputLine = sc.nextLine();
            inputLine = inputLine.toUpperCase();
            for (Person.Subjects sub : Person.Subjects.values()) {
                if (inputLine.contains(sub.name())) {
                    subjects.add(sub);
                }
            }
            if (subjects.isEmpty()) {
                System.out.println("No one subject has been added. Try again");
            }
        }
        System.out.println("Enter the average grades for the study Subjects, format = \"ALGEBRA 4,6\"");
        HashMap<Person.Subjects, Double> grades = new HashMap<>();
        for (Person.Subjects sub : subjects) {
            System.out.print(sub.name() + " ");
            while (true) {
                try {
                    grades.put(sub, sc.nextDouble());
                    break;
                } catch (InputMismatchException ime) {
                    System.out.println("Incorrect grade! Please follow format, example = 3,7");
                    sc.nextLine();
                }
            }

        }
        sc.nextLine();

        // make command
        FileWriter out;
        Path pathToFolder = Paths.get(pathToManagementFolder);
        if (!Files.exists(pathToFolder)) {
            System.out.println("Directory: " + pathToManagementFolder + "doesn't exist");
            return;
        }
        int i = 1;
        while ((new File(pathToManagementFolder + "CS_" + i + ".txt").exists()) && (i < 10000)) {
            i++;
        }
        if (i == 10000) {
            System.out.println("Too many CS commands in folder!");
            return;
        }
        out = new FileWriter(pathToManagementFolder + "CS_" + i + ".txt");
        out.write("CS\n" + fullName + '\n' + yearOfBirth + "\n" + telephoneNumber + '\n');
        for (Person.Subjects sub : grades.keySet()) {
            out.write(sub.name() + ' ' + grades.get(sub) + '\n');
        }
        out.close();
        System.out.println("Command saved in " + pathToManagementFolder + "CS_" + i + ".txt");
    }

    public void createTeacher() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the teacher's full name");
        String fullName = sc.nextLine();
        System.out.println("Enter the teacher's year of birth");
        int yearOfBirth = 0;
        while (true) {
            try {
                yearOfBirth = sc.nextInt();
                sc.nextLine();
                break;
            } catch (InputMismatchException ime) {
                System.out.println("Incorrect year! Please follow example: 1998");
                sc.nextLine();
            }
        }
        System.out.println("Enter the teacher's phone number");
        String telephoneNumber = sc.nextLine();
        System.out.print("Enter which subject the teacher teaches\nAvailable subjects: ");
        for (Person.Subjects sub : Person.Subjects.values()) {
            System.out.print(sub.name() + " ");
        }
        System.out.println();

        String subject;
        while (true) {
            subject = sc.nextLine();
            try {
                Person.Subjects.valueOf(subject.toUpperCase());
                break;
            } catch (IllegalArgumentException iae) {
                System.out.println("There was an error in entering the subject, check if there is such an subject " +
                        "and enter it again");
            }
        }

        System.out.println("Enter the teacher's working hours, example 8:10 - 17:05");
        String workingHours = sc.nextLine();

        // make command
        FileWriter out;
        Path pathToFolder = Paths.get(pathToManagementFolder);
        if (!Files.exists(pathToFolder)) {
            System.out.println("Directory: " + pathToManagementFolder + "doesn't exist");
            return;
        }
        int i = 1;
        while ((new File(pathToManagementFolder + "CT_" + i + ".txt").exists()) && (i < 10000)) {
            i++;
        }
        if (i == 10000) {
            System.out.println("Too many CT commands in folder!");
            return;
        }
        out = new FileWriter(pathToManagementFolder + "CT_" + i + ".txt");
        out.write("CT\n" + fullName + '\n' + yearOfBirth + "\n" + telephoneNumber + '\n' + subject.toUpperCase() +
                '\n' + workingHours);
        out.close();
        System.out.println("Command saved in " + pathToManagementFolder + "CT_" + i + ".txt");


    }

    public void endCommand() throws IOException{
        FileWriter out;
        Path pathToFolder = Paths.get(pathToManagementFolder);
        if (!Files.exists(pathToFolder)) {
            System.out.println("Directory: " + pathToManagementFolder + "doesn't exist");
            return;
        }
        int i = 1;
        while ((new File(pathToManagementFolder + "EN_" + i + ".txt").exists()) && (i < 10000)) {
            i++;
        }
        if (i == 10000) {
            System.out.println("Too many EN commands in folder!");
            return;
        }
        out = new FileWriter(pathToManagementFolder + "EN_" + i + ".txt");
        out.write("EN\n");
        out.close();
        System.out.println("Command saved in " + pathToManagementFolder + "EN_" + i + ".txt");
    }

    public String getPathToManagementFolder() {
        return pathToManagementFolder;
    }

    public void modifyStudent(int id) throws IOException{

        String fullName = null;
        int yearOfBirth = 0;
        String telephoneNumber = null;
        HashSet<Person.Subjects> subjectsToRemove = null;
        HashMap<Person.Subjects, Double> grades = null;
        Scanner sc = new Scanner(System.in);

        while (true) {
            int c = showMenuAndChoose(modifyStudentCommands);

            switch (c) {
                case 0:
                    if ((fullName!=null)||(yearOfBirth!=0)||(telephoneNumber!=null)||(subjectsToRemove!=null) ||
                        (grades!=null)){
                        saveMSFile(id, fullName, yearOfBirth, telephoneNumber, subjectsToRemove, grades);
                    }else{System.out.println("File wasn't created");}
                    return;
                case 1:
                    System.out.println("Enter new full name");
                    fullName = sc.nextLine();
                    System.out.println("New full name ready to be saved in a file");
                    break;
                case 2:
                    System.out.println("Input new year of birth");
                    while (true) {
                        try {
                            yearOfBirth = sc.nextInt();
                            break;
                        } catch (InputMismatchException ime) {
                            System.out.println("Incorrect year! Please follow example: 1998");
                            sc.nextLine();
                        }
                    }
                    sc.nextLine();
                    System.out.println("New year of birth ready to be saved in a file");
                    break;
                case 3:
                    System.out.println("Input new telephone number");
                    telephoneNumber = sc.nextLine();
                    System.out.println("New telephone number ready to be saved in a file");
                    break;
                case 4:
                    subjectsToRemove = new HashSet<>();
                    while (subjectsToRemove.isEmpty()) {
                        System.out.println("Enter which subjects you want to remove from the student(in one line)");
                        System.out.println("Available subjects:");
                        for (Person.Subjects sub : Person.Subjects.values()) {
                            System.out.print(sub.name() + " ");
                        }
                        System.out.println();
                        String inputLine = sc.nextLine();
                        inputLine = inputLine.toUpperCase();
                        for (Person.Subjects sub : Person.Subjects.values()) {
                            if (inputLine.contains(sub.name())) {
                                subjectsToRemove.add(sub);
                            }
                        }
                        if (subjectsToRemove.isEmpty()) {
                            System.out.println("No one subject has been added to remove list. Do you want to enter the subjects again? Y/N");
                            String ans = sc.nextLine();
                            if ((ans.equals("N")) || (ans.equals("n"))) {
                                subjectsToRemove = null;
                                break;
                            }
                        }
                    }


                    System.out.println("Remove list of subjects ready to be saved in a file");
                    break;
                case 5:
                    HashSet<Person.Subjects> subjects = new HashSet<>();
                    while (subjects.isEmpty()) {
                        System.out.println("Enter the names of the subjects(in one line) for which you want to add or change grades");
                        System.out.println("Available subjects:");
                        for (Person.Subjects sub : Person.Subjects.values()) {
                            System.out.print(sub.name() + " ");
                        }
                        System.out.println();
                        String inputLine = sc.nextLine();
                        inputLine = inputLine.toUpperCase();
                        for (Person.Subjects sub : Person.Subjects.values()) {
                            if (inputLine.contains(sub.name())) {
                                subjects.add(sub);
                            }
                        }
                        if (subjects.isEmpty()) {
                            System.out.println("No one subject has been added. Do you want to enter the subjects again? Y/N");
                            String ans = sc.nextLine();
                            if ((ans.equals("N")) || (ans.equals("n"))) {
                                break;
                            }
                        }
                    }
                    System.out.println("Enter the average grades for the study Subjects, format = \"ALGEBRA 4,6\"");
                    grades = new HashMap<>();
                    for (Person.Subjects sub : subjects) {
                        System.out.print(sub.name() + " ");
                        while (true) {
                            try {
                                grades.put(sub, sc.nextDouble());
                                break;
                            } catch (InputMismatchException ime) {
                                System.out.println("Incorrect grade! Please follow format, example = 3,7");
                                sc.nextLine();
                            }
                        }

                    }
                    sc.nextLine();
                    System.out.println("New grades ready to be saved in a file");
            }
        }

    }

    public void modifyTeacher(int id) throws IOException {

        String fullName = null;
        int yearOfBirth = 0;
        String telephoneNumber = null;
        Person.Subjects subject = null;
        String workingHours = null;
        Scanner sc = new Scanner(System.in);

        while (true) {
            int c = showMenuAndChoose(modifyTeacherCommands);

            switch (c) {
                case 0:
                    if ((fullName != null) || (yearOfBirth != 0) || (telephoneNumber != null) || (subject != null) ||
                            (workingHours != null)) {
                        saveMTFile(id, fullName, yearOfBirth, telephoneNumber, subject, workingHours);
                    }else{System.out.println("File wasn't created");}
                    return;
                case 1:
                    System.out.println("Enter new full name");
                    fullName = sc.nextLine();
                    System.out.println("New full name ready to be saved in a file");
                    break;
                case 2:
                    System.out.println("Input new year of birth");
                    while (true) {
                        try {
                            yearOfBirth = sc.nextInt();
                            break;
                        } catch (InputMismatchException ime) {
                            System.out.println("Incorrect year! Please follow example: 1998");
                            sc.nextLine();
                        }
                    }
                    sc.nextLine();
                    System.out.println("New year of birth ready to be saved in a file");
                    break;
                case 3:
                    System.out.println("Input new telephone number");
                    telephoneNumber = sc.nextLine();
                    System.out.println("New telephone number ready to be saved in a file");
                    break;
                case 4:
                    System.out.print("Enter a new subject that the teacher teaches\nAvailable subjects: ");
                    for (Person.Subjects sub : Person.Subjects.values()) {
                        System.out.print(sub.name() + " ");
                    }
                    System.out.println();
                    String sub;
                    while (true) {
                        sub = sc.nextLine();
                        try {
                            Person.Subjects.valueOf(sub.toUpperCase());
                            break;
                        } catch (IllegalArgumentException iae) {
                            System.out.println("There was an error in entering the subject. Do you want to enter a new subject again? Y/N");
                            String ans = sc.nextLine();
                            if ((ans.equals("N")) || (ans.equals("n"))) {
                                sub = null;
                                break;
                            }else{System.out.println("Enter new subject:");}
                        }
                    }
                    if (sub == null) { break; }
                    subject = Person.Subjects.valueOf(sub.toUpperCase());
                    System.out.println("New subject ready to be saved in a file");
                    break;
                case 5:
                    System.out.println("Enter the teacher's working hours, example 8:10 - 17:05");
                    workingHours = sc.nextLine();
                    System.out.println("New working hours ready to be saved in a file");
            }
        }

    }

    public void deletePerson(int id) throws IOException{

        FileWriter out;
        Path pathToFolder = Paths.get(pathToManagementFolder);
        if (!Files.exists(pathToFolder)) {
            System.out.println("Directory: " + pathToManagementFolder + "doesn't exist");
            return;
        }
        int i = 1;
        while ((new File(pathToManagementFolder + "DP_" + i + ".txt").exists()) && (i < 10000)) {
            i++;
        }
        if (i == 10000) {
            System.out.println("Too many DP commands in folder!");
            return;
        }
        out = new FileWriter(pathToManagementFolder + "DP_" + i + ".txt");
        out.write("DP\nID " + id + "\n");
        out.close();
        System.out.println("Command saved in " + pathToManagementFolder + "DP_" + i + ".txt");
    }

    public int inputId(){
        System.out.println("Input person ID");
        Scanner sc = new Scanner(System.in);
        int id = -1;
        while (true){
            try{
                id = sc.nextInt();
                sc.nextLine();
                break;
            }catch(InputMismatchException ime){
                System.out.println();
                sc.nextLine();
            }
        }
        return id;
    }

    private void saveMSFile(int id, String fullName, int yearOfBirth, String telephoneNumber,
                            HashSet<Person.Subjects> subjectsToRemove, HashMap<Person.Subjects,
            Double> grades) throws IOException {

        FileWriter out;
        Path pathToFolder = Paths.get(pathToManagementFolder);
        if (!Files.exists(pathToFolder)) {
            System.out.println("Directory: " + pathToManagementFolder + "doesn't exist");
            return;
        }
        int i = 1;
        while ((new File(pathToManagementFolder + "MS_" + i + ".txt").exists()) && (i < 10000)) {
            i++;
        }
        if (i == 10000) {
            System.out.println("Too many MS commands in folder!");
            return;
        }
        out = new FileWriter(pathToManagementFolder + "MS_" + i + ".txt");
        out.write("MS\nID " + id + "\n");
        if (fullName!=null){
            out.write("FN " + fullName + "\n");
        }
        if (yearOfBirth!=0){
            out.write("YB " + yearOfBirth + "\n");
        }
        if (telephoneNumber!=null){
            out.write("TN " + telephoneNumber + "\n");
        }
        if (subjectsToRemove!=null){
            out.write("RS");
            for (Person.Subjects sub: subjectsToRemove){
                out.write(" " + sub.name());
            }
            out.write("\n");
        }
        if (grades!=null){
            out.write("GS\n");
            for (Person.Subjects sub : grades.keySet()){
             out.write(sub.name() + " " + grades.get(sub) + "\n");
            }
        }
        out.close();

        System.out.println("Command saved in " + pathToManagementFolder + "MS_" + i + ".txt");
    }

    private void saveMTFile(int id, String fullName, int yearOfBirth, String telephoneNumber,
                            Person.Subjects subject, String workingHours ) throws IOException {

        FileWriter out;
        Path pathToFolder = Paths.get(pathToManagementFolder);
        if (!Files.exists(pathToFolder)) {
            System.out.println("Directory: " + pathToManagementFolder + "doesn't exist");
            return;
        }
        int i = 1;
        while ((new File(pathToManagementFolder + "MT_" + i + ".txt").exists()) && (i < 10000)) {
            i++;
        }
        if (i == 10000) {
            System.out.println("Too many MT commands in folder!");
            return;
        }
        out = new FileWriter(pathToManagementFolder + "MT_" + i + ".txt");
        out.write("MT\nID " + id + "\n");
        if (fullName!=null){
            out.write("FN " + fullName + "\n");
        }
        if (yearOfBirth!=0){
            out.write("YB " + yearOfBirth + "\n");
        }
        if (telephoneNumber!=null){
            out.write("TN " + telephoneNumber + "\n");
        }
        if (subject!=null){
            out.write("ST " + subject.name() + "\n");
        }
        if (workingHours!=null){
            out.write("WH " + workingHours);
        }
        out.close();

        System.out.println("Command saved in " + pathToManagementFolder + "MT_" + i + ".txt");
    }

    private static int chooseAction(int n) {
        Scanner sc = new Scanner(System.in);
        int c;
        while (true) {
            try {
                c = sc.nextInt();
                if ((c >= 0) && (c <= n)) {
                    break;
                }
                System.out.println("Incorrect number! Enter a number from 0 to " + n);
            } catch (InputMismatchException ime) {
                System.out.println("Incorrect format! Enter a number from 0 to " + n + " to choose action");
                sc.nextLine();
            }
        }
        return c;
    }

    private static void showCommands(String[] commands) {
        System.out.println(commands[0]);
        for (int i = 1; i < commands.length; i++) {
            System.out.println(i - 1 + ". " + commands[i]);
        }
    }

}
