CREATE TABLE IF NOT EXISTS orders (
	id_order VARCHAR(36) NOT NULL PRIMARY KEY,
	id_client INT NOT NULL,
	id_parcel INT NOT NULL,
	total_value FLOAT NOT NULL,
	order_address VARCHAR(1000) NOT NULL
);
-- INSERT INTO orders (id_client, id_parcel, total_value, order_address)
-- VALUES (123, 456, 1.99, 'Rua de cima');

CREATE TABLE IF NOT EXISTS ordersTest (
	id_order VARCHAR(36) NOT NULL PRIMARY KEY,
	id_client INT NOT NULL,
	id_parcel INT NOT NULL,
	total_value FLOAT NOT NULL,
	order_address VARCHAR(1000) NOT NULL
);