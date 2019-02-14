DROP TABLE IF EXISTS authors CASCADE;

CREATE TABLE authors (
  id SERIAL PRIMARY KEY,
  name character varying(255) NOT NULL
);

CREATE UNIQUE INDEX authors_name on authors (name);
