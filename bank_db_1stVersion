-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema securebanking
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema securebanking
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `securebanking` DEFAULT CHARACTER SET latin1 ;
USE `securebanking` ;

-- -----------------------------------------------------
-- Table `securebanking`.`emailtemplates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `securebanking`.`emailtemplates` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(255) NULL DEFAULT NULL,
  `FromAddress` VARCHAR(255) NULL DEFAULT NULL,
  `Subject` LONGTEXT NULL DEFAULT NULL,
  `BodyMessage` LONGTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `securebanking`.`externaluser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `securebanking`.`externaluser` (
  `customerID` INT(11) NOT NULL AUTO_INCREMENT,
  `emailID` VARCHAR(45) NOT NULL,
  `balance` DECIMAL(10,2) NOT NULL,
  `typeOfCustomer` INT(1) NOT NULL,
  `passwordHash` VARCHAR(45) NOT NULL,
  `employeeOverride` INT(1) NULL DEFAULT '0',
  `creationDate` DATETIME NOT NULL,
  `lastLogin` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `customQuestion1` VARCHAR(45) NULL DEFAULT NULL,
  `customQuestion2` VARCHAR(45) NULL DEFAULT NULL,
  `accountType` INT(1) NOT NULL DEFAULT '0',
  `OTP` VARCHAR(45) NULL DEFAULT NULL,
  `customQuestion3` VARCHAR(45) NULL DEFAULT NULL,
  `firstName` CHAR(25) NOT NULL,
  `lastName` CHAR(25) NOT NULL,
  `Address` VARCHAR(45) NOT NULL,
  `State` CHAR(20) NULL DEFAULT NULL,
  `Country` CHAR(30) NULL DEFAULT NULL,
  `zipCode` INT(5) NULL DEFAULT NULL,
  `phoneNo` INT(10) NULL DEFAULT NULL,
  PRIMARY KEY (`customerID`),
  UNIQUE INDEX `customerID_UNIQUE` (`customerID` ASC),
  UNIQUE INDEX `emailID_UNIQUE` (`emailID` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `securebanking`.`internaluser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `securebanking`.`internaluser` (
  `EmployeeID` INT(11) NOT NULL AUTO_INCREMENT,
  `emailID` VARCHAR(45) NOT NULL,
  `typeOfEmployee` INT(1) NOT NULL,
  `passwordHash` VARCHAR(45) NOT NULL,
  `creationDate` DATETIME NOT NULL,
  `lastLogin` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `firstName` CHAR(25) NOT NULL,
  `lastName` CHAR(25) NOT NULL,
  `phoneNo` INT(10) NULL DEFAULT NULL,
  `Address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`EmployeeID`),
  UNIQUE INDEX `EmployeeID_UNIQUE` (`EmployeeID` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `securebanking`.`session`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `securebanking`.`session` (
  `sessionID` VARCHAR(45) NOT NULL,
  `userID` INT(11) NOT NULL,
  `loginTime` DATETIME NOT NULL,
  `ExpiryDate` DATETIME NOT NULL,
  PRIMARY KEY (`sessionID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `securebanking`.`transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `securebanking`.`transactions` (
  `transactionID` INT(11) NOT NULL AUTO_INCREMENT,
  `senderID` INT(11) NOT NULL,
  `receiverID` INT(11) NOT NULL,
  `timeOfTransaction` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pendingRequest` INT(1) NULL DEFAULT '0',
  `transactionOverride` INT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`transactionID`),
  UNIQUE INDEX `transactionID_UNIQUE` (`transactionID` ASC),
  INDEX `sender_idx` (`senderID` ASC),
  INDEX `receiver_idx` (`receiverID` ASC),
  CONSTRAINT `receiver`
    FOREIGN KEY (`receiverID`)
    REFERENCES `securebanking`.`externaluser` (`customerID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sender`
    FOREIGN KEY (`senderID`)
    REFERENCES `securebanking`.`externaluser` (`customerID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
