CREATE DATABASE polanecky;

USE polanecky;

CREATE TABLE Oddeleni (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nazev VARCHAR(255) NOT NULL,
                          popis TEXT NOT NULL
);
CREATE TABLE Zakaznik (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          jmeno VARCHAR(255) NOT NULL,
                          prijmeni VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          datum_registrace DATE NOT NULL
);



CREATE TABLE Produkt (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nazev VARCHAR(255) NOT NULL,
                         cena DECIMAL(10, 2) NOT NULL,
                         skladem BOOLEAN NOT NULL
);
CREATE TABLE Zamestnanec (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             jmeno VARCHAR(255) NOT NULL,
                             prijmeni VARCHAR(255) NOT NULL,
                             email VARCHAR(255) NOT NULL,
                             pozice VARCHAR(255) NOT NULL,
                             plat DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Objednavka (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            zakaznik_id INT NOT NULL,
                            produkt_id INT NOT NULL,
                            mnozstvi INT NOT NULL,
                            cena DECIMAL(10, 2) NOT NULL,
                            datum_objednavky DATE NOT NULL,
                            FOREIGN KEY (zakaznik_id) REFERENCES Zakaznik(id),
                            FOREIGN KEY (produkt_id) REFERENCES Produkt(id)
);

INSERT INTO Oddeleni (nazev, popis) VALUES ('IT', 'Information Technology');
INSERT INTO Zakaznik (jmeno, prijmeni, email, datum_registrace) VALUES ('John', 'Doe', 'john.doe@example.com', '2023-01-01');
INSERT INTO Produkt (nazev, cena, skladem) VALUES ('Laptop', 999.99, TRUE);
INSERT INTO Zamestnanec (jmeno, prijmeni, email, pozice, plat) VALUES ('Jane', 'Smith', 'jane.smith@example.com', 'Manager', 50000);
INSERT INTO Objednavka (zakaznik_id, produkt_id, mnozstvi, cena, datum_objednavky) VALUES (1, 1, 1, 999.99, '2023-01-02');




# select * from Objednavka;
#
# select * from Produkt
# select * from Oddeleni
#
# select * from Zakaznik
# select * from Zamestnanec