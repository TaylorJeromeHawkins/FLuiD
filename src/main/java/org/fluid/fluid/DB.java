package org.fluid.fluid;

import java.sql.*;

public class DB {
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String USER = "root";
    static final String PASS = "";

    // A static variable to hold the connection so it persists between calls
    private static Connection conn;

    // Method to establish a connection and return the Connection object
    private static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        return conn;
    }

    public static String accessDB(String query) {
        StringBuilder result = new StringBuilder();

        try {
            // Get the persistent connection
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            // Trim and convert the query to uppercase for easier comparison
            String upperCaseQuery = query.trim().toUpperCase();

            // Check if it's a SELECT, SHOW, or USE query
            if (upperCaseQuery.startsWith("SELECT") || upperCaseQuery.startsWith("SHOW")) {
                // SELECT and SHOW queries return a ResultSet
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData metaData = rs.getMetaData();

                int columnCount = metaData.getColumnCount();
                int rowNumber = 1; // row counter

                // Loop through the ResultSet and append the results
                while (rs.next()) {
                    result.append(rowNumber).append("  ");
                    for (int i = 1; i <= columnCount; i++) {
                        result.append(rs.getString(i)).append("\t");
                    }
                    result.append("\n");
                    rowNumber++;
                }
            } else if (upperCaseQuery.startsWith("USE")) {
                // For the USE command, executeUpdate is needed to change the database
                stmt.executeUpdate(query);
                result.append("Database changed successfully.");
            } else {
                // Non-SELECT/SHOW/USE queries like INSERT, UPDATE, DELETE
                int affectedRows = stmt.executeUpdate(query);
                result.append(affectedRows).append(" rows affected.");
            }

        } catch (SQLException e) {
            // Append SQL error message to result to show in the output area
            result.append("SQL Error: ").append(e.getMessage());
        }

        return result.toString();
    }
}