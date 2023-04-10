CREATE TABLE products (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   name VARCHAR(255) NOT NULL,
   cost DOUBLE PRECISION NOT NULL,
   quantity INTEGER NOT NULL,
   seller_id BIGINT,
   CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   username VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   role VARCHAR(255) NOT NULL,
   deposit numeric(10, 2) DEFAULT 0.00 NOT NULL,
   CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE products ADD CONSTRAINT FK_PRODUCTS_ON_SELLER FOREIGN KEY (seller_id) REFERENCES users (id);