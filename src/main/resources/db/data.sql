insert into discount (discount_unit, is_constant_slab) values 
('PERCENTAGE', false),
('MONEY', true),
('MONEY', false),
('MONEY', true),
('MONEY', true);

insert into discount_slab (discount_id,discount_amount,units_to_get_discount) values 
(1,10,500),
(1, 7, 100),
(1,5,1),
(2,1,6),
(4,1.5,6),
(5,2,6);

insert into item (item_price, created_at, discount_id, item_name, item_type, item_unit) values
(0.01, '2024-04-24 10:00:00', 1, 'Vegetables', 'VEGETABLES', 'GRAM'),
(0.5, '2024-04-24 11:00:00', 2, 'Dutch Beer', 'BEERS', 'PIECE'),
(1.0, '2024-04-26 11:00:00', 3, 'Bread', 'BREADS', 'PIECE'),
(0.75, '2024-04-24 11:00:00', 4, 'Belgium Beer', 'BEERS', 'PIECE'),
(1.0, '2024-04-24 11:00:00', 5, 'German Beer', 'BEERS', 'PIECE');