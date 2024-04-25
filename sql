-- Tabela para armazenar informações das pessoas
CREATE TABLE Persons (
    id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL
);

-- Tabela para armazenar informações dos endereços
CREATE TABLE Addresses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    person_id INT NOT NULL,
    street VARCHAR(100) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    number VARCHAR(10) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    is_main_address BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (person_id) REFERENCES Persons(id)
);
