CREATE TABLE discount (
    discount_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_unit VARCHAR(255),
    discount_unit VARCHAR(255),
    is_discount_constant BOOLEAN
);


CREATE TABLE discount_slab (
    discount_slab_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    discount_id BIGINT,
    units_to_get_discount INT,
    discount_amount INT,
    CONSTRAINT fk_discount_slab_discount FOREIGN KEY (discount_id) REFERENCES discount (discount_id)
);

CREATE TABLE item (
    item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_type VARCHAR(255),
    created_at TIMESTAMP,
    discount_id BIGINT,
    FOREIGN KEY (discount_id) REFERENCES discount (discount_id)
);




CREATE TABLE cart (
    cart_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_date TIMESTAMP
);

CREATE TABLE cart_item (
    cart_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT,
    cart_id BIGINT,
    item_id BIGINT,
    FOREIGN KEY (cart_id) REFERENCES cart (cart_id),
    FOREIGN KEY (item_id) REFERENCES item (item_id)
);



