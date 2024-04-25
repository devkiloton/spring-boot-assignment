


-- Insert data into the cart table
INSERT INTO cart (cart_date) VALUES ('2024-04-25 10:00:00');
INSERT INTO cart (cart_date) VALUES ('2024-04-24 15:30:00');

-- Insert data into the cart_item table
INSERT INTO cart_item (quantity, cart_id, item_id, total_price, total_discount) VALUES (2, 1, 1, 3.0, 0.5);
INSERT INTO cart_item (quantity, cart_id, item_id, total_price, total_discount) VALUES (3, 1, 2, 6.0, 1.0);
INSERT INTO cart_item (quantity, cart_id, item_id, total_price, total_discount) VALUES (1, 2, 1, 1.5, 0.0);

INSERT INTO discount (discount_unit) VALUES
('PIECE'),
('PERCENTAGE');

-- Insert data into the discount_slab table
INSERT INTO discount_slab (discount_slab_id, discount_id, is_constant_slab, units_to_get_discount, discount_amount) VALUES (1, 1, true, 5, 2);
INSERT INTO discount_slab (discount_slab_id, discount_id, is_constant_slab, units_to_get_discount, discount_amount) VALUES (2, 1, true, 10, 10);


-- Populate item table
INSERT INTO item (item_type, item_price, item_unit, created_at, discount_id) VALUES
('VEGETABLES', 1.99, 'GRAM', '2022-04-24 10:00:00', 1),
('BEERS', 0.99, 'PIECE', '2022-04-24 11:00:00', 2);

