--------------- PostgeSQL ---------------
/*CREATE TABLE users(
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
*/