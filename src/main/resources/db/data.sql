INSERT INTO discount (discount_id, discount_unit) VALUES
(1, '$'),
(2, '%');



-- Populate item table
INSERT INTO item (item_type, item_price, item_unit, created_at, discount_id) VALUES
('Vegetable', 1.99, 'kg', '2022-04-24 10:00:00', 1),
('Fruit', 0.99, 'each', '2022-04-24 11:00:00', 2);


-- Populate cart table
INSERT INTO cart (cart_date) VALUES
('2024-04-24 12:00:00'),
('2024-04-24 13:00:00');

-- Populate cart_item table
INSERT INTO cart_item (cart_id, item_id, quantity) VALUES
(1, 1, 5),
(2, 2, 3);

-- Populate discount_slab table
INSERT INTO discount_slab (discount_slab_id, discount_id, is_constant_slab, units_to_get_discount, discount_amount) VALUES
(1, 1, true, 100, 10),
(2, 1, false, 200, 20),
(3, 2, true, 150, 15),
(4, 2, false, 250, 25);



