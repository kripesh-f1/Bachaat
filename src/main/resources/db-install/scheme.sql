/* TO CREATE DATABASE */
CREATE DATABASE IF NOT EXISTS bachaat;

/* TO USE DATABASE */
USE bachaat;

/* TO CREATE TABLE TBL_USER */
CREATE TABLE IF NOT EXISTS tbl_user (
    user_id int PRIMARY KEY AUTO_INCREMENT,
    first_name varchar(50),
    middle_name varchar(50),
    password varchar(70),
    last_name varchar(50),
    mobile_number varchar(50),
    email_address varchar(50),
    address varchar(50),
    activation_code int,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    updated_date TIMESTAMP DEFAULT 0 ON UPDATE CURRENT_TIMESTAMP ,
    active boolean DEFAULT FALSE
);

/* TO CREATE TABLE TBL_AUTHORITY */
CREATE TABLE tbl_authority (
		authority_id int PRIMARY KEY AUTO_INCREMENT,
		authority_name varchar(50)
);

/* TO CREATE TABLE USER_AUTHORITY */
CREATE TABLE user_authority (
		user_id int, FOREIGN KEY(user_id) REFERENCES tbl_user(user_id),
		authority_id int, FOREIGN KEY(authority_id) REFERENCES tbl_authority(authority_id)
);