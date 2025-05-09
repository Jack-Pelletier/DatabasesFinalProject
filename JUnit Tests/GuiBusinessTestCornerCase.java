import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GuiBusinessTestCornerCase {

    private static GuiDAL dal;

    @BeforeClass
    public static void setup() {
        dal = new GuiDAL("Italian", "root", "WPR92bxv#2020"); // Adjust database credentials as needed
    }

    @Test
    public void testAddReservationValid() {
        String name = "John Doe";
        int hall = 1;
        int size = 10;
        int steakMeals = 2;
        int salmonMeals = 1;
        int chickenMeals = 3;
        int pastaMeals = 4;
        String time = "2025-06-15 18";

        boolean result = dal.AddReservation("Italian", "root", "Flint0711##", name, hall, size, steakMeals, salmonMeals, chickenMeals, pastaMeals, time);
        assertTrue("Should successfully add reservation with valid data", result);
    }

    @Test
    public void testAddReservationInvalidName() {
        String name = ""; // Invalid name
        int hall = 1;
        int size = 10;
        int steakMeals = 2;
        int salmonMeals = 1;
        int chickenMeals = 3;
        int pastaMeals = 4;
        String time = "2025-06-15 18";

        boolean result = dal.AddReservation("Italian", "root", "Flint0711##", name, hall, size, steakMeals, salmonMeals, chickenMeals, pastaMeals, time);
        assertFalse("Should fail when name is empty", result);
    }

    @Test
    public void testCancelReservationValid() {
        String reservationName = "John Doe";

        boolean result = dal.CancelReservation("Italian", "root", "Flint0711##", reservationName);
        assertTrue("Should successfully cancel reservation", result);
    }

    @Test
    public void testCancelReservationInvalidName() {
        String reservationName = ""; // Invalid name

        boolean result = dal.CancelReservation("Italian", "root", "Flint0711##", reservationName);
        assertFalse("Should fail when reservation name is empty", result);
    }

    @Test
    public void testAddTakeoutOrderValid() {
        String orderName = "Jane Doe";
        String entree = "Steak";
        String appetizer = "Salad";
        String side = "Fries";
        String drink = "Water";

        boolean result = dal.AddTakeoutOrder("Italian", "root", "WPR92bxv#2020", orderName, entree, appetizer, side, drink);
        assertTrue("Should successfully add takeout order with valid data", result);
    }

    @Test
    public void testAddTakeoutOrderInvalidData() {
        String orderName = ""; // Invalid order name
        String entree = "Steak";
        String appetizer = "Salad";
        String side = "Fries";
        String drink = "Water";

        boolean result = dal.AddTakeoutOrder("Italian", "root", "WPR92bxv#2020", orderName, entree, appetizer, side, drink);
        assertFalse("Should fail when order name is empty", result);
    }

    @Test
    public void testCancelTakeoutOrderValid() {
        String orderName = "Jane Doe";

        boolean result = dal.CancelTakeoutOrder("Italian", "root", "Flint0711##", orderName);
        assertTrue("Should successfully cancel takeout order", result);
    }

    @Test
    public void testCancelTakeoutOrderInvalidName() {
        String orderName = ""; // Invalid order name

        boolean result = dal.CancelTakeoutOrder("Italian", "root", "Flint0711##", orderName);
        assertFalse("Should fail when order name is empty", result);
    }

}
