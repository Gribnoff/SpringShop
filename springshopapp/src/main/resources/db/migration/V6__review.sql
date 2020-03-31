create table if not exists reviews
(
	id uuid default uuid_generate_v4() not null
		constraint reviews_pk
			primary key,
	shopuser uuid not null
		constraint reviews_shopuser_fk
			references shopusers,
	product uuid not null
		constraint reviews_product_fk
			references products,
	comment varchar(500) not null
);

alter table reviews owner to postgres;

create unique index if not exists reviews_id_uindex
	on reviews (id);

alter table reviews
    add image uuid default null;
alter table reviews
    add constraint reviews_image_fk
        foreign key (image) references images;

alter table reviews
    add approved boolean default false not null;
