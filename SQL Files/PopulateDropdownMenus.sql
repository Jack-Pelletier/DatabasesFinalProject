Use Italian;
DROP PROCEDURE IF EXISTS GetFoodItemByCategory
DELIMITER //
CREATE PROCEDURE GetFoodItemByCategory(IN categoryName VARCHAR(50))
BEGIN
    IF categoryName = 'Entree' THEN
        SELECT FoodName FROM Entree;
    ELSEIF categoryName = 'Appetizers' THEN
        SELECT FoodName FROM Appetizers;
    ELSEIF categoryName = 'Sides' THEN
        SELECT FoodName FROM Sides;
    ELSEIF categoryName = 'Drinks' THEN
        SELECT FoodName FROM Drinks;
    END IF;
END //

DELIMITER ;
Call GetFoodItemByCategory('Entree');
