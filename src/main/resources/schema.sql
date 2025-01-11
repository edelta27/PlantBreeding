DROP TABLE IF EXISTS PLANT;

CREATE TABLE PLANT (
                               id INT AUTO_INCREMENT  PRIMARY KEY,
                               name_plant VARCHAR(250) NOT NULL,
                               type_plant ENUM NOT NULL,
                               planting_date DATE NOT NULL,
                               health_status ENUM NOT NULL,
                               annual_or_perennial BOOLEAN NOT NULL,
                               description CHAR NULL,
                               height INTEGER NULL,
                               date_created_plant DATE,
                               date_edited_plant DATE,
                               version (with @Version annotation),
);
DROP TABLE IF EXISTS TASK;

CREATE TABLE TASK (
                       id INT AUTO_INCREMENT  PRIMARY KEY,
                       type_task ENUM NOT NULL,
                       notes_task CHAR,
                       date_task DATE,
                       status_task ENUM NOT NULL,
                       date_created_task DATE,
                       date_edited_task DATE,
                       version (with @Version annotation),
);
DROP TABLE IF EXISTS FERTILIZER;

CREATE TABLE FERTILIZER (
                      id INT AUTO_INCREMENT  PRIMARY KEY,
                      name_fertilizer VARCHAR(250) NOT NULL,
                      type_fertilizer ENUM NOT NULL,
                      application_method ENUM NOT NULL,
                      recommendations CHAR,
                      date_created_task DATE,
                      date_edited_task DATE,
                      version (with @Version annotation),
);