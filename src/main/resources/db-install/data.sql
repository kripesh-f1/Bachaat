
/* TO INSERT RECORD IN TBL_USER */
INSERT INTO tbl_user(first_name,middle_name,last_name,password,mobile_number,email_address,address,activation_code)
VALUES ('admin','admin','admin','admin','1234567890','admin@admin.com','admin',12345);

/* TO INSERT RECORD OF ADMIN IN TBL_ROLE */
INSERT INTO tbl_role(name)
VALUES ('ADMIN');

/* TO INSERT RECORD OF USER IN TBL_ROLE */
INSERT INTO tbl_role(name)
VALUES ('USER');


/* TO INSERT RECORD IN USER_ROLE */
INSERT INTO user_role(user_id,role_id)
VALUES (1,1);