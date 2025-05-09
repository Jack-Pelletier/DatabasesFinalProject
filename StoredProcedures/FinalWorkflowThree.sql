USE Italian;

DROP PROCEDURE IF EXISTS AddReservation;

DELIMITER //

CREATE PROCEDURE AddReservation(
    IN p_ReservationName VARCHAR(200),
    IN p_BanquetHall INT,               -- FIXED: Use INT to match foreign key
    IN p_PartySize INT,
    IN p_SteakMeals INT,
    IN p_SalmonMeals INT,
    IN p_ChickenMeals INT,
    IN p_PastaMeals INT,
    IN p_ReservationDate DATETIME,
    OUT p_Success BOOLEAN
)
BEGIN
    DECLARE v_TotalMeals INT;
    DECLARE v_Cost INT;

    -- Calculate total meals and validate
    SET v_TotalMeals = p_SteakMeals + p_SalmonMeals + p_ChickenMeals + p_PastaMeals;

    IF v_TotalMeals = p_PartySize THEN
        SET v_Cost = (p_SteakMeals * 36) + ;

        -- Insert into Reservations table
        INSERT INTO Reservations (
            ReservationName, BanquetHall, PartySize, Cost,
            SteakMeals, SalmonMeals, ChickenMeals, PastaMeals, ReservationDate
        ) VALUES (
            p_ReservationName, p_BanquetHall, p_PartySize, v_Cost,
            p_SteakMeals, p_SalmonMeals, p_ChickenMeals, p_PastaMeals, p_ReservationDate
        );

        SET p_Success = TRUE;
    ELSE
        SET p_Success = FALSE;
    END IF;
END //

DELIMITER ;

-- Declare a variable to capture the output (success/failure)
SET @success = FALSE;

-- Make sure BanquetHall = 1 exists in banquethalls before calling
CALL AddReservation(
    'Johnson Wedding',           
    1,                            
    100,
    v_Cost,
    30,                          
    20,                          
    25,                          
    25,                          
    NOW(),                       
    @success                     
);

-- Check the result
SELECT @success AS ReservationSuccess;
