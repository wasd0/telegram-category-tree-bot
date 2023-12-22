create table if not exists user_roles
(
    id      bigserial primary key,
    user_id bigint      NOT NULL UNIQUE,
    name    varchar(60) NOT NULL UNIQUE
);

create table if not exists categories
(
    id bigserial primary key,
    name varchar(255) not null unique,
    parent_id bigint null 
);