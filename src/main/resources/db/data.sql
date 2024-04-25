INSERT INTO discount (discount_unit, item_id) VALUES
('PIECE', 1),
('PERCENTAGE', 2);




-- Populate item table
INSERT INTO item (item_type, item_price, item_unit, created_at, discount_id) VALUES
('Vegetable', 1.99, 'kg', '2022-04-24 10:00:00', 1),
('Fruit', 0.99, 'each', '2022-04-24 11:00:00', 2);


-- Populate cart table
INSERT INTO cart (cart_date) VALUES
('2024-04-24 12:00:00'),
('2024-04-24 13:00:00');

-- Populate cart_item table
INSERT INTO cart_item (cart_item_id, quantity, cart_id, item_id, total_price, total_discount) VALUES
(1, 2, 101, 201, 15.0, 3.0),
(2, 3, 102, 202, 22.5, 4.5),
(3, 1, 103, 203, 10.0, 2.0);

-- Populate discount_slab table
INSERT INTO discount_slab (discount_slab_id, discount_id, is_constant_slab, units_to_get_discount, discount_amount) VALUES
(1, 1, true, 100, 10),
(2, 1, false, 200, 20),
(3, 2, true, 150, 15),
(4, 2, false, 250, 25);



