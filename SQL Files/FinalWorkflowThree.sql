USE Italian;
DROP PROCEDURE IF EXISTS AddReservation;

DELIMITER //
CREATE PROCEDURE AddReservation(
    IN p_ReservationName VARCHAR(200),
    IN p_BanquetHall INT,
    IN p_PartySize INT,
    IN p_SteakMeals INT,
    IN p_SalmonMeals INT,
    IN p_ChickenMeals INT,
    IN p_PastaMeals INT,
    IN p_ReservationDate DATETIME
)
BEGIN
    DECLARE v_SteakMealCost INT DEFAULT 0;
    DECLARE v_SalmonMealCost INT DEFAULT 0;
    DECLARE v_ChickenMealCost INT DEFAULT 0;
    DECLARE v_PastaMealCost INT DEFAULT 0;
    DECLARE v_TotalCost INT;  

    -- Calculate the total cost based on the number of meals
    SET v_TotalCost = (p_SteakMeals * v_SteakMealCost) + 
                      (p_SalmonMeals * v_SalmonMealCost) + 
                      (p_ChickenMeals * v_ChickenMealCost) + 
                      (p_PastaMeals * v_PastaMealCost);

    -- Insert the reservation into the Reservations table
    INSERT INTO Reservations (
        ReservationName, BanquetHall, PartySize, Cost, 
        SteakMeals, SalmonMeals, ChickenMeals, PastaMeals, ReservationDate
    )
    VALUES (
        p_ReservationName, p_BanquetHall, p_PartySize, v_TotalCost, 
        p_SteakMeals, p_SalmonMeals, p_ChickenMeals, p_PastaMeals, p_ReservationDate
    );
END //
DELIMITER ;