create table if not exists images
(
	id uuid default uuid_generate_v4() not null
		constraint images_pk
			primary key,
	name varchar not null
);

alter table images owner to postgres;

create table if not exists products
(
	description varchar,
	id uuid default uuid_generate_v4() not null
		constraint products_pk
			primary key,
	price double precision not null,
	title varchar(255) not null,
	added timestamp default now() not null,
	available boolean default false,
    image uuid
		constraint image_fk
			references images,
	category integer
);

alter table products owner to postgres;

INSERT INTO shop.images (id, name)
    VALUES  ('08b39bd2-5357-43f1-aa3e-d1b9ce028e5e', 'apple.png'),
            ('290679fa-9812-44c6-aa22-df5689d99e14', 'bread.png'),
            ('b85d8706-d663-4311-a15a-fea21e476ee9', 'cheese.png'),
            ('90806402-753e-436b-85c4-cbe84e4644eb', 'donut.png'),
            ('0c38d227-e573-451d-8360-1eb038fd7472', 'juice.png'),
            ('9ec9ea5a-f9ef-4159-9a9c-bfeedc77ca23', 'lemonade.png'),
            ('90a1448a-2989-4882-aa70-7cd97f0bcbf6', 'milk.png'),
            ('62b68ec4-7e33-4bce-a27e-cffb558040f1', 'tea.png');

-- noinspection SqlResolve @ column/"image"
INSERT INTO shop.products (description, id, price, title, added, available, image, category)
    VALUES  (null, '9ec9ea5a-f9ef-4159-9a9c-bfeedc77ca23', 80, 'Лимонад', '2020-03-16 10:19:29.622000', true, '9ec9ea5a-f9ef-4159-9a9c-bfeedc77ca23', 0),
            (null, '290679fa-9812-44c6-aa22-df5689d99e14', 50, 'Хлеб', '2020-03-15 22:54:21.184000', true, '290679fa-9812-44c6-aa22-df5689d99e14', 1),
            (null, '08b39bd2-5357-43f1-aa3e-d1b9ce028e5e', 150, 'Яблоки', '2020-03-15 22:54:21.184000', true, '08b39bd2-5357-43f1-aa3e-d1b9ce028e5e', 1),
            (null, '90a1448a-2989-4882-aa70-7cd97f0bcbf6', 100, 'Молоко', '2020-03-15 22:54:21.184000', true, '90a1448a-2989-4882-aa70-7cd97f0bcbf6', 0),
            (null, '0c38d227-e573-451d-8360-1eb038fd7472', 120, 'Сок', '2020-03-16 10:19:29.622000', true, '0c38d227-e573-451d-8360-1eb038fd7472', 0),
            (null, '62b68ec4-7e33-4bce-a27e-cffb558040f1', 50, 'Чай', '2020-03-16 10:19:29.622000', true, '62b68ec4-7e33-4bce-a27e-cffb558040f1', 0),
            (null, '90806402-753e-436b-85c4-cbe84e4644eb', 10, 'Пончики', '2020-03-16 10:19:29.622000', true, '90806402-753e-436b-85c4-cbe84e4644eb', 1),
            (null, 'b85d8706-d663-4311-a15a-fea21e476ee9', 200, 'Сыр', '2020-03-15 22:54:21.184000', true, 'b85d8706-d663-4311-a15a-fea21e476ee9', 1);