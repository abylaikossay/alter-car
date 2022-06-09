set
client_encoding = 'UTF8';

create table account
(
    id         bigserial primary key,
    username   varchar(255) not null unique,
    password   varchar(255) not null,
    created_at timestamp    not null default now(),
    updated_at timestamp    not null default now()
);
alter sequence account_id_seq restart with 1000000;
create index account_username on account (username);

create table account_session
(
    id            bigserial primary key,
    username      varchar(255) not null,
    refresh_token varchar(255) not null,
    expiry_date   timestamp    not null,
    created_at    timestamp    not null default now(),
    updated_at    timestamp    not null default now(),
    foreign key (username) references account (username)

);
alter sequence account_session_id_seq restart with 1000000;

create table role
(
    id         varchar(100) primary key,
    name       varchar(255) not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

create table account_role
(
    account_id bigint       not null,
    role_id    varchar(100) not null,
    primary key (account_id, role_id),
    foreign key (account_id) references account (id),
    foreign key (role_id) references role (id)
);

create table privilege
(
    id         varchar(100) primary key,
    name       varchar(255) not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

create table role_privilege
(
    role_id      varchar(100) not null,
    privilege_id varchar(100) not null,
    primary key (role_id, privilege_id),
    foreign key (role_id) references role (id),
    foreign key (privilege_id) references privilege (id)
);

create table user_info
(
    id         bigserial primary key,
    surname    varchar(255) not null,
    name       varchar(255) not null,
    patronymic varchar(255),
    account_id bigint       not null,
    created_at timestamp    not null default now(),
    updated_at timestamp    not null default now(),
    foreign key (account_id) references account (id)
);
alter sequence user_info_id_seq restart with 1000000;

