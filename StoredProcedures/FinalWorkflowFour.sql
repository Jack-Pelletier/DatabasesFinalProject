
Use Italian;
DROP PROCEDURE IF EXISTS CancelOrderOrReservation
DELIMITER //
CREATE PROCEDURE CancelOrderOrReservation(
    IN p_RecordType VARCHAR(20),       -- 'reservation' or 'takeout'
    IN p_ReservationName VARCHAR(200)
)
BEGIN
    IF LOWER(p_RecordType) = 'reservation' THEN
        DELETE FROM Reservations WHERE ReservationName = p_Name;
    ELSEIF LOWER(p_RecordType) = 'takeout' THEN
        DELETE FROM TakeoutOrders WHERE OrderName = p_Name;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Invalid record type. Use ''reservation'' or ''takeout''.';
    END IF;
END //
DELIMITER ;

-- Call to cancel a reservation
CALL CancelOrderOrReservation(
    'reservation',           -- p_RecordType (use 'reservation')
    'Johnson Wedding'       -- p_Name (the reservation name to delete)
);

