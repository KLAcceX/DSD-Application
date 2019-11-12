CREATE USER postgres WITH PASSWORD 'admin';

-- Database: dsdproj

-- DROP DATABASE dsdproj;

CREATE DATABASE dsdproj
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Table: public.tb_user

-- DROP TABLE public.tb_user;

CREATE TABLE public.tb_user
(
    surname character varying(30) COLLATE pg_catalog."default",
    nickname character varying(15) COLLATE pg_catalog."default" NOT NULL,
    name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    email character varying(45) COLLATE pg_catalog."default" NOT NULL,
    status character(1) COLLATE pg_catalog."default",
    id integer NOT NULL DEFAULT nextval('tb_user_id_seq'::regclass),
    CONSTRAINT tb_user_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.tb_user
    OWNER to postgres;

-- Table: public.tb_group

-- DROP TABLE public.tb_group;

CREATE TABLE public.tb_group
(
    name character varying(40) COLLATE pg_catalog."default" NOT NULL,
    id integer NOT NULL DEFAULT nextval('tb_group_id_seq'::regclass),
    CONSTRAINT tb_group_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.tb_group
    OWNER to postgres;

-- Table: public.tb_server_info

-- DROP TABLE public.tb_server_info;

CREATE TABLE public.tb_server_info
(
    ip character varying COLLATE pg_catalog."default" NOT NULL,
    port integer NOT NULL,
    CONSTRAINT tb_server_info_pkey PRIMARY KEY (ip, port)
)

TABLESPACE pg_default;

ALTER TABLE public.tb_server_info
    OWNER to postgres;