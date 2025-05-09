USE Italian;
DROP PROCEDURE IF EXISTS CancelReservation;
DELIMITER //

CREATE PROCEDURE CancelReservation(
    IN p_ReservationName VARCHAR(200)
)
BEGIN
    DELETE FROM Reservations 
    WHERE ReservationName = p_ReservationName;
END //
DELIMITER ;
