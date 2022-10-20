-- Using H2 because did not work TestContainers work with WSL with daemon via tcp, I will try later.
CREATE TABLE event_register_company (
	id bigint  generated always as identity,
	payload json NOT NULL,
	"time" timestamp NOT NULL,
	CONSTRAINT evt_reg_comp_pk PRIMARY KEY (id)
);

CREATE TABLE company (
	id bigint  generated always as identity,
	name varchar(255) NOT NULL,
	CONSTRAINT company_pk PRIMARY KEY (id)
);

CREATE TABLE ticker (
	id bigint   generated always as identity,
	company_id SMALLINT NOT NULL,
	name varchar(10) NOT NULL,
	CONSTRAINT ticker_pk PRIMARY KEY (id),
	CONSTRAINT ticker_company_fk FOREIGN KEY (company_id) REFERENCES company(id)
);
