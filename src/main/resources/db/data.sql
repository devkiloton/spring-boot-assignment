-- Insert data into discount table
INSERT INTO discount (item_unit, discount_unit, is_discount_constant) VALUES
('g', '%', TRUE),
('pc', '$', FALSE);


-- Populate item table
INSERT INTO item (item_type, created_at, discount_id) VALUES
('Vegetable', CURRENT_TIMESTAMP, 1),
('Fruit', CURRENT_TIMESTAMP, 2);

-- Populate cart table
INSERT INTO cart (cart_date) VALUES
('2024-04-24 12:00:00'),
('2024-04-24 13:00:00');

-- Populate cart_item table
INSERT INTO cart_item (cart_id, item_id, quantity) VALUES
(1, 1, 5),
(2, 2, 3);

-- Populate discount_slab table
INSERT INTO discount_slab (discount_id, units_to_get_discount, discount_amount) VALUES
(1, 100, 10),
(1, 200, 20),
(2, 1, 5),
(2, 3, 10);


