CREATE TABLE if not exists purchase_product (
    purchase UUID NOT NULL CONSTRAINT FK_purchase_product_purchase REFERENCES purchases,
    product UUID NOT NULL CONSTRAINT FK_purchase_product_product REFERENCES products
);