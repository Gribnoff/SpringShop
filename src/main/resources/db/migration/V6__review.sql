create table if not exists reviews
(
	id uuid default uuid_generate_v4() not null
		constraint review_pk
			primary key,
	shopuser uuid not null
		constraint review_shopuser_fk
			references shopusers,
	product uuid not null
		constraint review_product_fk
			references products,
	comment varchar(500) not null
);

alter table reviews owner to postgres;

create unique index if not exists review_id_uindex
	on reviews (id);
