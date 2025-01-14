CREATE USER 'appliance_user'@'%' IDENTIFIED BY 'qwerty';
GRANT ALL PRIVILEGES ON *.* TO 'appliance_user'@'%' WITH GRANT OPTION;
CREATE schema appliance_db;

USE appliance_db;

CREATE TABLE IF NOT EXISTS appliance (
    id bigint auto_increment,
    name varchar(25) NOT NULL,
    watts varchar(25) NOT NULL,

    PRIMARY KEY (id)
);


INSERT INTO appliance (name, watts) VALUES ('Cocina', '1500');
INSERT INTO appliance (name, watts) VALUES ('Horno', '3000');
INSERT INTO appliance (name, watts) VALUES ('Horno microondas', '700');



