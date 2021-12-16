package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1337";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";


    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }
        int c = Menu.showAndChooseOption(Menu.ENTRY_MESSAGE);
        switch (c){
            case 0:
                return;
            case 1:
                // SpringData JPA
                break;
            case 2:
                // Spring JDBC
                break;
        }

        c = Menu.showAndChooseOption(Menu.MAIN_MESSAGE);
        switch (c){
            case 0:
                return;
            case 1:
                // Create Person
                break;
            case 2:
                // Show Persons
                break;
            case 3:
                // Update Person
                break;
            case 4:
                // Delete Person
                break;
        }

    }
}
