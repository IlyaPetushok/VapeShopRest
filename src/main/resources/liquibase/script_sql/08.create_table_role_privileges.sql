create table role_privileges(
	rp_id_role integer references role(id_role),
	rp_id_privileges integer references privileges(id_privelege)
);