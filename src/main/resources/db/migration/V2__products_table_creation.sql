CREATE TABLE IF NOT EXISTS public.products (
    description character varying,
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    price double precision NOT NULL,
    title character varying(255) NOT NULL,
    added timestamp without time zone DEFAULT now() NOT NULL,
    available boolean DEFAULT false,
    image uuid,
    category integer
);

ALTER TABLE public.products OWNER TO postgres;

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.products
    ADD CONSTRAINT product_image_fk FOREIGN KEY (image) REFERENCES public.images(id) NOT VALID;
