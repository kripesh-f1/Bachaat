
/* TO INSERT RECORD IN TBL_USER */
INSERT INTO tbl_users(first_name,middle_name,last_name,password,mobile_number,email_address,address,activation_code)
VALUES ('admin','admin','admin','admin','1234567890','admin@admin.com','admin',12345);

/* TO INSERT RECORD OF ADMIN IN TBL_ROLE */
INSERT INTO tbl_roles(name)
VALUES ('ADMIN');

/* TO INSERT RECORD OF USER IN TBL_ROLE */
INSERT INTO tbl_roles(name)
VALUES ('USER');


/* TO INSERT RECORD IN USER_ROLE */
INSERT INTO user_role(user_id,role_id)
VALUES (1,1);

/*TO INSERT RECORD IN TBL_MENU*/
INSERT INTO `tbl_menu` (`menu_id`, `link`, `menu_name`, `parent_id`, `status`) VALUES
	(1, '/home', 'home', 0, b'1'),
	(2, '/technologies', 'technologies', 1, b'1'),
	(3, '/reference', 'references and guide', 1, b'1'),
	(4, '/feedbacks', 'feedbacks', 1, b'1'),
	(5, '/technologies/html', 'html', 2, b'1'),
	(6, '/technologies/css', 'css', 2, b'1'),
	(7, '/technologies/JS', 'JS', 2, b'1'),
	(8, '/technologies/Graphics', 'Graphics', 2, b'1'),
	(9, '/technologies/HTTP', 'HTTP', 2, b'1'),
	(10, '/references/learn web development', 'learn web development', 3, b'1'),
	(11, '/references/tutorial', 'tutorial', 3, b'1'),
	(12, '/references/reference', 'references', 3, b'1'),
	(13, '/references/developer guide', 'developer guide', 3, b'1'),
	(14, '/references/game development', 'game development', 3, b'1'),
	(15, '/feedbacks/get help', 'get help', 4, b'1'),
	(16, '/feedbacks/join main community', 'join main community', 4, b'1'),
	(17, '/feedbacks/report a bug', 'report a bug', 4, b'1');
