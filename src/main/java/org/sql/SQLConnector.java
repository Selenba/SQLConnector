package org.sql;

import java.sql.*;

/***********************************************************************************************************
 Requires mysql-connector
 Use code below to import it with Maven or import it manually
 <dependency>
 <groupId>mysql</groupId>
 <artifactId>mysql-connector-java</artifactId>
 <version>8.0.27</version>
 </dependency>
 ***********************************************************************************************************/

public class SQLConnector {

    private Connection c = null;
    private String database;
    private String user;
    private String password;

    public SQLConnector(String database, String user, String password) {
        //This constructor will verify that the logs are correct and save them for later use.
        try {
            c = DriverManager.getConnection(database, user, password);
            this.database = database;
            this.user = user;
            this.password = password;
            c.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Connector creation failed. \nPlease verify that the database is running and that the connection logs are correct.");
        }
    }

    private ResultSet connectToGetData(String sqlRequest) throws SQLException {
        //Fetches data from database
        if (c != null) {
            c.close();
        }

        try {
            c = DriverManager.getConnection(this.database, this.user, this.password);
            Statement st = c.createStatement();
            ResultSet res = st.executeQuery(sqlRequest);
            return res;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Get data request failed. Please verify your sql syntax.");
            System.out.println("Failed request : " + sqlRequest);
            return null;
        }
    }

    private boolean connectToEdit(String sqlRequest) throws SQLException {
        //Updates the database
        if (c != null) {
            c.close();
        }

        try {
            c = DriverManager.getConnection(this.database, this.user, this.password);
            Statement st = c.createStatement();
            st.executeUpdate(sqlRequest);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Update request failed. Please verify your sql syntax.");
            System.out.println("Failed request : " + sqlRequest);
            return false;
        }
    }

    public boolean update(String sqlRequest) throws SQLException {
        return connectToEdit(sqlRequest);
    }

    public ResultSet get(String sqlRequest) throws SQLException {
        //returns a ResultSet based on request
        return connectToGetData(sqlRequest);
    }
}