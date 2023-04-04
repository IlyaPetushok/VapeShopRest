create table role(
	id_role integer generated always as identity,
	name_role character varying(40) not null,
	Primary key(id_role)
);