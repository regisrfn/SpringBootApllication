CREATE TABLE IF NOT EXISTS orders (
	id_order VARCHAR NOT NULL PRIMARY KEY,
	id_client VARCHAR NOT NULL,
	id_parcel VARCHAR NOT NULL,
	total_value FLOAT NOT NULL,
	order_address VARCHAR NOT NULL
);
-- INSERT INTO orders (id_order, id_client, id_parcel, total_value, order_address)
-- VALUES (1, 123, 456, 1.99, 'Rua de cima');

-- CREATE TABLE IF NOT EXISTS ordersTest (
-- 	id_order VARCHAR(36) NOT NULL PRIMARY KEY,
-- 	id_client INT NOT NULL,
-- 	id_parcel INT NOT NULL,
-- 	total_value FLOAT NOT NULL,
-- 	order_address VARCHAR(256) NOT NULL
-- );