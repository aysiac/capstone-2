BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account;

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

CREATE TABLE transaction(
	transaction_id serial NOT NULL,
	from_user int NOT NULL,
	to_user int NOT NULL,
	transaction_date date NOT NULL,
	transaction_amount decimal(13,2) NOT NULL,
	status varchar(50) NOT NULL,
	CONSTRAINT PK_transaction PRIMARY KEY (transaction_id),
	CONSTRAINT FK_trasaction_from_user FOREIGN KEY (from_user) REFERENCES tenmo_user (user_id),
	CONSTRAINT FK_trasaction_to_user FOREIGN KEY (to_user) REFERENCES tenmo_user (user_id));
	
	
CREATE TABLE transfer_type(
	transfer_type_id serial PRIMARY KEY,
	transfer_name varchar(50));
		
CREATE TABLE status(
	status_id serial PRIMARY KEY,
	status_name varchar(50));
	
ALTER TABLE transaction Rename TO transfer

ALTER TABLE transfer RENAME transaction_id TO transfer_id

ALTER TABLE transfer RENAME transaction_date TO transfer_date;
ALTER TABLE transfer RENAME transaction_amount TO transfer_amount;

ALTER TABLE transfer ADD COLUMN status_id int 
ALTER TABLE transfer ADD CONSTRAINT fk_transfer_status FOREIGN KEY (status_id) REFERENCES status (status_id)

ALTER TABLE transfer ADD COLUMN transfer_type_id int
ALTER TABLE transfer ADD CONSTRAINT fk_transfer_type FOREIGN KEY (transfer_type_id) REFERENCES transfer_type (transfer_type_id)

ALTER TABLE transfer DROP column status

ALTER TABLE transfer ADD COLUMN created_date Date;
ALTER TABLE transfer ADD COLUMN created_by int;
	
COMMIT;
