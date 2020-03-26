create table if not exists roles
(
    id smallint not null
        constraint roles_pk
            primary key,
    name varchar(255) not null,
    description varchar(255)
);

create unique index roles_id_uindex
    on roles (id);

create unique index roles_name_uindex
    on roles (name);

INSERT INTO public.roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.roles (id, name) VALUES (2, 'ROLE_CUSTOMER');
INSERT INTO public.roles (id, name) VALUES (3, 'ROLE_MANAGER');


create table if not exists shopusers
(
    id uuid default uuid_generate_v4() not null
        constraint shopuser_pk
            primary key,
    phone varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) default NULL::character varying
);

alter table shopusers owner to postgres;

create unique index shopuser_email_uindex
    on shopusers (email);

create unique index shopuser_id_uindex
    on shopusers (id);

alter table purchases
    add shopuser uuid default '' not null ;

alter table purchases
    add constraint purchases_shop_fk
        foreign key (shopuser) references shopusers;

create unique index shopuser_phone_uindex
    on shopusers (phone);

INSERT INTO public.shopusers (id, phone, password, email, first_name, last_name)
    VALUES  ('6b718067-e1e4-4202-a7e2-7339ea0d6cb4', 'anonymous', 'anonymous', 'anonymous@springshop.com', 'anonymous', 'anonymous'),
            ('fbe5a8e7-8555-4ee8-bff2-c572447e5f25', 'admin', '$2y$12$pvkdAgbpPSg/pnRIxSSMOe3oBtFI1gnclOfgEvDY5wAU4vZrwrqBW', 'admin@springshop.com', 'Admin', 'Admin'),
            ('04c8bd30-ba4e-4e82-b996-db907e37a2c6', 'user', '$2y$12$RzcNIXtkdIZQ9NttEF2DVOj4RYGn3guYT3CKFOWbnvX2t4hIm3jyK', 'user@springshop.com', 'User', 'User');


create table if not exists shopuser_role
(
    shopuser uuid not null
        constraint shopuser_role_shopuser_fk
            references shopusers,
    role smallint not null
        constraint shopuser_role_role_fk
            references roles,
    constraint shopuser_role_pk
        primary key (shopuser, role)
);

alter table shopuser_role owner to postgres;

INSERT INTO public.shopuser_role (shopuser, role)
    VALUES  ('6b718067-e1e4-4202-a7e2-7339ea0d6cb4', 2),
            ('04c8bd30-ba4e-4e82-b996-db907e37a2c6', 2),
            ('fbe5a8e7-8555-4ee8-bff2-c572447e5f25', 1);
