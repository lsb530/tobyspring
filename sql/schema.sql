CREATE TABLE orders
(
    id    bigint NOT NULL,
    no    varchar(255),
    total numeric(38, 2),
    PRIMARY KEY (id)
);
ALTER TABLE if EXISTS orders DROP CONSTRAINT IF EXISTS UK43egxxciqr9ncgmxbdx2avi8n;
ALTER TABLE if EXISTS orders ADD CONSTRAINT UK43egxxciqr9ncgmxbdx2avi8n UNIQUE (NO);
CREATE SEQUENCE orders_SEQ START WITH 1 INCREMENT BY 50;