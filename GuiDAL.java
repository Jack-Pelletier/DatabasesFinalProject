import java.sql.*;

public class GuiDAL {
    private String databaseName;
    private String user;
    private String password;

    public GuiDAL(String databaseName, String user, String password) {
        this.databaseName = databaseName;
        this.user = user;
        this.password = password;
    }

    // Helper method to get MySQL connection
    private Connection getMySQLConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, user, password);
        } catch (SQLException exception) {
            System.out.println("Failed to connect to the database: " + exception.getMessage());
            return null;
        }
    }
    

    // General method to execute a stored procedure
    public boolean TryExecutingAStoredProcedure(String databaseName, String user, String password, String storedProcedureName, Object... params) {
        Connection myConnection = getMySQLConnection();
        if (myConnection == null) {
            System.out.println("Failed to get a connection, cannot execute stored procedure");
            return false;
        }
        try {
            // Dynamically construct the query with placeholders for parameters
            StringBuilder queryBuilder = new StringBuilder("{Call ");
            queryBuilder.append(storedProcedureName);
            if (params.length > 0) {
                queryBuilder.append("(");
                for (int i = 0; i < params.length; i++) {
                    queryBuilder.append(i == 0 ? "?" : ",?");
                }
                queryBuilder.append(")");
            }
            queryBuilder.append("}");

            String query = queryBuilder.toString();
            CallableStatement myStoredProcedureCall = myConnection.prepareCall(query);

            // Set input parameters dynamically
            for (int i = 0; i < params.length; i++) {
                myStoredProcedureCall.setObject(i + 1, params[i]);
            }

            // Execute the stored procedure
            boolean hasResultSet = myStoredProcedureCall.execute();

            // Handle the results if there is a ResultSet
            if (hasResultSet) {
                ResultSet myResults = myStoredProcedureCall.getResultSet();
                while (myResults.next()) {
                    System.out.println("Result: " + myResults.getString(1));
                }
            } else {
                System.out.println("Stored procedure executed successfully, no result set returned.");
            }
        } catch (SQLException exception) {
            System.out.println("Failed to execute stored procedure: " + exception.getMessage());
            return false;
        }
        return true;
    }

    public ResultSet GetAllFoodItems() {
        try {
            Connection conn = DriverManager.getConnection(databaseName, user, password);
            CallableStatement stmt = conn.prepareCall("{CALL GetAllFoodItems()}");
            return stmt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    
    // Add a takeout order
    public boolean AddTakeoutOrder(String databaseName, String user, String password,
                                   String orderName, String entree, String appetizer,
                                   String side, String drink, Timestamp orderDate) {
        return TryExecutingAStoredProcedure(databaseName, user, password, "AddTakeoutOrder",
                                            orderName, entree, appetizer, side, drink, orderDate);
    }

    // Add a reservation (with success flag OUT parameter)
    public boolean AddReservation(String databaseName, String user, String password,
                                  String reservationName, int banquetHall, int partySize,
                                  int steakMeals, int salmonMeals, int chickenMeals,
                                  int pastaMeals, Timestamp reservationDate) {
        Connection conn = getMySQLConnection();
        if (conn == null) return false;

        try {
            CallableStatement stmt = conn.prepareCall("{CALL AddReservation(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            stmt.setString(1, reservationName);
            stmt.setInt(2, banquetHall);
            stmt.setInt(3, partySize);
            stmt.setInt(4, steakMeals);
            stmt.setInt(5, salmonMeals);
            stmt.setInt(6, chickenMeals);
            stmt.setInt(7, pastaMeals);
            stmt.setTimestamp(8, reservationDate);
            stmt.registerOutParameter(9, java.sql.Types.BOOLEAN); // Output parameter

            stmt.execute();
            return stmt.getBoolean(9); // Return success flag
        } catch (SQLException ex) {
            System.out.println("Error adding reservation: " + ex.getMessage());
            return false;
        }
    }

    // Cancel a reservation or takeout order
    public boolean CancelOrderOrReservation(String databaseName, String user, String password,
                                            String type, String name) {
        return TryExecutingAStoredProcedure(databaseName, user, password, "CancelOrderOrReservation",
                                            type, name);
    }

}