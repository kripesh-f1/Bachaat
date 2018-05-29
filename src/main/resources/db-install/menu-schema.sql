 CREATE TABLE IF NOT EXISTS tbl_menu (
  menu_id         INT PRIMARY KEY AUTO_INCREMENT,
  menu_name       VARCHAR(50),
  link            VARCHAR(50),
  parent_id       INT
  status          BOOLEAN DEFAULT TRUE
);