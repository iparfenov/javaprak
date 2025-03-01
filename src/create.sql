DROP DATABASE IF EXISTS bd;

CREATE DATABASE bd
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en'
    LC_CTYPE = 'en'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

DROP SCHEMA IF EXISTS website CASCADE;
CREATE SCHEMA IF NOT EXISTS website;

CREATE TABLE IF NOT EXISTS website.employees
(
    employee_id serial NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    address text COLLATE pg_catalog."default" NOT NULL,
    birthday date NOT NULL,
    education text COLLATE pg_catalog."default",
    working_since date NOT NULL,
    "position" text COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default" NOT NULL,
    is_admin boolean NOT NULL DEFAULT false,
    login text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT employees_pkey PRIMARY KEY (employee_id)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS website.employees
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS website.employees_payouts
(
    payout_id integer NOT NULL DEFAULT nextval('website.employees_payouts_payout_id_seq'::regclass),
    employee_id integer NOT NULL,
    amount numeric(20,2) NOT NULL,
    paid_at date NOT NULL,
    bonus_id integer,
    CONSTRAINT employees_payouts_pkey PRIMARY KEY (payout_id),
    CONSTRAINT employees_payouts_bonus_id_fkey FOREIGN KEY (bonus_id)
    REFERENCES website.bonuses (bonus_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT payouts_employee_id_fk FOREIGN KEY (employee_id)
    REFERENCES website.employees (employee_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;
ALTER TABLE IF EXISTS website.employees_bonuses
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS website.projects
(
    project_id serial NOT NULL,
    project_name text COLLATE pg_catalog."default" NOT NULL,
    project_start timestamp with time zone NOT NULL,
    project_end timestamp with time zone,
    project_head integer NOT NULL,
    CONSTRAINT projects_pkey PRIMARY KEY (project_id),
    CONSTRAINT head_fk FOREIGN KEY (project_head)
        REFERENCES website.employees (employee_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS website.projects
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS website.employees_projects
(
    employee_id integer NOT NULL,
    project_id integer NOT NULL,
    "position" text COLLATE pg_catalog."default" NOT NULL,
    appointed_at date NOT NULL,
    quit_at date,
    CONSTRAINT employees_projects_pkey PRIMARY KEY (employee_id, project_id),
    CONSTRAINT employee_id_fk FOREIGN KEY (employee_id)
        REFERENCES website.employees (employee_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT project_id_fk FOREIGN KEY (project_id)
        REFERENCES website.projects (project_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS website.employees_projects
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS website.employee_history
(
    employee_id integer NOT NULL,
    positions text[] COLLATE pg_catalog."default" NOT NULL,
    promoted_at date[] NOT NULL,
    CONSTRAINT employee_history_pkey PRIMARY KEY (employee_id),
    CONSTRAINT employee_history_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES website.employees (employee_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS website.employee_history
    OWNER to postgres;


