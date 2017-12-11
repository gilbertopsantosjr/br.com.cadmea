insert into country (cou_code, cou_language,cou_name) values ('55','pt_br','Brasil');
insert into country (cou_code, cou_language,cou_name) values ('353','en_UK','Ireland');

--adding Gilberto Santos User
insert into person_passport (cou_id, nationality, passport_number) values (1, 1,'FJ122624');
insert into person_member (date_of_birth, gender, name, relationship, surname, serial_person_number) values ('1980-08-22', 1,'Gilberto', 1,'Pereira dos Santos Junior', '3734563');
insert into phone (is_default, pho_number) values (1, '0873404578');
insert into person_member_phone (pes_id, pho_id) values (1, 1);

--user: gilbertopsantosjr@gmail.com
--senha: admin
insert into user_system( usu_dt_expired, usu_dt_register, usu_email, usu_last_visit, usu_nickname, usu_pwd, pes_id, usu_situation, usu_remember, usu_terms, usu_favorite_language) values ( null, current_date(), 'gilbertopsantosjr@gmail.com', current_date(), 'gilbertopsantosjr', '$2a$08$ZlQuc3MkNSWvTh5PdjHZ2uVB34LiSMaI6NT8VI5a8k1PXmtyqxiaq', 1, 1, 0, 1, 'pt_br');

--adding Antoly User
insert into person_member(date_of_birth, gender, name, relationship, surname, serial_person_number) values ('1988-10-09', 1, 'Anatoly', 2, 'Slowetzky', '23455');
insert into phone (is_default, pho_number) values (1, '0852503102');
insert into person_member_phone (pes_id, pho_id) values (2, 2);

insert into user_system ( usu_dt_expired, usu_dt_register, usu_email, usu_last_visit, usu_nickname, usu_pwd, pes_id, usu_situation, usu_remember, usu_terms) values ( null, current_date(), 'anatoly.slowetzky@gmail.com', current_date(), 'anatoly.slowetzky', '$2a$08$ZlQuc3MkNSWvTh5PdjHZ2uVB34LiSMaI6NT8VI5a8k1PXmtyqxiaq', 2, 1, 0, 1);

insert into cadmea_system (sys_identity, sys_name, sys_url) values ('CADMEA_TEST', 'Cadmea System Test', '/dashboard');
insert into cadmea_system (sys_identity, sys_name, sys_url) values ('MAGEC', 'System of properties management', '/dashboard');
insert into cadmea_system (sys_identity, sys_name, sys_url) values ('1CADS', 'Cadmea System', '/private/index.jsp');
insert into cadmea_system (sys_identity, sys_name, sys_url) values ('2MTFU', 'Message to the future', '/dashboard');
insert into cadmea_system (sys_identity, sys_name, sys_url) values ('HCSUT', 'Health Care System', '/dashboard');
insert into cadmea_system (sys_identity, sys_name, sys_url) values ('FOME', 'System of recipt to follow a diet plan', '/dashboard');

-- adding roles
insert into cadmea_user_permission (role_name,situation, sys_id) values ('ROLE_ADMIN',1, 1 );
insert into cadmea_user_permission (role_name,situation, sys_id) values ('ROLE_USER',1,  1 );
insert into cadmea_user_permission (role_name,situation, sys_id) values ('ROLE_USER',0,  2 );

-- adding permissino to the role
insert into cadmea_permissions_per_user (per_id, user_id) values (1, 1);

-- adding user Gilberto santos to CadmeaSystem CADMEA_TEST
insert into cadmea_systems_per_user (usu_id, sys_id) values (1, 1);

-- adding user Anatoly to CadmeaSystem CADMEA_TEST
insert into cadmea_systems_per_user (usu_id, sys_id) values (2, 1);