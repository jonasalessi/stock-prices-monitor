---- Simple sample using a init sql, please it's not recommended use this way, 
-- just using right now to make it easier to apply REPLICA IDENTITY

\c stock;
-- Create the roles and schemas
CREATE ROLE company NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN PASSWORD 'admin';
CREATE SCHEMA "company-api" AUTHORIZATION company;

CREATE ROLE pull NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN PASSWORD 'admin';
CREATE SCHEMA "pull-api" AUTHORIZATION pull;


-- Create the Company API tables 

CREATE TABLE "company-api"."event_register_company" (
	id bigserial PRIMARY KEY,
	payload jsonb NOT NULL,
	"time" timestamp NOT NULL
);

ALTER TABLE "company-api"."event_register_company" REPLICA IDENTITY FULL;

CREATE TABLE "company-api".company (
	id bigserial PRIMARY KEY,
	name varchar(255) NOT NULL
);

CREATE TABLE "company-api".ticker (
	id bigserial primary KEY,
	company_id int4 NULL,
	"name" varchar(10) NOT NULL,
	CONSTRAINT ticker_company_fk FOREIGN KEY (company_id) REFERENCES "company-api".company(id)
);

-- Create the PULL API table

CREATE TABLE "pull-api".ticker (
	id int4 NOT NULL,
	"name" varchar NOT NULL
);
