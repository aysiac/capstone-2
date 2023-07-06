BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, transfer, transfer_type, status;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE TABLE transfer_type(
	transfer_type_id serial PRIMARY KEY,
	transfer_type_name varchar(50));
		
CREATE TABLE status(
	status_id serial PRIMARY KEY,
	status_name varchar(50));
	
CREATE TABLE transfer(
	transfer_id serial NOT NULL,
	from_user int NOT NULL,
	to_user int NOT NULL,
	transfer_amount decimal(13,2) NOT NULL,
	status_id int NOT NULL,
	transfer_type_id int NOT NULL,
	CONSTRAINT pk_transfer PRIMARY KEY (transfer_id),
	CONSTRAINT fk_transfer_from_user FOREIGN KEY (from_user) REFERENCES tenmo_user(user_id),
	CONSTRAINT fk_transfer_to_user FOREIGN KEY (to_user) REFERENCES tenmo_user(user_id),
	CONSTRAINT fk_transfer_status FOREIGN KEY (status_id) REFERENCES status (status_id),
	CONSTRAINT fk_transfer_type FOREIGN KEY (transfer_type_id) REFERENCES transfer_type (transfer_type_id)
	);

	
COMMIT;
