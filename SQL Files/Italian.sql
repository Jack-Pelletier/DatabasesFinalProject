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
    ReservationDate varchar(200) not null,
    PRIMARY KEY (ReservationName),
    FOREIGN KEY (BanquetHall) REFERENCES BanquetHalls (Roomnum) on update cascade on delete cascade
);

CREATE TABLE IF NOT EXISTS TakeoutOrders (
    OrderName varchar(200) not null,
    Cost int not null,
    Entree varchar (100),
    Appetizer varchar (100),
	Side varchar (100),
    Drink varchar (100),
    PRIMARY KEY (OrderName),
    FOREIGN KEY (Entree) REFERENCES Entree (FoodName) on update cascade on delete cascade,
	FOREIGN KEY (Appetizer) REFERENCES Appetizers (FoodName) on update cascade on delete cascade,
    FOREIGN KEY (Side) REFERENCES Sides (FoodName) on update cascade on delete cascade,
	FOREIGN KEY (Drink) REFERENCES Drinks (FoodName) on update cascade on delete cascade
);