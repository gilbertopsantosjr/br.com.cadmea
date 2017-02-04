INSERT INTO cadmea_person_member (pes_date_of_birth, pes_gender, pes_name, pes_register, pes_relationship, con_id) VALUES ('2016-11-22 00:00:00', '1', 'Gilberto Santos', '1233', '1', NULL);

INSERT INTO cadmea_user_system (DTYPE, usu_dt_expired, usu_dt_register, usu_email, usu_last_visit, usu_pwd, usu_situation, usu_username, pes_id) VALUES ('USER', '2016-11-11 00:00:00', '2016-11-11 00:00:00', 'gilbertopsantosjr@gmail.com', '2016-11-11 00:00:00', '21232f297a57a5a743894a0e4a801fc3', '1', 'gilbertopsantosjr', '1');

insert into cadmea_person_member (pes_id, pes_date_of_birth, pes_gender, pes_name, pes_register, pes_relationship) values (null, ?, ?, ?, ?, ?)
insert into phone (pho_id, is_default, pho_number) values (null, ?, ?)
insert into cadmea_user_system (usu_id, usu_dt_expired, usu_dt_register, usu_email, usu_last_visit, usu_nickname, usu_pwd, pes_id, usu_situation) values (null, ?, ?, ?, ?, ?, ?, ?, ?)
insert into cadmea_user_permission (per_id, role) values (null, ?)
insert into cadmea_phones_per_person (user_id, pho_id) values (?, ?)
insert into cadmea_permissions_per_user (user_id, permission_id) values (?, ?)