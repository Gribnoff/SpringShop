create table if not exists purchases
(
	id uuid default uuid_generate_v4() not null
		constraint purchases_pk
			primary key,
	price double precision,
	email varchar(255),
	phone varchar(255)
);

create table cart_records
(
	id uuid default uuid_generate_v4() not null,
	quantity int default 0 not null,
	price double precision default 0.0 not null,
	product uuid not null
		constraint cart_records_product_fk
			references products
            on update cascade on delete cascade,
	purchase uuid not null
		constraint cart_records_purchase_fk
			references purchases
            on update cascade on delete cascade
);

create unique index cart_records_id_uindex
	on cart_records (id);

alter table cart_records
	add constraint cart_records_pk
		primary key (id);

