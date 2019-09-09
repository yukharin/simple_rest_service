-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema rest_service
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema rest_service
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `rest_service` DEFAULT CHARACTER SET utf8;
USE `rest_service`;

-- -----------------------------------------------------
-- Table `rest_service`.`persons`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rest_service`.`persons`
(
    `id`        BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(50) NOT NULL,
    `birthdate` DATE        NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `name_index` (`name` ASC) VISIBLE,
    INDEX `birtdate_index` (`birthdate` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rest_service`.`cars`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rest_service`.`cars`
(
    `id`         BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `model`      VARCHAR(80)  NOT NULL,
    `horsepower` INT UNSIGNED NOT NULL,
    `id_person`  BIGINT(20)   NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `id_person_idx` (`id_person` ASC) INVISIBLE,
    INDEX `model_index` (`model` ASC) VISIBLE,
    CONSTRAINT `id_person`
        FOREIGN KEY (`id_person`)
            REFERENCES `rest_service`.`persons` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
