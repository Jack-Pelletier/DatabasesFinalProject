import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public ResultSet GetAllFoodItems() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, user, password);
        if (conn == null) {
            System.out.println("Failed to get a connection, cannot execute stored procedure");
            return null;
        }
        try { 
            CallableStatement stmt = conn.prepareCall("{CALL GetAllFoodItems()}");
            ResultSet rs = stmt.executeQuery();
            System.out.println(rs);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getFoodItemByCategory(String category) throws SQLException {
    List<String> foodItems = new ArrayList<>();
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, user, password);

    if (conn == null) {
        System.out.println("Connection failed");
        return foodItems;
    }

    try {
        CallableStatement stmt = conn.prepareCall("{CALL GetFoodItemByCategory(?)}");
        stmt.setString(1, category);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            foodItems.add(rs.getString("FoodName"));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return foodItems;
}

    
    
    
    // Add a takeout order
    public boolean AddTakeoutOrder(String databaseName, String user, String password,
                                   String orderName, String entree, String appetizer,
                                   String side, String drink) {
        return TryExecutingAStoredProcedure(databaseName, user, password, "AddTakeoutOrder",
                                            orderName, entree, appetizer, side, drink);
    }

    // Add a reservation (with success flag OUT parameter)
    public boolean AddReservation(String databaseName, String user, String password,
                                  String reservationName, int banquetHall, int partySize,
                                  int steakMeals, int salmonMeals, int chickenMeals,
                                  int pastaMeals, String reservationDate) {
        return TryExecutingAStoredProcedure(databaseName, user, password, "AddReservation", reservationName, banquetHall, partySize, steakMeals, salmonMeals, chickenMeals, pastaMeals, reservationDate);
                                  
    }

    public ResultSet GetAllTakeoutOrders(String databaseName, String user, String password) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, user, password);
        if (conn == null) {
            System.out.println("Failed to get a connection, cannot execute stored procedure");
            return null;
        }
        try {
            CallableStatement stmt = conn.prepareCall("{CALL GetAllTakeoutOrders()}");
            return stmt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public ResultSet GetAllReservations() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, user, password);
        if (conn == null) {
            System.out.println("Connection failed. Cannot load reservations.");
            return null;
        }
    
        try {
            CallableStatement stmt = conn.prepareCall("{CALL GetAllReservations()}");
            return stmt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    

    public boolean CancelTakeoutOrder(String databaseName, String user, String password,
                                   String orderName) {
        return TryExecutingAStoredProcedure(databaseName, user, password, "CancelTakeoutOrder",
                                            orderName);
    }

    public boolean CancelReservation(String databaseName, String user, String password,
                                   String resName) {
        return TryExecutingAStoredProcedure(databaseName, user, password, "CancelReservation",
                                            resName);
    }

}