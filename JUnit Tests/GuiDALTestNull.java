import static org.junit.Assert.assertFalse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GuiDALTestNull {

    private static GuiDAL dal;

    @BeforeClass
    public static void Setup() {
        dal = new GuiDAL("Italian", "root", "WPR92bxv#2020"); // Replace with your actual DB credentials
    }

    @AfterClass
    public static void Clean() {
        System.out.println("Cleanup: Null input tests complete.");
    }

    @Test
    public void testTryExecutingAStoredProcedureWithNulls() {
        boolean result = dal.TryExecutingAStoredProcedure(null, null, null, null);
        assertFalse("Expected failure when all inputs are null", result);
    }

    @Test
    public void testAddTakeoutOrderWithNulls() {
        boolean result = dal.AddTakeoutOrder(null, null, null, null, null, null, null, null);
        assertFalse("Expected failure when all inputs to AddTakeoutOrder are null", result);
    }

    @Test
    public void testAddReservationWithNulls() {
        boolean result = dal.AddReservation(null, null, null, null, 0, 0, 0, 0, 0, 0, null);
        assertFalse("Expected failure when all credentials and reservation name/date are null", result);
    }

    @Test
    public void testCancelTakeoutOrderWithNulls() {
        boolean result = dal.CancelTakeoutOrder(null, null, null, null);
        assertFalse("Expected failure when all CancelTakeoutOrder inputs are null", result);
    }

    @Test
    public void testCancelReservationWithNulls() {
        boolean result = dal.CancelReservation(null, null, null, null);
        assertFalse("Expected failure when all CancelReservation inputs are null", result);
    }
}
