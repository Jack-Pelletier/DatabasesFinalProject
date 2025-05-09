DROP DATABASE IF EXISTS Italian;
CREATE DATABASE Italian;
USE Italian;

CREATE TABLE IF NOT EXISTS Food (
    FoodName varchar(200) not null,
    FoodType varchar(200) not null,
    PRIMARY KEY (FoodName)
);

CREATE TABLE IF NOT EXISTS Entree (
    FoodName varchar(200) not null,
    Calories int not null,
    Cost int not null,
    Gluten bool not null,
    Seafood bool not null,
    PRIMARY KEY (FoodName)
);

CREATE TABLE IF NOT EXISTS Appetizers (
    FoodName varchar(200) not null,
    Calories int not null,
    Cost int not null,
    Gluten bool not null,
    Seafood bool not null,
    PRIMARY KEY (FoodName)
);

CREATE TABLE IF NOT EXISTS Sides (
    FoodName varchar(200) not null,
    Calories int not null,
    Cost int not null,
    Gluten bool not null,
    Seafood bool not null,
    PRIMARY KEY (FoodName)
);

CREATE TABLE IF NOT EXISTS Drinks (
    FoodName varchar(200) not null,
    Cost int not null,
    Alcohol bool not null,
    PRIMARY KEY (FoodName)
);

CREATE TABLE IF NOT EXISTS BanquetHalls (
    RoomName varchar(200) not null,
    Roomnum int not null,
    SizeLimit int not null,
    PRIMARY KEY (Roomnum)
);

CREATE TABLE IF NOT EXISTS Reservations (
    ReservationName varchar(200) not null,
    BanquetHall int not null,
    PartySize int not null,
    Cost int not null,
    SteakMeals int not null,
    SalmonMeals int not null,
    ChickenMeals int not null,
    PastaMeals int not null,
    ReservationDate datetime not null,
    PRIMARY KEY (ReservationName),
    FOREIGN KEY (BanquetHall) REFERENCES BanquetHalls (Roomnum) on update cascade on delete cascade
);

CREATE TABLE IF NOT EXISTS TakeoutOrders (
    OrderName varchar(200) not null,
    Entree varchar (100) not null,
    Appetizer varchar (100) not null,
	Side varchar (100) not null,
    Drink varchar (100),
    PRIMARY KEY (OrderName),
    FOREIGN KEY (Entree) REFERENCES Entree (FoodName) on update cascade on delete cascade,
	FOREIGN KEY (Appetizer) REFERENCES Appetizers (FoodName) on update cascade on delete cascade,
    FOREIGN KEY (Side) REFERENCES Sides (FoodName) on update cascade on delete cascade,
	FOREIGN KEY (Drink) REFERENCES Drinks (FoodName) on update cascade on delete cascade
);

insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Jumbo Shrimp Cocktail", 360, 17, true, true);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Calamari", 450, 18, false, true);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Mozzarella Fritta", 560, 13, false, false);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Bella Napoli", 300, 13, false, false);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Portabella Mushroom Parmigiana", 660, 13, false, false);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("East Utica Greens", 280, 18, true, false);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Garlic Cheese Bread", 300, 11, false, false);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Italian Long Hot Peppers", 410, 14, false, false);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Bruschetta Pomodoro", 430, 11, false, false);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Zuppa Di Stracciatella", 350, 7, false, false);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Clam Chowder", 330, 7, false, true);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Steamed Clams", 420, 17, false, true);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Pasta Fagioli", 300, 7, false, false);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Roma Tomato & Mozzarella Salad", 370, 10, true, false);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Gorgonzola Wedge", 340, 9, true, false);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Antipasto Salad", 750, 17, true, false);
insert into Appetizers (FoodName, Calories, Cost, Gluten, Seafood) Values ("Tomato Cucumber Salad", 300, 7, false, false);

insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Ceasar Salad", 660, 20, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Filet Mignon", 800, 41, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Filet Portabella", 690, 31, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Filet Marsala", 780, 31, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Pork Chop", 830, 26, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("New York Strip", 910, 41, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Sirloin Steak", 830, 28, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Top Shelf Hamburger", 760, 17, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Tortelini Aglio Olio", 690, 22, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Pasta with Meatballs", 730, 21, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Pasta with Marinara", 700, 20, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Chicken Sinatra", 800, 26, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Chicken Prosciutto", 760, 24, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Cicken Marsala", 690, 24, true, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Veal Parmigana", 770, 29, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Veal Marsala", 790, 29, true, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("One Pound Lasagna", 940, 24, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Ravioli Tre Modi", 740, 21, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Chicken Riggies", 770, 24, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Fettuccine Alfredo", 710, 23, false, false);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Salmon", 750, 29, false, true);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Frutti De Mare", 790, 30, true, true);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Shrimp Scampi", 740, 29, false, true);
insert into Entree (FoodName, Calories, Cost, Gluten, Seafood) Values ("Linguine With Clam Sauce", 700, 25, false, true);

insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("House Salad", 250, 6, false, false);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Small Gorgonzola Wedge", 270, 7, false, false);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Small Tomato Cucumber Salad", 230, 5, false, false);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Tomato and Mozarella Salad", 250, 8, false, false);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Sauteed Shrimp", 290, 6, false, true);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Cold Water Lobster Tail", 400, 40, false, true);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Grilled Onions", 200, 4, true, false);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Hot Cherry Peppers, Mushrooms and Onions", 270, 5, false, false);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Baked Potato", 300, 5, true, false);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Homemade Meatballs", 280, 7, false, false);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Steamed Broccoli", 190, 5, true, false);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Steamed Asparagus", 160, 5, true, false);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Garlic Mashed Potatoes", 220, 5, true, false);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Sauteed Mushrooms", 260, 5, true, false);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("French Fries", 290, 6, true, false);
insert into Sides (FoodName, Calories, Cost, Gluten, Seafood) Values ("Homemade Italian Sausage", 310, 7, false, false);

insert into Drinks (FoodName, Cost, Alcohol) Values ("Water", 0, false);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Lemonade", 3, false);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Soft Drink", 4, false);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Shirley Temple", 5, false);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Chocolate Milk", 2, false);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Ultimate Cosmo Martini", 16, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Old Blue Eyes", 16, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Chocolate Martini", 16, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Sour Apple Martini", 16, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Metropolitan", 16, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Pomergranite Martini", 16, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Limoncello Martini", 16, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("The Well-Mannered Dirty Martini", 16, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Bellintini", 16, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Midnight Espresso", 16, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Moscato", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Riesling", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Rose", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Pinot Grigio", 10, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Chardonnay", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Reserve Chardonnay", 12, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Pinot Noir", 10, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Da Vinci Chianti", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Red Blend Vino", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Fetzer Merlot", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Cabernet Sauvignon", 10, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Bourbon Barrel Aged Zinfandel", 12, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Alamos Malbec", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Black Opal Shiraz", 16, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Stag's Leap Merlot", 17, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Riunite Lambrusco", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("House Cabernet", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("House Chardonnay", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("House Merlot", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("House White Zinfandel", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("House Pinot Grigio", 9, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Prosecco", 10, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Brut", 20, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Manhattan Cocktail", 16, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Old Fashioned Cocktail", 16, true);
insert into Drinks (FoodName, Cost, Alcohol) Values ("Margarita", 16, true);

Insert into BanquetHalls(RoomName, RoomNum, SizeLimit) Values ("Goodfellas Room", 1, 8);
Insert into BanquetHalls(RoomName, RoomNum, SizeLimit) Values ("Rat Pack Room", 2, 8);
Insert into BanquetHalls(RoomName, RoomNum, SizeLimit) Values ("Tony Bennett Room", 3, 8);
Insert into BanquetHalls(RoomName, RoomNum, SizeLimit) Values ("Yankees Room", 4, 8);
Insert into BanquetHalls(RoomName, RoomNum, SizeLimit) Values ("Sinatra Room", 5, 20);
Insert into BanquetHalls(RoomName, RoomNum, SizeLimit) Values ("Banquet Room", 6, 50);










