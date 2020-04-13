CREATE TABLE IF NOT EXISTS shop.product_image
(
	product uuid not null
		constraint product_fk
			references products,
	image uuid not null
		constraint image_fk
			references images,
	constraint products_images_pk
		primary key (product, image)
);

alter table product_image owner to postgres;

INSERT INTO shop.images (id, name) VALUES ('25490baa-4788-4a64-aad1-cb2ec1b38bd4', 'apple_1.png');

INSERT INTO shop.product_image (product, image)
	VALUES 	('9ec9ea5a-f9ef-4159-9a9c-bfeedc77ca23', '9ec9ea5a-f9ef-4159-9a9c-bfeedc77ca23'),
			('290679fa-9812-44c6-aa22-df5689d99e14', '290679fa-9812-44c6-aa22-df5689d99e14'),
			('08b39bd2-5357-43f1-aa3e-d1b9ce028e5e', '08b39bd2-5357-43f1-aa3e-d1b9ce028e5e'),
			('90a1448a-2989-4882-aa70-7cd97f0bcbf6', '90a1448a-2989-4882-aa70-7cd97f0bcbf6'),
			('0c38d227-e573-451d-8360-1eb038fd7472', '0c38d227-e573-451d-8360-1eb038fd7472'),
			('62b68ec4-7e33-4bce-a27e-cffb558040f1', '62b68ec4-7e33-4bce-a27e-cffb558040f1'),
			('90806402-753e-436b-85c4-cbe84e4644eb', '90806402-753e-436b-85c4-cbe84e4644eb'),
			('b85d8706-d663-4311-a15a-fea21e476ee9', 'b85d8706-d663-4311-a15a-fea21e476ee9'),
			('08b39bd2-5357-43f1-aa3e-d1b9ce028e5e', '25490baa-4788-4a64-aad1-cb2ec1b38bd4');

ALTER TABLE shop.products
    DROP COLUMN IF EXISTS image;

