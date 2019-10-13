DROP TABLE IF EXISTS users;
 
CREATE TABLE users (
  id UUID  PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(25) NOT NULL,
  password VARCHAR(10) NOT NULL,
  created TIMESTAMP(9) NOT NULL,
  modified TIMESTAMP(9) NULL,
  last_login TIMESTAMP(9) NOT NULL,
  token CLOB NOT NULL,
  is_active BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS phones;
 
CREATE TABLE phones (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  number VARCHAR(10) NOT NULL,
  citycode VARCHAR(3) NOT NULL,
  countrycode VARCHAR(3) DEFAULT NULL,
  owner UUID DEFAULT NULL
);

ALTER TABLE phones
    ADD FOREIGN KEY (owner) 
    REFERENCES users(id);