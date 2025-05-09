USE Italian;
DROP PROCEDURE IF EXISTS GetAllTakeoutOrders;

DELIMITER //
CREATE PROCEDURE GetAllTakeoutOrders()
BEGIN
    SELECT OrderName, Entree, Appetizer, Side, Drink
    FROM TakeoutOrders;
END //
DELIMITER ;