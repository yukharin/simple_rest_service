-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Schema rest_service
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema rest_service
-- -----------------------------------------------------


-- -----------------------------------------------------
-- Table `rest_service`.`persons`
-- -----------------------------------------------------
CREATE TABLE persons
(
    `id`        BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(50) NOT NULL,
    `birthdate` DATE        NOT NULL,
    PRIMARY KEY (`id`)
);


-- -----------------------------------------------------
-- Table `rest_service`.`cars`
-- -----------------------------------------------------
CREATE TABLE cars
(
    `id`         BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `model`      VARCHAR(80) NOT NULL,
    `horsepower` INT         NOT NULL,
    `id_person`  BIGINT(20)  NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_person`)
        REFERENCES persons (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);




