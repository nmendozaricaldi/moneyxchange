--------------- PostgeSQL ---------------
CREATE TABLE users(
   username varchar(20) NOT NULL,
   password varchar(20) NOT NULL,
   enabled boolean NOT NULL DEFAULT FALSE,
   primary key(username)
);

create table user_roles (
  user_role_id SERIAL PRIMARY KEY,
  username varchar(20) NOT NULL,
  role varchar(20) NOT NULL,
  UNIQUE (username,role),
  FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE money_rate
(
    symbol character varying(255) COLLATE pg_catalog."default" NOT NULL,
    rate double precision,
    CONSTRAINT money_rate_pkey PRIMARY KEY (symbol)
)

INSERT INTO users(username,password,enabled) VALUES ('admin','admin', true);
INSERT INTO users(username,password,enabled) VALUES ('user','user', true);
 
INSERT INTO user_roles (username, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles (username, role) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role) VALUES ('user', 'ROLE_USER');

INSERT INTO money_rate (symbol, rate) VALUES ('EU', 1.079);
