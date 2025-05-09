USE Italian;
DROP PROCEDURE IF EXISTS AddTakeoutOrder;

DELIMITER //
CREATE PROCEDURE AddTakeoutOrder(
    IN p_OrderName VARCHAR(200),
    IN p_Entree VARCHAR(100),
    IN p_Appetizer VARCHAR(100),
    IN p_Side VARCHAR(100),
    IN p_Drink VARCHAR(100)
)
BEGIN
    DECLARE v_EntreeCost INT DEFAULT 0;
    DECLARE v_AppetizerCost INT DEFAULT 0;
    DECLARE v_SideCost INT DEFAULT 0;
    DECLARE v_DrinkCost INT DEFAULT 0;
    DECLARE v_TotalCost INT;

    -- Sum up total cost
    SET v_TotalCost = v_EntreeCost + v_AppetizerCost + v_SideCost + v_DrinkCost;

    -- Insert the order
    INSERT INTO TakeoutOrders (
        OrderName, Entree, Appetizer, Side, Drink
    )
    VALUES (
        p_OrderName, v_TotalCost, p_Entree, p_Appetizer, p_Side, p_Drink
    );
END //

DELIMITER ;

CALL AddTakeoutOrder(
    'Jane Smith Order',
    'Filet Mignon',              
    'Bruschetta Pomodoro',     
    'Garlic Mashed Potatoes',   
    'Lemonade'                 
);
