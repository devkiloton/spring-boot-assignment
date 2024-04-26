CREATE TABLE cart (
    cart_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_date TIMESTAMP
);

CREATE TABLE cart_item (
    cart_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT,
    cart_id BIGINT,
    item_id BIGINT,
    total_price DOUBLE,
    total_discount DOUBLE,
    FOREIGN KEY (cart_id) REFERENCES cart (cart_id)
);

CREATE TABLE discount (
    discount_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    is_constant_slab BOOLEAN,
    discount_unit VARCHAR(255)
);

CREATE TABLE discount_slab (
    discount_slab_id BIGINT PRIMARY KEY,
    discount_id BIGINT,
    units_to_get_discount INT,
    discount_amount INT,
    FOREIGN KEY (discount_id) REFERENCES discount (discount_id)
);

CREATE TABLE item (
    item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_type VARCHAR(255),
    item_name VARCHAR(255),
    item_price DOUBLE,
    item_unit VARCHAR(255),
    created_at TIMESTAMP,
    discount_id BIGINT,
    FOREIGN KEY (discount_id) REFERENCES discount (discount_id)
);
