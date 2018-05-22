
/* TO INSERT RECORD IN TBL_USER */
INSERT INTO tbl_user(first_name,middle_name,last_name,password,mobile_number,email_address,address,activation_code)
VALUES ('admin','admin','admin','admin','1234567890','admin@admin.com','admin',12345);

/* TO INSERT RECORD IN TBL_AUTHORITY */
INSERT INTO tbl_authority(authority_name)
VALUES ('admin');

/* TO INSERT RECORD IN USER_AUTHORITY */
INSERT INTO user_authority(user_id,authority_id)
VALUES (1,1);