---- Simple sample using a init sql, please it's not recommended use this way, 
-- just using right now to make it easier to apply REPLICA IDENTITY

\c stock;
-- Create the roles and schemas
CREATE ROLE company NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN PASSWORD 'admin';
CREATE SCHEMA "company-api" AUTHORIZATION company;

CREATE ROLE pull NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN PASSWORD 'admin';
CREATE SCHEMA "pull-api" AUTHORIZATION pull;