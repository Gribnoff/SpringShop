CREATE TABLE IF NOT EXISTS public.product_image
(
    product_id uuid NOT NULL,
    image_id uuid NOT NULL,
    CONSTRAINT product_image_pkey PRIMARY KEY (product_id, image_id),
    CONSTRAINT image_fkey FOREIGN KEY (image_id)
        REFERENCES public.images (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT product_fkey FOREIGN KEY (product_id)
        REFERENCES public.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.product_image
    OWNER to postgres;

INSERT INTO public.images (id, name) VALUES ('25490baa-4788-4a64-aad1-cb2ec1b38bd4', 'apple_1.png');

INSERT INTO public.product_image (product_id, image_id)
	VALUES 	('9ec9ea5a-f9ef-4159-9a9c-bfeedc77ca23', '9ec9ea5a-f9ef-4159-9a9c-bfeedc77ca23'),
			('290679fa-9812-44c6-aa22-df5689d99e14', '290679fa-9812-44c6-aa22-df5689d99e14'),
			('08b39bd2-5357-43f1-aa3e-d1b9ce028e5e', '08b39bd2-5357-43f1-aa3e-d1b9ce028e5e'),
			('90a1448a-2989-4882-aa70-7cd97f0bcbf6', '90a1448a-2989-4882-aa70-7cd97f0bcbf6'),
			('0c38d227-e573-451d-8360-1eb038fd7472', '0c38d227-e573-451d-8360-1eb038fd7472'),
			('62b68ec4-7e33-4bce-a27e-cffb558040f1', '62b68ec4-7e33-4bce-a27e-cffb558040f1'),
			('90806402-753e-436b-85c4-cbe84e4644eb', '90806402-753e-436b-85c4-cbe84e4644eb'),
			('b85d8706-d663-4311-a15a-fea21e476ee9', 'b85d8706-d663-4311-a15a-fea21e476ee9'),
			('08b39bd2-5357-43f1-aa3e-d1b9ce028e5e', '25490baa-4788-4a64-aad1-cb2ec1b38bd4');

ALTER TABLE public.products
    DROP COLUMN image;

