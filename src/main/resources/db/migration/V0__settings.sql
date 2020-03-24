create schema if not exists public;

comment on schema public is 'standard public schema';

alter schema public owner to postgres;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;
COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';
