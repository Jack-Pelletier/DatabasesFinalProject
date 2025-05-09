USE Italian;
DROP PROCEDURE IF EXISTS CancelReservation;
DELIMITER //

CREATE PROCEDURE CancelReservation(
    IN p_ReservationName VARCHAR(200),
    IN p_BanquetHall INT
)
BEGIN
    DELETE FROM Reservations 
    WHERE ReservationName = p_ReservationName
       OR BanquetHall = p_BanquetHall;
END //
DELIMITER ;
