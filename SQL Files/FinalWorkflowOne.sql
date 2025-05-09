Use Italian;
DROP PROCEDURE IF EXISTS GetAllFoodItems;

DELIMITER $$
CREATE PROCEDURE GetAllFoodItems()
BEGIN
    SELECT 'Appetizer' AS FoodType, FoodName, Calories, Cost, Gluten, Seafood
    FROM Entree
    UNION ALL
    SELECT 'Entree' AS FoodType, FoodName, Calories, Cost, Gluten, Seafood
    FROM Appetizers
    UNION ALL
    SELECT 'Side' AS FoodType, FoodName, Calories, Cost, Gluten, Seafood
    FROM Sides;
END$$
DELIMITER ;
Call GetAllFoodItems();