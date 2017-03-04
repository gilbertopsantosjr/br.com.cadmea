insert into cadmea_person_member 
	(pes_date_of_birth, pes_gender, pes_name, pes_surname, pes_relationship) 
values 
	('1980-08-22 00:00:00', '1', 'Gilberto', 'Pereira dos Santos Junior', 3);
	
insert into phone (is_default, pho_number) values (1, '0873404578');

/* password is admin*/
insert into cadmea_user_system 
	( usu_dt_expired, usu_dt_register, usu_email, usu_last_visit, usu_nickname, usu_pwd, pes_id, usu_situation, usu_remember, usu_terms) 
values 
	( null, current_date(), 'gilbertopsantosjr@gmail.com', current_date(), 'gilbertopsantosjr', '$2a$08$ZlQuc3MkNSWvTh5PdjHZ2uVB34LiSMaI6NT8VI5a8k1PXmtyqxiaq', 1, 1, 0, 1);

insert into cadmea_user_permission (role) values ('ROLE_ADMIN');

insert into cadmea_permissions_per_user (user_id, permission_id) values (1, 1);