CREATE ROLE storedb_user LOGIN PASSWORD '123456';
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO storedb_user;
GRANT USAGE ON ALL SEQUENCES IN SCHEMA  public TO storedb_user;

CREATE TABLE product (
	id SERIAL PRIMARY KEY,
	name TEXT UNIQUE NOT NULL,
  description TEXT,
	price REAL NOT NULL,
 	picturePath TEXT
);

CREATE TABLE store_user(
  id serial PRIMARY KEY,
  name VARCHAR (50) UNIQUE NOT NULL,
  password VARCHAR (60) NOT NULL,
  userType VARCHAR (50) NOT NULL CHECK (userType in ('GUEST', 'USER', 'ADMIN'))
);


