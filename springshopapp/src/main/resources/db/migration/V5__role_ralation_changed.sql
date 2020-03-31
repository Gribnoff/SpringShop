alter table shopusers
    add role varchar(255) default 'ROLE_CUSTOMER' not null;

update shopusers
    set role = roles.name
    from shopuser_role
        join roles
            on shopuser_role.role = roles.id
    where shopusers.id = shopuser_role.shopuser;

drop table shopuser_role;
drop table roles;
