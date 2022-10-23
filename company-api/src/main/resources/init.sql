CREATE TABLE register_company_outbox
(
    id      bigserial PRIMARY KEY,
    payload  varchar(1000)     NOT NULL,
    created_at  timestamp  NOT NULL
);

ALTER TABLE register_company_outbox REPLICA IDENTITY FULL;

CREATE TABLE company
(
    id   bigserial PRIMARY KEY,
    name varchar(255) NOT NULL
);

CREATE TABLE ticker
(
    id         bigserial primary KEY,
    company_id int4 NULL,
    name     varchar(10) NOT NULL,
    CONSTRAINT ticker_company_fk FOREIGN KEY (company_id) REFERENCES "company-api".company (id)
);
