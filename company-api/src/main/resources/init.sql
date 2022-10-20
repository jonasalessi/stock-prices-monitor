CREATE TABLE "company-api"."event_register_company"
(
    id      bigserial PRIMARY KEY,
    payload jsonb     NOT NULL,
    "time"  timestamp NOT NULL
);

ALTER TABLE "company-api"."event_register_company" REPLICA IDENTITY FULL;

CREATE TABLE "company-api".company
(
    id   bigserial PRIMARY KEY,
    name varchar(255) NOT NULL
);

CREATE TABLE "company-api".ticker
(
    id         bigserial primary KEY,
    company_id int4 NULL,
    "name"     varchar(10) NOT NULL,
    CONSTRAINT ticker_company_fk FOREIGN KEY (company_id) REFERENCES "company-api".company (id)
);
