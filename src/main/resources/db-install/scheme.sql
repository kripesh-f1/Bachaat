/* TO CREATE DATABASE */
CREATE DATABASE IF NOT EXISTS bachaat;

/* TO USE DATABASE */
USE bachaat;

/* TO CREATE TABLE TBL_USER */
CREATE TABLE IF NOT EXISTS tbl_users (
  user_id         INT PRIMARY KEY AUTO_INCREMENT,
  first_name      VARCHAR(50),
  middle_name     VARCHAR(50),
  password        VARCHAR(70),
  last_name       VARCHAR(50),
  mobile_number   VARCHAR(50),
  email_address   VARCHAR(50),
  address         VARCHAR(50),
  activation_code INT,
  created_date    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
  updated_date    TIMESTAMP       DEFAULT 0 ON UPDATE CURRENT_TIMESTAMP,
  active          BOOLEAN         DEFAULT FALSE
);

/* TO CREATE TABLE TBL_ROLE */
CREATE TABLE IF NOT EXISTS tbl_roles (
  role_id INT PRIMARY KEY AUTO_INCREMENT,
  name    VARCHAR(50)
);

/* TO CREATE TABLE USER_ROLE */
CREATE TABLE IF NOT EXISTS user_role (
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES tbl_user (user_id),
  role_id      INT,
  FOREIGN KEY (role_id) REFERENCES tbl_role (role_id)
);