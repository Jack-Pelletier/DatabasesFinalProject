import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GuiDALTestDuplicate {

    private static GuiDAL dal;

    @BeforeClass
    public static void Setup() {
        dal = new GuiDAL("Italian", "root", "WPR92bxv#2020"); // Adjust to your actual DB name and credentials
    }

    @AfterClass
    public static void Clean() {
        System.out.println("Cleanup complete. No test data removed since operations are idempotent.");
    }

    @Test
    public void testAddTakeoutOrderTwice() {
        boolean first = dal.AddTakeoutOrder("restaurantdb", "root", "password",
                "TestOrder1", "Lasagna", "Bruschetta", "Garlic Bread", "Wine");
        boolean second = dal.AddTakeoutOrder("restaurantdb", "root", "password",
                "TestOrder1", "Lasagna", "Bruschetta", "Garlic Bread", "Wine");
        assertTrue("Expected repeated takeout order insertion to succeed", first && second);
    }

    @Test
    public void testAddReservationTwice() {
        boolean first = dal.AddReservation("restaurantdb", "root", "password",
                "TestRes1", 1, 10, 2, 2, 3, 3, "2025-12-25");
        boolean second = dal.AddReservation("restaurantdb", "root", "password",
                "TestRes1", 1, 10, 2, 2, 3, 3, "2025-12-25");
        assertTrue("Expected repeated reservation to succeed", first && second);
    }

    @Test
    public void testCancelTakeoutOrderTwice() {
        boolean first = dal.CancelTakeoutOrder("restaurantdb", "root", "password", "TestOrder1");
        boolean second = dal.CancelTakeoutOrder("restaurantdb", "root", "password", "TestOrder1");
        assertTrue("Expected repeated takeout order cancellation to succeed", first && second);
    }

    @Test
    public void testCancelReservationTwice() {
        boolean first = dal.CancelReservation("restaurantdb", "root", "password", "TestRes1");
        boolean second = dal.CancelReservation("restaurantdb", "root", "password", "TestRes1");
        assertTrue("Expected repeated reservation cancellation to succeed", first && second);
    }

    @Test
    public void testGetFoodItemsByCategoryRepeatedly() {
        try {
            boolean success = dal.getFoodItemByCategory("Entree").size() >= 0; // Can be empty but should not fail
            boolean success2 = dal.getFoodItemByCategory("Entree").size() >= 0;
            assertTrue("Expected repeated food category query to succeed", success && success2);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("Exception occurred during repeated category query", false);
        }
    }
}
