-- Insert data into the cart table
INSERT INTO cart (cart_date) VALUES 
('2024-04-25 10:00:00'), ('2024-04-24 15:30:00'), ('2024-04-24 15:00:00');

-- Insert data into the cart_item table
INSERT INTO cart_item (quantity, cart_id, item_id, total_price, total_discount) VALUES
(500, 1, 2, 6.0, 1.0),
(7, 2, 1, 3, 1.0),
(3, 3, 3, 3.0, 1.0);


INSERT INTO discount (discount_unit, is_constant_slab) VALUES
('PERCENTAGE', false),
('MONEY', true),
('MONEY', false),
('MONEY', true),
('MONEY', true);


-- Insert data into the discount_slab table
INSERT INTO discount_slab (discount_slab_id, discount_id, units_to_get_discount, discount_amount) VALUES 
(1, 1, 500, 10),
(2, 1, 100, 7),
(3, 1, 1, 5),
(4, 2, 6, 1),
(5, 4, 6, 1.5),
(6, 5, 6, 2);


-- Populate item table
INSERT INTO item (item_type, item_name, item_price, item_unit, created_at, discount_id) VALUES
('VEGETABLES', 'Vegetables', 0.01, 'GRAM', '2024-04-24 10:00:00', 1),
('BEERS', 'Dutch Beer', 0.5, 'PIECE', '2024-04-24 11:00:00', 2),
('BREADS', 'Bread', 1.00, 'PIECE', '2024-04-19 11:00:00', 3),
('BEERS', 'Belgium Beer', 0.75, 'PIECE', '2024-04-24 11:00:00', 4),
('BEERS', 'German Beer', 1, 'PIECE', '2024-04-24 11:00:00', 5);

