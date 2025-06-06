-- public.bank_accounts definition

-- Drop table

-- DROP TABLE public.bank_accounts;

CREATE TABLE public.bank_accounts (
	active bool NOT NULL,
	balance float8 NOT NULL,
	id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	CONSTRAINT bank_accounts_pkey PRIMARY KEY (id)
);


-- public.app_role definition

-- Drop table

-- DROP TABLE public.app_role;

CREATE TABLE public.app_role (
	"role" varchar(255) NOT NULL,
	CONSTRAINT app_role_pkey PRIMARY KEY (role)
);


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	bank_account_id int8 NULL,
	created_at timestamp(6) NULL,
	id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	username varchar(20) NOT NULL,
	email varchar(50) NOT NULL,
	"password" varchar(128) NOT NULL,
	CONSTRAINT users_bank_account_id_key UNIQUE (bank_account_id),
	CONSTRAINT users_email_key UNIQUE (email),
	CONSTRAINT users_pkey PRIMARY KEY (id),
	CONSTRAINT fk9dg9wpc5dj5ls4o80ku820rmj FOREIGN KEY (bank_account_id) REFERENCES public.bank_accounts(id)
);
CREATE INDEX idx_email ON public.users USING btree (email);


-- public.user_connections definition

-- Drop table

-- DROP TABLE public.user_connections;

CREATE TABLE public.user_connections (
	connection_id int8 NOT NULL,
	user_id int8 NOT NULL,
	CONSTRAINT user_connections_pkey PRIMARY KEY (connection_id, user_id),
	CONSTRAINT fk56b5yg0vwv72mhph7e5u2hn6x FOREIGN KEY (user_id) REFERENCES public.users(id),
	CONSTRAINT fkohvj3bhf0c6gb645k4atn4rax FOREIGN KEY (connection_id) REFERENCES public.users(id)
);


-- public.users_roles definition

-- Drop table

-- DROP TABLE public.users_roles;

CREATE TABLE public.users_roles (
	user_id int8 NOT NULL,
	roles_role varchar(255) NOT NULL,
	CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES public.users(id),
	CONSTRAINT fk7095o4r0ww4wmo93v7b90w10f FOREIGN KEY (roles_role) REFERENCES public.app_role("role")
);


-- public.transactions definition

-- Drop table

-- DROP TABLE public.transactions;

CREATE TABLE public.transactions (
	amount float8 NOT NULL,
	"date" timestamp(6) NULL,
	id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	receiver_id int8 NOT NULL,
	sender_id int8 NOT NULL,
	description varchar(255) NULL,
	CONSTRAINT transactions_pkey PRIMARY KEY (id),
	CONSTRAINT fk3ly4r8r6ubt0blftudix2httv FOREIGN KEY (sender_id) REFERENCES public.users(id),
	CONSTRAINT fk5nn8ird7idyxyxki68gox2wbx FOREIGN KEY (receiver_id) REFERENCES public.users(id)
);
CREATE INDEX idx_receiver ON public.transactions USING btree (receiver_id);
CREATE INDEX idx_sender ON public.transactions USING btree (sender_id);

-- Insert fake bank accounts
INSERT INTO public.bank_accounts (id, active, balance) VALUES
    (1, true, 1000.00),
    (2, true, 1500.00),
    (3, true, 2000.00),
    (4, true, 2500.00),
    (5, true, 3000.00),
    (6, true, 3500.00),
    (7, true, 4000.00),
    (8, true, 4500.00),
    (9, true, 5000.00),
    (10, true, 5500.00);

-- Insert fake users
INSERT INTO public.users (username, email, "password", bank_account_id, created_at) VALUES
    ('alice', 'alice@example.com', '$2a$10$WsYGYFpGJaDMnuOTL/qiW.siu6Ibn1Kvzy2cs/kXONu3G05u0UsYO', 1, '2023-01-10 10:15:00'),
    ('bob', 'bob@example.com', '$2a$10$WsYGYFpGJaDMnuOTL/qiW.siu6Ibn1Kvzy2cs/kXONu3G05u0UsYO', 2, '2023-02-12 11:30:00'),
    ('charlie', 'charlie@example.com', '$2a$10$WsYGYFpGJaDMnuOTL/qiW.siu6Ibn1Kvzy2cs/kXONu3G05u0UsYO', 3, '2023-03-18 09:00:00'),
    ('diana', 'diana@example.com', '$2a$10$WsYGYFpGJaDMnuOTL/qiW.siu6Ibn1Kvzy2cs/kXONu3G05u0UsYO', 4, '2023-04-22 14:45:00'),
    ('eric', 'eric@example.com', '$2a$10$WsYGYFpGJaDMnuOTL/qiW.siu6Ibn1Kvzy2cs/kXONu3G05u0UsYO', 5, '2023-05-01 08:20:00'),
    ('fiona', 'fiona@example.com', '$2a$10$WsYGYFpGJaDMnuOTL/qiW.siu6Ibn1Kvzy2cs/kXONu3G05u0UsYO', 6, '2023-06-10 13:10:00'),
    ('george', 'george@example.com', '$2a$10$WsYGYFpGJaDMnuOTL/qiW.siu6Ibn1Kvzy2cs/kXONu3G05u0UsYO', 7, '2023-07-04 16:00:00'),
    ('hannah', 'hannah@example.com', '$2a$10$WsYGYFpGJaDMnuOTL/qiW.siu6Ibn1Kvzy2cs/kXONu3G05u0UsYO', 8, '2023-08-08 18:25:00'),
    ('isaac', 'isaac@example.com', '$2a$10$WsYGYFpGJaDMnuOTL/qiW.siu6Ibn1Kvzy2cs/kXONu3G05u0UsYO', 9, '2023-09-15 20:40:00'),
    ('admin', 'admin@hotmail.fr', '$2a$10$PcVp7qiZ.MUqWYxuYjsKheEbppWQMugXoibAJGQ8vtF37XF2Jp656', 10, '2023-10-20 07:55:00');

-- Insert fake transactions
INSERT INTO public.transactions (amount, receiver_id, sender_id, description) VALUES
    (100.50, 2, 1, 'Payment for services'),
    (250.00, 3, 1, 'Gift'),
    (75.25, 4, 2, 'Refund'),
    (1200.00, 5, 3, 'Salary payment'),
    (33.99, 6, 5, 'Lunch share'),
    (455.10, 7, 2, 'Loan repayment'),
    (12.00, 8, 7, 'Coffee reimbursement'),
    (600.75, 9, 4, 'Project bonus'),
    (50.50, 2, 5, 'Shared expenses'),
    (89.30, 1, 6, 'Book purchase');

-- Insert fake user connections
INSERT INTO public.user_connections (user_id, connection_id) VALUES
    (1, 2),
    (1, 3),
    (2, 4),
    (2, 5),
    (3, 6),
    (4, 7),
    (5, 8),
    (6, 9),
    (7, 8),
    (8, 1);