DROP PROCEDURE IF EXISTS CancelTakeoutOrder;
DELIMITER //
CREATE PROCEDURE CancelTakeoutOrder(
    IN p_OrderName VARCHAR(200)
)
BEGIN
    DELETE FROM TakeoutOrders 
    WHERE OrderName = p_OrderName;
END //
DELIMITER ;