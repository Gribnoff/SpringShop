CREATE TABLE IF NOT EXISTS public.images (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    name character varying NOT NULL
);

ALTER TABLE public.images OWNER TO postgres;

ALTER TABLE ONLY public.images
    ADD CONSTRAINT images_pkey PRIMARY KEY (id);