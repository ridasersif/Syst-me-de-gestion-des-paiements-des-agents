CREATE TABLE Agents (
  idAgent INT AUTO_INCREMENT PRIMARY KEY,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  agentType ENUM('WORKER','MANAGER','DIRECTOR','INTERN') NOT NULL,
  department_id INT DEFAULT NULL,
  CONSTRAINT fk_agents_department FOREIGN KEY (department_id) REFERENCES Department (idDepartment)
);

CREATE TABLE Department (
  idDepartment INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  manager_id INT DEFAULT NULL,
  CONSTRAINT fk_department_manager FOREIGN KEY (manager_id) REFERENCES Agents (idAgent)
);

CREATE TABLE Payment (
  idPayment INT AUTO_INCREMENT PRIMARY KEY,
  paymentType ENUM('SALARY','PRIME','BONUS','ALLOWANCE') NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  date DATE NOT NULL,
  reason VARCHAR(255) DEFAULT NULL,
  agent_id INT DEFAULT NULL,
  CONSTRAINT fk_payment_agent FOREIGN KEY (agent_id) REFERENCES Agents (idAgent)
);

CREATE TABLE Allowance (
  idAllowance INT AUTO_INCREMENT PRIMARY KEY,
  payment_id INT UNIQUE,
  conditionValidated TINYINT(1) DEFAULT 0,
  CONSTRAINT fk_allowance_payment FOREIGN KEY (payment_id) REFERENCES Payment (idPayment)
);

CREATE TABLE Bonus (
  idBonus INT AUTO_INCREMENT PRIMARY KEY,
  payment_id INT UNIQUE,
  conditionValidated TINYINT(1) DEFAULT 0,
  CONSTRAINT fk_bonus_payment FOREIGN KEY (payment_id) REFERENCES Payment (idPayment)
);

INSERT INTO Department (name, manager_id) VALUES
('Logistics', NULL),
('Finance', NULL),
('IT', NULL),
('HR', NULL),
('Marketing', NULL),
('Logistics', NULL);

INSERT INTO Agents (firstName, lastName, email, password, agentType, department_id) VALUES
('Rida', 'Sersif', 'ridasersif1@gmail.com', '0000', 'DIRECTOR', NULL),
('rida', 'sersif', 'rida@gmail.ma', '0000', 'MANAGER', 1),
('sersif', 'sersif', 'sersif@gmail.com', '0000', 'INTERN', 6);


