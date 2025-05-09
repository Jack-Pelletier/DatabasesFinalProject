USE Italian;
DROP PROCEDURE IF EXISTS GetAllReservations;

DELIMITER //
CREATE PROCEDURE GetAllReservations()
BEGIN
    SELECT ReservationName, BanquetHall, PartySize, Cost, SteakMeals, SalmonMeals, ChickenMeals, PastaMeals, ReservationDate
    FROM Reservations;
END //
DELIMITER ;

