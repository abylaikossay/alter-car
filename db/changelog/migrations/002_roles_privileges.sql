set client_encoding='UTF8';

-- Роли
insert into role(id, name) values('ROLE_SYSTEM','Система');

insert into role(id, name) values('ROLE_ADMIN','Админ');

insert into role(id, name) values('ROLE_MODERATOR','Модератор');

insert into role(id, name) values('ROLE_INVESTOR','Инвестор');

insert into role(id, name) values('ROLE_BASIC','Обычный пользователь');

insert into role(id, name) values('ROLE_LEVEL_LOCAL','Лицо местного уровня');

insert into role(id, name) values('ROLE_LEVEL_CENTRAL','Лицо центрального уровня');

insert into role(id, name) values('ROLE_LEVEL_AUTHORISED','Лицо уполномоченного органа');

insert into role(id, name) values('ROLE_LEVEL_COMMISSION','Лицо комиссии');


-- Права
insert into privilege(id, name) values('CREATE_ACCOUNT','Создание аккаунта');

insert into privilege(id, name) values('READ_ACCOUNT','Чтение данных аккаунта аккаунта');

insert into privilege(id, name) values('EDIT_ACCOUNT','Редактирование данных аккаунта');

insert into privilege(id, name) values('CREATE_PROJECT','Создание проекта');

insert into privilege(id, name) values('READ_PROJECT','Чтение проектов');

insert into privilege(id, name) values('EDIT_PROJECT','Редактирование проектов');

insert into privilege(id, name) values('APPROVE_LEVEL_LOCAL', 'Согласование заявок на этапе местного уровня');

insert into privilege(id, name) values('APPROVE_LEVEL_CENTRAL', 'Согласование заявок на этапе центрального уровня');

insert into privilege(id, name) values('APPROVE_LEVEL_AUTHORISED', 'Согласование заявок на этапе уполномоченного уровня');

insert into privilege(id, name) values('APPROVE_LEVEL_COMMISSION', 'Согласование заявок на этапе комиссии');


-- Связка Роль-Права
insert into role_privilege (role_id, privilege_id) values ('ROLE_SYSTEM', 'CREATE_ACCOUNT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_SYSTEM', 'READ_ACCOUNT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_SYSTEM', 'EDIT_ACCOUNT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_SYSTEM', 'CREATE_PROJECT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_SYSTEM', 'READ_PROJECT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_SYSTEM', 'EDIT_PROJECT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_SYSTEM', 'APPROVE_LEVEL_LOCAL');
insert into role_privilege (role_id, privilege_id) values ('ROLE_SYSTEM', 'APPROVE_LEVEL_CENTRAL');
insert into role_privilege (role_id, privilege_id) values ('ROLE_SYSTEM', 'APPROVE_LEVEL_AUTHORISED');
insert into role_privilege (role_id, privilege_id) values ('ROLE_SYSTEM', 'APPROVE_LEVEL_COMMISSION');

insert into role_privilege (role_id, privilege_id) values ('ROLE_ADMIN', 'READ_ACCOUNT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_ADMIN', 'EDIT_ACCOUNT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_ADMIN', 'CREATE_PROJECT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_ADMIN', 'READ_PROJECT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_ADMIN', 'EDIT_PROJECT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_ADMIN', 'APPROVE_LEVEL_LOCAL');
insert into role_privilege (role_id, privilege_id) values ('ROLE_ADMIN', 'APPROVE_LEVEL_CENTRAL');
insert into role_privilege (role_id, privilege_id) values ('ROLE_ADMIN', 'APPROVE_LEVEL_AUTHORISED');
insert into role_privilege (role_id, privilege_id) values ('ROLE_ADMIN', 'APPROVE_LEVEL_COMMISSION');

insert into role_privilege (role_id, privilege_id) values ('ROLE_MODERATOR', 'READ_PROJECT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_MODERATOR', 'EDIT_PROJECT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_MODERATOR', 'APPROVE_LEVEL_LOCAL');
insert into role_privilege (role_id, privilege_id) values ('ROLE_MODERATOR', 'APPROVE_LEVEL_CENTRAL');
insert into role_privilege (role_id, privilege_id) values ('ROLE_MODERATOR', 'APPROVE_LEVEL_AUTHORISED');
insert into role_privilege (role_id, privilege_id) values ('ROLE_MODERATOR', 'APPROVE_LEVEL_COMMISSION');

insert into role_privilege (role_id, privilege_id) values ('ROLE_INVESTOR', 'READ_PROJECT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_BASIC', 'READ_PROJECT');

insert into role_privilege (role_id, privilege_id) values ('ROLE_LEVEL_LOCAL', 'READ_PROJECT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_LEVEL_LOCAL', 'APPROVE_LEVEL_LOCAL');


insert into role_privilege (role_id, privilege_id) values ('ROLE_LEVEL_CENTRAL', 'READ_PROJECT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_LEVEL_CENTRAL', 'APPROVE_LEVEL_CENTRAL');


insert into role_privilege (role_id, privilege_id) values ('ROLE_LEVEL_AUTHORISED', 'READ_PROJECT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_LEVEL_AUTHORISED', 'APPROVE_LEVEL_AUTHORISED');

insert into role_privilege (role_id, privilege_id) values ('ROLE_LEVEL_COMMISSION', 'READ_PROJECT');
insert into role_privilege (role_id, privilege_id) values ('ROLE_LEVEL_COMMISSION', 'APPROVE_LEVEL_COMMISSION');

