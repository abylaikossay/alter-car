set client_encoding='UTF8';

insert into account(id, username, password)
values ((select nextval('account_id_seq')), 'system','$2a$12$a5CWotO1LwIFmhSJMCnOs.BYHkevlaecRhpgRTraNzml5CdySOS8C');

insert into user_info(surname, name, account_id)
values ('SYSTEM', 'SYSTEM', (select currval('account_id_seq')));

insert into account_role values ((select currval('account_id_seq')), 'ROLE_SYSTEM');
