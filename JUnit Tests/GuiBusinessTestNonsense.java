import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class GuiBusinessTestNonsense {

    private static GuiDAL dal;

    @BeforeClass
    public static void setup() {
        dal = new GuiDAL("Italian", "user", "pass"); // Adjust credentials as needed
    }

    @Test
    public void testAddReservationWithNonsenseName() {
        String name = "nonsenseName123!@#"; // Nonsense name
        int hall = 1;
        int size = 10;
        int steakMeals = 2;
        int salmonMeals = 1;
        int chickenMeals = 3;
        int pastaMeals = 4;
        String time = "2025-06-15 18";

        boolean result = dal.AddReservation("Italian", "root", "Flint0711##", name, hall, size, steakMeals, salmonMeals, chickenMeals, pastaMeals, time);
        assertFalse("Should fail with nonsense reservation name", result);
    }

    @Test
    public void testAddTakeoutOrderWithNonsenseOrderName() {
        String orderName = "nonsenseOrderNameXYZ123!"; // Nonsense order name
        String entree = "Steak";
        String appetizer = "Salad";
        String side = "Fries";
        String drink = "Water";

        boolean result = dal.AddTakeoutOrder("Italian", "root", "WPR92bxv#2020", orderName, entree, appetizer, side, drink);
        assertFalse("Should fail with nonsense takeout order name", result);
    }

    @Test
    public void testCancelReservationWithNonsenseName() {
        String reservationName = "invalidName123456!"; // Nonsense reservation name

        boolean result = dal.CancelReservation("Italian", "root", "Flint0711##", reservationName);
        assertFalse("Should fail when canceling reservation with nonsense name", result);
    }

    @Test
    public void testCancelTakeoutOrderWithNonsenseOrderName() {
        String orderName = "invalidOrder12345!"; // Nonsense order name

        boolean result = dal.CancelTakeoutOrder("Italian", "root", "Flint0711##", orderName);
        assertFalse("Should fail when canceling takeout order with nonsense name", result);
    }

    
}
