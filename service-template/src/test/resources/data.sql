DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
  RES_ID BIGINT PRIMARY KEY,
  CUST_ID VARCHAR(250) NOT NULL UNIQUE,
  FIRST_NM VARCHAR(250) NOT NULL,
  LAST_NM VARCHAR(250) NOT NULL,
  ADD_ST_1 VARCHAR(250) NOT NULL,
  ADD_ST_2 VARCHAR(250),
  ADD_CITY VARCHAR(250) NOT NULL,
  ADD_STAT VARCHAR(250) NOT NULL,
  ADD_ZIP VARCHAR(250) NOT NULL
);

INSERT INTO customer (RES_ID, CUST_ID, FIRST_NM, LAST_NM, ADD_ST_1, ADD_ST_2, ADD_CITY, ADD_STAT, ADD_ZIP) VALUES (100, 'LEE_2', 'Bruce', 'Lee', '41 Cumberland Road', 'Unit A', 'Kowloon', 'Hong Kong', '9234W-Z');
