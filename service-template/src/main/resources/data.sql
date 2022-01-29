DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
  RES_ID BIGINT PRIMARY KEY,
  CUST_ID VARCHAR(250) NOT NULL UNIQUE,
  FIRST_NM VARCHAR(250) NOT NULL,
  LAST_NM VARCHAR(250) NOT NULL,
  ADD_ST VARCHAR(250) NOT NULL,
  ADD_CITY VARCHAR(250) NOT NULL,
  ADD_STAT VARCHAR(250) NOT NULL,
  ADD_ZIP VARCHAR(250) NOT NULL
);
