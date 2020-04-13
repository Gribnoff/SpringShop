create schema if not exists shop;

comment on schema shop is 'standard public schema';

alter schema shop owner to postgres;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA shop;
COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';
