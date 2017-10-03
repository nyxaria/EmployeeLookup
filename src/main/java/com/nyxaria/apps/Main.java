package com.nyxaria.apps;

import com.nyxaria.apps.Controller.DatabaseHandler;
import com.nyxaria.apps.Model.DataHandler;
import com.nyxaria.apps.View.DataFrame;

import java.sql.SQLException;
import java.util.HashMap;

/*
 * Program created for the function of looking up employees
 * by specifying department, region and/or name.
 *
 * Mainly created to learn SQL, MYSQL and MVC.
 * Create by nyxaria on 1st October.
 */

public class Main {

    public DataHandler dataHandler; // M
    public DataFrame dataFrame;   // V
    public DatabaseHandler dbHandler;   // C

    String databaseUrl = "jdbc:mysql://localhost:3306/employees";
    String databaseUser = "root";
    String databasePassword = "temp_pass";
    String databaseTable = "company"; //arbitrary company name


    public Main() {
        try {
            dbHandler = new DatabaseHandler(databaseUrl, databaseUser, databasePassword, databaseTable, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dataHandler = new DataHandler(this);

        dataFrame = new DataFrame(this);
    }

    public static void main(String[] args) {
        new Main();
    }
}
