package com.nyxaria.apps.Controller;

import com.nyxaria.apps.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHandler {

    private Main main;
    private Connection conn;
    private String table;

    private String[] columnNames;
    private HashMap<String, String>[] cache;

    public DatabaseHandler(String databaseUri, String user, String pass, String table, Main main) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();

        conn = DriverManager.getConnection(databaseUri, user, pass);
        this.table = table;
        this.main = main;

        getData();
    }


    public HashMap<String, String>[] getData() {
        if (cache != null) return cache;

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        ArrayList<String> columns = new ArrayList<String>();

        HashMap<String, String> map = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + table);

            while (rs.next()) {
                map = new HashMap<String, String>();
                map.put("name", rs.getString("name"));
                map.put("region", rs.getString("region"));
                map.put("department", rs.getString("department"));
                map.put("age", rs.getString("age"));
                map.put("sex", rs.getString("sex"));

                data.add(map);
            }

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                columns.add(rsmd.getColumnName(i));
            }

            columnNames = columns.toArray(new String[0]);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return (cache = data.toArray(new HashMap[0]));
    }

    public String[] getColumnNames() {
        return columnNames;
    }

}
