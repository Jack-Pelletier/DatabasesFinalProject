import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class GuiBusinessTestNull {

    private static GuiDAL dal;

    @BeforeClass
    public static void setup() {
        dal = new GuiDAL("Italian", "user", "pass"); // Adjust database credentials as needed
    }

    @Test
    public void testAddReservationWithNullName() {
        String name = null; // Null name
        int hall = 1;
        int size = 10;
        int steakMeals = 2;
        int salmonMeals = 1;
        int chickenMeals = 3;
        int pastaMeals = 4;
        String time = "2025-06-15 18";

        boolean result = dal.AddReservation("Italian", "root", "Flint0711##", name, hall, size, steakMeals, salmonMeals, chickenMeals, pastaMeals, time);
        assertFalse("Should fail when reservation name is null", result);
    }

    @Test
    public void testAddTakeoutOrderWithNullName() {
        String orderName = null; // Null order name
        String entree = "Steak";
        String appetizer = "Salad";
        String side = "Fries";
        String drink = "Water";

        boolean result = dal.AddTakeoutOrder("Italian", "root", "WPR92bxv#2020", orderName, entree, appetizer, side, drink);
        assertFalse("Should fail when takeout order name is null", result);
    }

    @Test
    public void testCancelReservationWithNullName() {
        String reservationName = null; // Null reservation name

        boolean result = dal.CancelReservation("Italian", "root", "Flint0711##", reservationName);
        assertFalse("Should fail when canceling reservation with null name", result);
    }

    @Test
    public void testCancelTakeoutOrderWithNullName() {
        String orderName = null; // Null order name

        boolean result = dal.CancelTakeoutOrder("Italian", "root", "Flint0711##", orderName);
        assertFalse("Should fail when canceling takeout order with null name", result);
    }
}
