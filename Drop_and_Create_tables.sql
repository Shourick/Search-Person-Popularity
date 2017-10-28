CREATE SCHEMA IF NOT EXISTS `spp` ;
DROP TABLE IF EXISTS `spp`.`keywords`;
DROP TABLE IF EXISTS `spp`.`personpagerank`;
DROP TABLE IF EXISTS `spp`.`persons`;
DROP TABLE IF EXISTS `spp`.`pages`;
DROP TABLE IF EXISTS `spp`.`sites`;
CREATE TABLE IF NOT EXISTS `spp`.`persons` (
	`ID` INT NOT NULL AUTO_INCREMENT,
    `Name` BLOB(2048) NOT NULL,
    PRIMARY KEY (`ID`)
);
CREATE TABLE IF NOT EXISTS `spp`.`keywords` (
	`ID` INT NOT NULL AUTO_INCREMENT,
	`Name` BLOB(2048) NOT NULL,
    `PersonID` INT NOT NULL,
	PRIMARY KEY (`ID`),
    INDEX `fk_PersonID_idx` (`PersonID` ASC),
	CONSTRAINT `fk_PersonID`
		FOREIGN KEY (`PersonID`)
		REFERENCES `spp`.`persons`(`ID`)
		ON DELETE CASCADE
		ON UPDATE NO ACTION
);
CREATE TABLE IF NOT EXISTS `spp`.`sites` (
	`ID` INT NOT NULL AUTO_INCREMENT,
    `Name` VARCHAR(256) NOT NULL,
    PRIMARY KEY (`ID`)
);
CREATE TABLE IF NOT EXISTS `spp`.`pages` (
	`ID` INT NOT NULL AUTO_INCREMENT,
    `Url` BLOB(2048) NOT NULL,
    `SiteID` INT NOT NULL,
    `FoundDateTime` DATETIME NULL,
    `LastScanDate` DATETIME NULL,
    PRIMARY KEY (`ID`),
    INDEX `fk_SiteID_idx` (`SiteID` ASC),
    CONSTRAINT `fk_SiteID`
		FOREIGN KEY (`SiteID`)
		REFERENCES `spp`.`sites` (`ID`)
		ON DELETE CASCADE
		ON UPDATE NO ACTION
);
CREATE TABLE IF NOT EXISTS `spp`.`personpagerank` (
	`ID`  INT NOT NULL AUTO_INCREMENT,
	`PersonID` INT NOT NULL,
    `PageID` INT NOT NULL,
	`Rank` INT NOT NULL DEFAULT 0,
	PRIMARY KEY (`ID`),
    INDEX `fk_PageIG_idx` (`PageID` ASC),
	CONSTRAINT `fk_PersonID_rank`
		FOREIGN KEY (`PersonID`)
		REFERENCES `spp`.`persons`(`ID`)
		ON DELETE CASCADE
		ON UPDATE NO ACTION,
	CONSTRAINT `fk_PageID`
		FOREIGN KEY (`PageID`)
		REFERENCES `spp`.`pages` (`ID`)
		ON DELETE CASCADE
		ON UPDATE NO ACTION
);
